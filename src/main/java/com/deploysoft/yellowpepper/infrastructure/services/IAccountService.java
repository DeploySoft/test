package com.deploysoft.yellowpepper.infrastructure.services;

import com.deploysoft.yellowpepper.domain.dto.AccountDto;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 22/09/20
 **/
public interface IAccountService {
    Optional<AccountDto> findById(Long originAccount);

    AccountDto createAccount();
    void updateAmount(Long accountId, BigDecimal amount);
}
