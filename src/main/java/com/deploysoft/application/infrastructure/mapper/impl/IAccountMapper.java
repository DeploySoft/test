package com.deploysoft.application.infrastructure.mapper.impl;

import com.deploysoft.application.domain.dto.AccountDto;
import com.deploysoft.application.infrastructure.mapper.IMapperGeneric;
import com.deploysoft.application.persistence.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 20/09/20
 **/
@Mapper( uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface IAccountMapper extends IMapperGeneric<Account, AccountDto> {
}
