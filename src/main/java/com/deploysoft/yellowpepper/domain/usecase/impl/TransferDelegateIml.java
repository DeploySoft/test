package com.deploysoft.yellowpepper.domain.usecase.impl;

import com.deploysoft.yellowpepper.domain.config.TaxConfig;
import com.deploysoft.yellowpepper.domain.constant.ErrorEnum;
import com.deploysoft.yellowpepper.domain.constant.TypeConfigEnum;
import com.deploysoft.yellowpepper.domain.constant.TypeTransactionEnum;
import com.deploysoft.yellowpepper.domain.dto.TransferRequestDto;
import com.deploysoft.yellowpepper.domain.dto.TransferResponseDto;
import com.deploysoft.yellowpepper.domain.exception.TransferException;
import com.deploysoft.yellowpepper.domain.usecase.IAccountDelegate;
import com.deploysoft.yellowpepper.domain.usecase.ITransferDelegate;
import com.deploysoft.yellowpepper.persistence.model.Account;
import com.deploysoft.yellowpepper.persistence.model.AccountConfig;
import com.deploysoft.yellowpepper.persistence.repositories.ITransferRepository;
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
public class TransferDelegateIml implements ITransferDelegate {

    private final ITransferRepository iTransferRepository;
    private final IAccountDelegate iAccountDelegate;
    private final TaxConfig taxConfig;

    private final Predicate<AccountConfig> limitTransferPredicate;
    private final BiFunction<Account, AccountConfig, Boolean> checkConfig;

    public TransferDelegateIml(ITransferRepository iTransferRepository,
                               IAccountDelegate iAccountDelegate,
                               TaxConfig taxConfig) {
        this.iTransferRepository = iTransferRepository;
        this.taxConfig = taxConfig;
        this.iAccountDelegate = iAccountDelegate;
        this.limitTransferPredicate = accountConfig -> TypeConfigEnum.LIMIT_TRANSFER_PER_DAY.equals(accountConfig.getAccountConfigId().getTypeConfigEnum());
        this.checkConfig = (account, accountConfig) -> {
            long transactions = this.iTransferRepository.countDistinctByIdAccountIdAndIdDateAndTypeTransactionEnum(account.getId(), LocalDate.now(), TypeTransactionEnum.OUTCOME);
            return transactions > Integer.parseInt(accountConfig.getValue());
        };
    }

    @Override
    public ResponseEntity<TransferResponseDto> doTransfer(TransferRequestDto transferRequestDto) throws TransferException {

        Account originAccount = iAccountDelegate.getAccount(transferRequestDto.getOriginAccount())
                .orElseThrow(() -> new TransferException(ErrorEnum.INVALID_ACCOUNT_ORIGIN));
        Account destinationAccount = iAccountDelegate.getAccount(transferRequestDto.getDestinationAccount())
                .orElseThrow(() -> new TransferException(ErrorEnum.INVALID_ACCOUNT_DESTINATION));

        checkAccountConfig(originAccount);
        checkAmount(originAccount, checkTax(transferRequestDto.getAmount()));

        doTransfer(originAccount, TypeTransactionEnum.OUTCOME, transferRequestDto.getAmount());
        doTransfer(destinationAccount, TypeTransactionEnum.INCOME, transferRequestDto.getAmount());

        return null;
    }

    private void doTransfer(Account originAccount, TypeTransactionEnum outcome, BigDecimal amount) {
        amount = TypeTransactionEnum.OUTCOME.equals(outcome) ? amount.negate() : amount.plus();
        iAccountDelegate.updateAmount(originAccount, amount);
    }

    private void checkAccountConfig(Account originAccount) throws TransferException {
        Optional<AccountConfig> config = originAccount.getAccountConfig().stream()
                .filter(limitTransferPredicate)
                .findFirst();
        if (config.isPresent() &&
                Boolean.TRUE.equals(this.checkConfig.apply(originAccount, config.get()))) {
            throw new TransferException(ErrorEnum.LIMIT_EXCEEDED);
        }
    }

    private void checkAmount(Account originAccount, BigDecimal amount) throws TransferException {
        if (originAccount.getAmount().compareTo(amount) < 0) {
            throw new TransferException(ErrorEnum.INSUFFICIENT_FUNDS);
        }
    }

    private BigDecimal checkTax(BigDecimal amount) {
        if (amount.compareTo(new BigDecimal(1000)) > 0) {
            return amount.add(amount.divide(new BigDecimal(100)).multiply(taxConfig.getTaxGreaterThan1000()));
        } else {
            return amount.add(amount.divide(new BigDecimal(100)).multiply(taxConfig.getNormalTax()));
        }
    }

}
