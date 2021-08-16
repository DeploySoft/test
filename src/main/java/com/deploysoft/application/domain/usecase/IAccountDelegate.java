package com.deploysoft.application.domain.usecase;

import com.deploysoft.application.domain.dto.AccountDto;
import org.springframework.http.ResponseEntity;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 20/09/20
 **/
public interface IAccountDelegate {

    //FIXME Remove coupling
    ResponseEntity<AccountDto> createAccount();

    //FIXME Remove coupling
    ResponseEntity<AccountDto> checkAccount(Long accountId);

}
