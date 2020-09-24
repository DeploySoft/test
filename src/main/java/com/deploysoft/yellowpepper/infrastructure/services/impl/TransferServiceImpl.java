package com.deploysoft.yellowpepper.infrastructure.services.impl;

import com.deploysoft.yellowpepper.domain.constant.TypeTransactionEnum;
import com.deploysoft.yellowpepper.infrastructure.services.ITransferService;
import com.deploysoft.yellowpepper.persistence.model.Transaction;
import com.deploysoft.yellowpepper.persistence.repositories.ITransferRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 23/09/20
 **/
@Service
@AllArgsConstructor
public class TransferServiceImpl implements ITransferService {
    private final ITransferRepository iTransferRepository;

    @Override
    public long countTransactionsPerDay(Long accountId, LocalDate date, TypeTransactionEnum typeTransactionEnum) {
        return this.iTransferRepository.countDistinctByIdAccountIdAndIdDateAndTypeTransactionEnum(accountId, LocalDate.now(), TypeTransactionEnum.OUTCOME);
    }

    @Override
    public void createTransaction(Transaction transaction) {
        this.iTransferRepository.save(transaction);
    }
}
