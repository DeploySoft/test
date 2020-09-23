package com.deploysoft.yellowpepper.infrastructure.services;

import com.deploysoft.yellowpepper.domain.constant.TypeTransactionEnum;

import java.time.LocalDate;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 23/09/20
 **/
public interface ITransferService {

    long countTransactionsPerDay(Long accountId, LocalDate date, TypeTransactionEnum typeTransactionEnum);
}
