package com.deploysoft.yellowpepper.domain.command.impl;

import com.deploysoft.yellowpepper.domain.command.Command;
import com.deploysoft.yellowpepper.domain.constant.TypeTransactionEnum;
import com.deploysoft.yellowpepper.domain.dto.AccountDto;
import com.deploysoft.yellowpepper.domain.dto.TransferRequestDto;
import com.deploysoft.yellowpepper.domain.dto.TransferResponseDto;
import com.deploysoft.yellowpepper.infrastructure.services.IAccountService;
import com.deploysoft.yellowpepper.infrastructure.services.ITransferService;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 21/09/20
 **/
public class TransactionInCommand extends TransactionGeneral implements Command<TransferRequestDto> {

    private final IAccountService iAccountService;

    public TransactionInCommand(IAccountService iAccountService, ITransferService iTransferService) {
        super(iTransferService);
        this.iAccountService = iAccountService;
    }

    @Override
    public void execute(TransferRequestDto input) {
        AccountDto account = super.getAccount();
        iAccountService.updateAmount(account.getId(), account.getAmount().add(input.getAmount()));
        super.createTransaction(TypeTransactionEnum.INCOME, input.getAmount(), input.getDescription());
    }


}
