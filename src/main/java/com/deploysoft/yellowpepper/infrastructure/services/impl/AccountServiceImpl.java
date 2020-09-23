package com.deploysoft.yellowpepper.infrastructure.services.impl;

import com.deploysoft.yellowpepper.domain.constant.TypeConfigEnum;
import com.deploysoft.yellowpepper.domain.dto.AccountDto;
import com.deploysoft.yellowpepper.infrastructure.mapper.impl.IAccountMapper;
import com.deploysoft.yellowpepper.infrastructure.services.IAccountService;
import com.deploysoft.yellowpepper.persistence.model.Account;
import com.deploysoft.yellowpepper.persistence.model.AccountConfig;
import com.deploysoft.yellowpepper.persistence.repositories.IAccountRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 22/09/20
 **/
@Service
public class AccountServiceImpl implements IAccountService {


    private final IAccountMapper iAccountMapper;
    private final IAccountRepository iAccountRepository;

    public AccountServiceImpl(IAccountMapper iAccountMapper, IAccountRepository iAccountRepository) {
        this.iAccountMapper = iAccountMapper;
        this.iAccountRepository = iAccountRepository;
    }

    @Override
    public Optional<AccountDto> findById(Long originAccount) {
        return iAccountRepository.findById(originAccount).flatMap(account -> Optional.of(iAccountMapper.modelToDTO(account)));
    }

    @Override
    public AccountDto createAccount() {
        Account account = new Account();
        account.setId(generateUniqueId());
        account.setAccountConfig(getDefaultConfig(account));
        Account saved = iAccountRepository.save(account);
        return iAccountMapper.modelToDTO(saved);
    }

    private Long generateUniqueId() {
        long val;
        do {
            val = UUID.randomUUID().getMostSignificantBits();
        } while (val < 0L);
        return val;
    }

    private List<AccountConfig> getDefaultConfig(Account account) {
        AccountConfig limitTransactionConfig = new AccountConfig();
        limitTransactionConfig.setValue("3");
        limitTransactionConfig.setId(new AccountConfig.AccountConfigKey(account.getId(), TypeConfigEnum.LIMIT_TRANSFER_PER_DAY));
        return Collections.singletonList(limitTransactionConfig);
    }
}
