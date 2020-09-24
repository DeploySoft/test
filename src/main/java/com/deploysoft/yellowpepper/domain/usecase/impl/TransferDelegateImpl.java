package com.deploysoft.yellowpepper.domain.usecase.impl;

import com.deploysoft.yellowpepper.domain.command.Command;
import com.deploysoft.yellowpepper.domain.command.impl.TransactionInCommand;
import com.deploysoft.yellowpepper.domain.command.impl.TransactionOutCommand;
import com.deploysoft.yellowpepper.domain.constant.CurrencyEnum;
import com.deploysoft.yellowpepper.domain.constant.ErrorEnum;
import com.deploysoft.yellowpepper.domain.constant.TypeConfigEnum;
import com.deploysoft.yellowpepper.domain.constant.TypeTransactionEnum;
import com.deploysoft.yellowpepper.domain.dto.AccountConfigDto;
import com.deploysoft.yellowpepper.domain.dto.AccountDto;
import com.deploysoft.yellowpepper.domain.dto.TransferRequestDto;
import com.deploysoft.yellowpepper.domain.dto.TransferResponseDto;
import com.deploysoft.yellowpepper.domain.exception.TransferException;
import com.deploysoft.yellowpepper.domain.usecase.ITaxDelegate;
import com.deploysoft.yellowpepper.domain.usecase.ITransferDelegate;
import com.deploysoft.yellowpepper.infrastructure.services.IAccountService;
import com.deploysoft.yellowpepper.infrastructure.services.ITransferService;
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
    public ResponseEntity<TransferResponseDto> doTransfer(TransferRequestDto transfer) throws TransferException {

        //Check accounts
        AccountDto origin = iAccountService.findById(transfer.getOriginAccount())
                .orElseThrow(() -> new TransferException(ErrorEnum.INVALID_ACCOUNT_ORIGIN));

        AccountDto destination = iAccountService.findById(transfer.getDestinationAccount())
                .orElseThrow(() -> new TransferException(ErrorEnum.INVALID_ACCOUNT_DESTINATION));

        //Check config with recursive method
        checkConfigAccount(origin, TypeConfigEnum.LIMIT_TRANSFER_PER_DAY);

        //Check initial tax
        BigDecimal taxOriginalCurrency = iTaxDelegate.checkTax(origin.getAmount());
        checkAmount(origin.getAmount(), taxOriginalCurrency);

        //Convert currency
        this.iTaxDelegate.convertAmount(transfer.getCurrency(), getAccountCurrency(destination), transfer.getAmount())
                .peek(transfer::setAmount)
                .getOrElseThrow(TransferException::new);

        //Do transfer with Command Pattern
        doTransfer(transfer, origin, destination);

        return ResponseEntity.ok(TransferResponseDto.builder().taxCollected(taxOriginalCurrency).build());
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

    private void checkAmount(BigDecimal amountInAccount, BigDecimal amountTransaction) throws TransferException {
        BigDecimal withTax = amountInAccount.add(amountTransaction);
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
