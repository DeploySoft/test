package com.deploysoft.yellowpepper.infrastructure.mapper.impl;

import com.deploysoft.yellowpepper.domain.dto.AccountDto;
import com.deploysoft.yellowpepper.infrastructure.mapper.IMapperGeneric;
import com.deploysoft.yellowpepper.persistence.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 20/09/20
 **/
@Mapper( uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface IAccountMapper extends IMapperGeneric<Account, AccountDto> {
}
