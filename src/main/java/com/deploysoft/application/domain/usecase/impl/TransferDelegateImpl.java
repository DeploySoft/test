package com.deploysoft.application.domain.usecase.impl;

import com.deploysoft.application.domain.command.Command;
import com.deploysoft.application.domain.command.impl.TransactionInCommand;
import com.deploysoft.application.domain.command.impl.TransactionOutCommand;
import com.deploysoft.application.domain.constant.CurrencyEnum;
import com.deploysoft.application.domain.constant.ErrorEnum;
import com.deploysoft.application.domain.constant.TypeConfigEnum;
import com.deploysoft.application.domain.constant.TypeTransactionEnum;
import com.deploysoft.application.domain.dto.AccountConfigDto;
import com.deploysoft.application.domain.dto.AccountDto;
import com.deploysoft.application.domain.dto.TransferRequestDto;
import com.deploysoft.application.domain.exception.TransferException;
import com.deploysoft.application.domain.usecase.ITaxDelegate;
import com.deploysoft.application.domain.usecase.ITransferDelegate;
import com.deploysoft.application.infrastructure.services.IAccountService;
import com.deploysoft.application.infrastructure.services.ITransferService;
import com.deploysoft.application.domain.dto.TransferResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 20/09/20
 **/
@Component
public class TransferDelegateImpl implements ITransferDelegate {

    private final ITransferService iTransferService;
    private final IAccountService iAccountService;
    private final ITaxDelegate iTaxDelegate;

    private final BiFunction<AccountDto, AccountConfigDto, Boolean> limitTransactions;

    public TransferDelegateImpl(ITransferService iTransferService, IAccountService iAccountService, ITaxDelegate iTaxDelegate) {
        this.iTransferService = iTransferService;
        this.iTaxDelegate = iTaxDelegate;
        this.iAccountService = iAccountService;
        this.limitTransactions = (account, accountConfig) -> {
            long transactions = this.iTransferService.countTransactionsPerDay(account.getId(), LocalDate.now(), TypeTransactionEnum.OUTCOME);
            return transactions >= Integer.parseInt(accountConfig.getValue());
        };
    }

    @Override
    @Transactional
    public ResponseEntity doTransfer(TransferRequestDto transfer) throws TransferException {

        //Check accounts
        AccountDto origin = iAccountService.findById(transfer.getOriginAccount())
                .orElseThrow(() -> new TransferException(ErrorEnum.INVALID_ACCOUNT_ORIGIN));

        AccountDto destination = iAccountService.findById(transfer.getDestinationAccount())
                .orElseThrow(() -> new TransferException(ErrorEnum.INVALID_ACCOUNT_DESTINATION));

        //Check config with recursive method
        checkConfigAccount(origin, TypeConfigEnum.LIMIT_TRANSFER_PER_DAY);

        //Check initial tax
        BigDecimal taxOriginalCurrency = iTaxDelegate.checkTax(transfer.getAmount()).get();

        //Convert currency and set in transfer
        CurrencyEnum accountCurrency = getAccountCurrency(destination);
        this.iTaxDelegate.convertAmount(transfer.getCurrency(), accountCurrency, transfer.getAmount())
                .peek(transfer::setAmount)
                .getOrElseThrow(TransferException::new);

        //Check current currency tax
        BigDecimal taxCurrentCurrency = iTaxDelegate.checkTax(transfer.getAmount()).peek(transfer::setTax).get();
        checkAmount(origin.getAmount(), transfer.getAmount(), taxCurrentCurrency);

        //Convert currency and set in tax
        this.iTaxDelegate.convertAmount(transfer.getCurrency(), accountCurrency, transfer.getTax())
                .peek(transfer::setTax)
                .getOrElseThrow(TransferException::new);

        //Do transfer with Command Pattern
        doTransfer(transfer, origin, destination);

        return ResponseEntity.ok(TransferResponseDto.builder().taxCollected(taxOriginalCurrency).build().setObjectMap(accountCurrency,taxCurrentCurrency));
    }

    @Transactional
    public synchronized void doTransfer(TransferRequestDto transfer, AccountDto origin, AccountDto destination) {
        List<Command<TransferRequestDto>> transactions = Arrays.asList(
                new TransactionOutCommand(this.iAccountService, this.iTransferService).setAccount(origin),
                new TransactionInCommand(this.iAccountService, this.iTransferService).setAccount(destination)
        );
        transactions.forEach(transactionGeneral -> transactionGeneral.execute(transfer));
    }

    private void checkConfigAccount(AccountDto originAccount, TypeConfigEnum config) throws TransferException {
        Optional<AccountConfigDto> validation = originAccount.getAccountConfig().stream()
                .filter(accountConfig -> config.equals(accountConfig.getId().getTypeConfigEnum()))
                .findFirst();
        if (validation.isPresent()) {
            switch (config) {
                case LIMIT_TRANSFER_PER_DAY:
                    validateLimitTransactions(originAccount, validation.get());
                    break;
                case CURRENCY_DEFAULT:
                    //Your logic to more configs
                    throw new IllegalStateException("Useless until you implemented your logic");
                default:
                    throw new IllegalStateException("Unexpected value: " + config);
            }
        }
    }

    private void checkAmount(BigDecimal amountInAccount, BigDecimal transfer, BigDecimal tax) throws TransferException {
        BigDecimal withTax = transfer.add(tax);
        if (amountInAccount.compareTo(withTax) < 0)
            throw new TransferException(ErrorEnum.INSUFFICIENT_FUNDS);
    }

    private void validateLimitTransactions(AccountDto account, AccountConfigDto validation) throws TransferException {
        if (Boolean.TRUE.equals(this.limitTransactions.apply(account, validation))) {
            throw new TransferException(ErrorEnum.LIMIT_EXCEEDED);
        }
    }

    private CurrencyEnum getAccountCurrency(AccountDto receptor) {
        return receptor.getAccountConfig().stream()
                .filter(account -> TypeConfigEnum.CURRENCY_DEFAULT.equals(account.getId().getTypeConfigEnum()))
                .findFirst()
                .map(AccountConfigDto::getValue)
                .map(CurrencyEnum::valueOf)
                .orElse(CurrencyEnum.CAD);
    }

}
