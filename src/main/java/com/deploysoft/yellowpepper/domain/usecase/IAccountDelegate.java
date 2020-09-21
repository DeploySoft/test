package com.deploysoft.yellowpepper.domain.usecase;

import com.deploysoft.yellowpepper.domain.dto.AccountDto;
import com.deploysoft.yellowpepper.persistence.model.Account;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 20/09/20
 **/
public interface IAccountDelegate {

    ResponseEntity<AccountDto> createAccount();

    ResponseEntity<AccountDto> checkAccount(Long accountId);

    Optional<Account> getAccount(Long accountId);

    void updateAmount(Account account, BigDecimal amount);
}
