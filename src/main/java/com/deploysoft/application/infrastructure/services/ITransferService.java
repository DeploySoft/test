package com.deploysoft.application.infrastructure.services;

import com.deploysoft.application.domain.constant.TypeTransactionEnum;
import com.deploysoft.application.persistence.model.Transaction;

import java.time.LocalDate;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 23/09/20
 **/
public interface ITransferService {

    long countTransactionsPerDay(Long accountId, LocalDate date, TypeTransactionEnum typeTransactionEnum);

    void createTransaction(Transaction transaction);
}
