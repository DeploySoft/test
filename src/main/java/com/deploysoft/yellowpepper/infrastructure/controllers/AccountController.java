package com.deploysoft.yellowpepper.infrastructure.controllers;

import com.deploysoft.yellowpepper.domain.dto.AccountDto;
import com.deploysoft.yellowpepper.domain.usecase.IAccountDelegate;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 19/09/20
 **/
@RestController
@AllArgsConstructor
public class AccountController {

    private final IAccountDelegate iAccountDelegate;

    @PostMapping("/account")
    public ResponseEntity<AccountDto> createAccount() {
        return iAccountDelegate.createAccount();
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<AccountDto> createAccount(@PathVariable("accountId") Long accountId) {
        return iAccountDelegate.checkAccount(accountId);
    }

}
