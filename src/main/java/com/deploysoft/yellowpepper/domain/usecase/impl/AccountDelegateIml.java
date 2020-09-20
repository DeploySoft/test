package com.deploysoft.yellowpepper.domain.usecase.impl;

import com.deploysoft.yellowpepper.domain.dto.AccountDto;
import com.deploysoft.yellowpepper.domain.dto.Response;
import com.deploysoft.yellowpepper.domain.usecase.IAccountDelegate;
import com.deploysoft.yellowpepper.infrastructure.mapper.impl.IAccountMapper;
import com.deploysoft.yellowpepper.persistence.model.Account;
import com.deploysoft.yellowpepper.persistence.repositories.IAccountRepository;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 20/09/20
 **/
@Component
@AllArgsConstructor
public class AccountDelegateIml implements IAccountDelegate {

    private final IAccountRepository iAccountRepository;
    private final IAccountMapper iAccountMapper;


    @Override
    public ResponseEntity<AccountDto> createAccount() {
        Account account = new Account();
        account.setId(generateUniqueId());
        Account saved = iAccountRepository.save(account);
        return ResponseEntity.ok(iAccountMapper.modelToDTO(saved));
    }

    @Override
    public ResponseEntity<AccountDto> checkAccount(Long accountId) {
        return iAccountRepository.findById(accountId)
                .map(value -> ResponseEntity.ok(iAccountMapper.modelToDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private Long generateUniqueId() {
        long val = -1;
        do {
            val = UUID.randomUUID().getMostSignificantBits();
        } while (val < 0);
        return val;
    }
}
