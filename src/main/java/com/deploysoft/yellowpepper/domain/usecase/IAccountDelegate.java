package com.deploysoft.yellowpepper.domain.usecase;

import com.deploysoft.yellowpepper.domain.dto.AccountDto;
import org.springframework.http.ResponseEntity;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 20/09/20
 **/
public interface IAccountDelegate {

    ResponseEntity<AccountDto> createAccount();

    ResponseEntity<AccountDto> checkAccount(Long accountId);

}
