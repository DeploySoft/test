package com.deploysoft.yellowpepper.domain.usecase.impl;

import com.deploysoft.yellowpepper.domain.constant.TypeConfigEnum;
import com.deploysoft.yellowpepper.domain.dto.AccountDto;
import com.deploysoft.yellowpepper.domain.usecase.IAccountDelegate;
import com.deploysoft.yellowpepper.infrastructure.mapper.impl.IAccountMapper;
import com.deploysoft.yellowpepper.persistence.model.Account;
import com.deploysoft.yellowpepper.persistence.model.AccountConfig;
import com.deploysoft.yellowpepper.persistence.repositories.IAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 20/09/20
 **/
@Component
@AllArgsConstructor
public class AccountDelegateImpl implements IAccountDelegate {

    private final IAccountRepository iAccountRepository;
    private final IAccountMapper iAccountMapper;

    @Override
    public ResponseEntity<AccountDto> createAccount() {
        Account account = new Account();
        account.setId(generateUniqueId());
        account.setAccountConfig(getDefaultConfig(account));
        Account saved = iAccountRepository.save(account);
        return ResponseEntity.ok(iAccountMapper.modelToDTO(saved));
    }

    @Override
    public ResponseEntity<AccountDto> checkAccount(Long accountId) {
        return iAccountRepository.findById(accountId)
                .map(value -> ResponseEntity.ok(iAccountMapper.modelToDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public Optional<Account> getAccount(Long accountId){
        return iAccountRepository.findById(accountId);
    }

    @Override
    public void updateAmount(Account account, BigDecimal amount) {
        account.setAmount(account.getAmount().add(amount));
        iAccountRepository.save(account);
    }

    private Long generateUniqueId() {
        long val = -1;
        do {
            val = UUID.randomUUID().getMostSignificantBits();
        } while (val < 0);
        return val;
    }

    private List<AccountConfig> getDefaultConfig(Account account) {
        AccountConfig.AccountConfigId accountConfigId = new AccountConfig.AccountConfigId();
        accountConfigId.setAccount(account);
        accountConfigId.setTypeConfigEnum(TypeConfigEnum.LIMIT_TRANSFER_PER_DAY);

        AccountConfig limitTransactionConfig = new AccountConfig();
        limitTransactionConfig.setValue("3");
        limitTransactionConfig.setAccountConfigId(accountConfigId);

        return Collections.singletonList(limitTransactionConfig);
    }
}
