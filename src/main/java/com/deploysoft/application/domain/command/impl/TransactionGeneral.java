package com.deploysoft.application.domain.command.impl;

import com.deploysoft.application.domain.command.Command;
import com.deploysoft.application.domain.constant.TypeTransactionEnum;
import com.deploysoft.application.domain.dto.AccountDto;
import com.deploysoft.application.domain.dto.TransferRequestDto;
import com.deploysoft.application.infrastructure.services.ITransferService;
import com.deploysoft.application.persistence.model.Transaction;

import java.math.BigDecimal;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 22/09/20
 **/
public abstract class TransactionGeneral implements Command<TransferRequestDto> {

    private final ITransferService iTransferService;
    private AccountDto account;

    public TransactionGeneral(ITransferService iTransferService) {
        this.iTransferService = iTransferService;
    }

    public void createTransaction(TypeTransactionEnum outcome, BigDecimal amount, String description) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transaction.setTypeTransactionEnum(outcome);
        transaction.setId(Transaction.TransactionKey.builder().accountId(this.account.getId()).build());
        iTransferService.createTransaction(transaction);
    }

    public TransactionGeneral setAccount(AccountDto account) {
        this.account = account;
        return this;
    }

    public AccountDto getAccount() {
        return account;
    }
}
