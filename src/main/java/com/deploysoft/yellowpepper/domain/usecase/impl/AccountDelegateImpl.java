package com.deploysoft.yellowpepper.domain.usecase.impl;

import com.deploysoft.yellowpepper.domain.dto.AccountDto;
import com.deploysoft.yellowpepper.domain.usecase.IAccountDelegate;
import com.deploysoft.yellowpepper.infrastructure.services.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 20/09/20
 **/
@Component
@AllArgsConstructor
public class AccountDelegateImpl implements IAccountDelegate {

    private final IAccountService iAccountService;

    @Override
    public ResponseEntity<AccountDto> createAccount() {
        return ResponseEntity.ok(iAccountService.createAccount());
    }

    @Override
    public ResponseEntity<AccountDto> checkAccount(Long accountId) {
        return iAccountService.findById(accountId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
