package com.deploysoft.yellowpepper.domain.usecase.impl;

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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 20/09/20
 **/
@Component
public class TransferDelegateImpl implements ITransferDelegate {

    private final ITransferService iTransferService;
    private final IAccountService iAccountService;
    private final ITaxDelegate iTaxDelegate;

    private final Predicate<AccountConfigDto> limitTransferPredicate;
    private final BiFunction<AccountDto, AccountConfigDto, Boolean> checkConfig;

    public TransferDelegateImpl(ITransferService iTransferService, IAccountService iAccountService, ITaxDelegate iTaxDelegate) {
        this.iTransferService = iTransferService;
        this.iTaxDelegate = iTaxDelegate;
        this.iAccountService = iAccountService;

        this.limitTransferPredicate = accountConfig -> TypeConfigEnum.LIMIT_TRANSFER_PER_DAY.equals(accountConfig.getAccountConfigId().getTypeConfigEnum());
        this.checkConfig = (account, accountConfig) -> {
            long transactions = this.iTransferService.countTransactionsPerDay(account.getId(),LocalDate.now(),TypeTransactionEnum.OUTCOME);
            return transactions >= Integer.parseInt(accountConfig.getValue());
        };
    }

    @Override
    public ResponseEntity<TransferResponseDto> doTransfer(TransferRequestDto transferRequestDto) throws TransferException {
        AccountDto originAccount = iAccountService.findById(transferRequestDto.getOriginAccount())
                .orElseThrow(() -> new TransferException(ErrorEnum.INVALID_ACCOUNT_ORIGIN));

        AccountDto destinationAccount = iAccountService.findById(transferRequestDto.getDestinationAccount())
                .orElseThrow(() -> new TransferException(ErrorEnum.INVALID_ACCOUNT_DESTINATION));

        checkAccountConfig(originAccount);
        checkAmount(originAccount, transferRequestDto.getAmount().add(iTaxDelegate.checkTax(transferRequestDto.getAmount())));

        //new TransactionInCommand(iAccountRepository,iTransferRepository);

       // doTransfer(originAccount, TypeTransactionEnum.OUTCOME, transferRequestDto.getAmount(), transferRequestDto.getDescription());
        // doTransfer(destinationAccount, TypeTransactionEnum.INCOME, transferRequestDto.getAmount(), transferRequestDto.getDescription());

        return ResponseEntity.ok(TransferResponseDto.builder().taxCollected(iTaxDelegate.checkTax(transferRequestDto.getAmount())).build());
    }

    private void checkAccountConfig(AccountDto originAccount) throws TransferException {
        Optional<AccountConfigDto> config = originAccount.getAccountConfig().stream()
                .filter(limitTransferPredicate)
                .findFirst();
        if (config.isPresent() && Boolean.TRUE.equals(this.checkConfig.apply(originAccount, config.get())))
            throw new TransferException(ErrorEnum.LIMIT_EXCEEDED);
    }

    private void checkAmount(AccountDto originAccount, BigDecimal amount) throws TransferException {
        if (originAccount.getAmount().compareTo(amount) < 0)
            throw new TransferException(ErrorEnum.INSUFFICIENT_FUNDS);
    }

}
