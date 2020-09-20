package com.deploysoft.yellowpepper.domain.usecase.impl;

import com.deploysoft.yellowpepper.domain.config.TaxConfig;
import com.deploysoft.yellowpepper.domain.constant.ErrorEnum;
import com.deploysoft.yellowpepper.domain.constant.TypeConfigEnum;
import com.deploysoft.yellowpepper.domain.constant.TypeTransactionEnum;
import com.deploysoft.yellowpepper.domain.dto.TransferRequestDto;
import com.deploysoft.yellowpepper.domain.dto.TransferResponseDto;
import com.deploysoft.yellowpepper.domain.exception.TransferException;
import com.deploysoft.yellowpepper.domain.usecase.ITransferDelegate;
import com.deploysoft.yellowpepper.persistence.model.Account;
import com.deploysoft.yellowpepper.persistence.model.AccountConfig;
import com.deploysoft.yellowpepper.persistence.repositories.IAccountRepository;
import com.deploysoft.yellowpepper.persistence.repositories.ITransferRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 20/09/20
 **/
@Component
public class TransferDelegateIml implements ITransferDelegate {

    private final ITransferRepository iTransferRepository;
    private final IAccountRepository iAccountRepository;
    private final Predicate<AccountConfig> limitTransferPredicate;
    private final TaxConfig taxConfig;

    public TransferDelegateIml(ITransferRepository iTransferRepository,
                               IAccountRepository iAccountRepository,
                               TaxConfig taxConfig) {
        this.iTransferRepository = iTransferRepository;
        this.iAccountRepository = iAccountRepository;
        this.taxConfig = taxConfig;
        this.limitTransferPredicate = accountConfig -> TypeConfigEnum.LIMIT_TRANSFER_PER_DAY.equals(accountConfig.getAccountConfigId().getTypeConfigEnum());
    }

    @Override
    public ResponseEntity<TransferResponseDto> doTransfer(TransferRequestDto transferRequestDto) throws TransferException {
        Optional<Account> originAccount = iAccountRepository.findById(transferRequestDto.getOriginAccount());
        Optional<Account> destinationAccount = iAccountRepository.findById(transferRequestDto.getDestinationAccount());

        checkAccounts(originAccount, destinationAccount);
        checkAccountConfig(originAccount);
        checkAmount(originAccount, checkTax(transferRequestDto.getAmount()));


        return null;
    }

    private void checkAccountConfig(Optional<Account> originAccount) throws TransferException {
        originAccount.flatMap(account -> account.getAccountConfig().stream()
                .filter(limitTransferPredicate)
                .findFirst()
                .flatMap(accountConfig -> {
                    long transactions = iTransferRepository.countDistinctByIdAccountIdAndIdDateAndTypeTransactionEnum(account.getId(), LocalDate.now(), TypeTransactionEnum.OUTCOME);
                    return (transactions >= Integer.parseInt(accountConfig.getValue())) ? Optional.empty() : Optional.of(true);
                })).orElseThrow(() -> new TransferException(ErrorEnum.LIMIT_EXCEEDED));
    }

    private void checkAmount(Optional<Account> originAccount, BigDecimal amount) throws TransferException {
        originAccount.filter(account -> account.getAmount().compareTo(amount) > 0)
                .orElseThrow(() -> new TransferException(ErrorEnum.INSUFFICIENT_FUNDS));
    }

    private void checkAccounts(Optional<Account> originAccount, Optional<Account> destinationAccount) throws TransferException {
        if (!originAccount.isPresent())
            throw new TransferException(ErrorEnum.INVALID_ACCOUNT_ORIGIN);
        if (!destinationAccount.isPresent())
            throw new TransferException(ErrorEnum.INVALID_ACCOUNT_DESTINATION);
    }

    private BigDecimal checkTax(BigDecimal amount) {
        if (amount.compareTo(new BigDecimal(1000)) > 0) {
            return amount.add(amount.divide(new BigDecimal(100)).multiply(taxConfig.getTaxGreaterThan1000()));
        }else{
            return amount.add(amount.divide(new BigDecimal(100)).multiply(taxConfig.getNormalTax()));
        }
    }

}
