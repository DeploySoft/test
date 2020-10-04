package com.deploysoft.application.domain.command.impl;

import com.deploysoft.application.domain.command.Command;
import com.deploysoft.application.domain.constant.TypeTransactionEnum;
import com.deploysoft.application.domain.dto.AccountDto;
import com.deploysoft.application.domain.dto.TransferRequestDto;
import com.deploysoft.application.infrastructure.services.IAccountService;
import com.deploysoft.application.infrastructure.services.ITransferService;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 21/09/20
 **/
public class TransactionOutCommand extends TransactionGeneral implements Command<TransferRequestDto> {

    private final IAccountService iAccountService;

    public TransactionOutCommand(IAccountService iAccountService, ITransferService iTransferService) {
        super(iTransferService);
        this.iAccountService = iAccountService;
    }

    @Override
    public void execute(TransferRequestDto input) {
        AccountDto account = super.getAccount();
        iAccountService.updateAmount(account.getId(), account.getAmount().subtract(input.getAmount()).subtract(input.getTax()));
        super.createTransaction(TypeTransactionEnum.OUTCOME, input.getAmount(), input.getDescription());
    }


}
