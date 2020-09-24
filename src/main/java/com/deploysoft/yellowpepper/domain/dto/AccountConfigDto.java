package com.deploysoft.yellowpepper.domain.dto;

import com.deploysoft.yellowpepper.domain.constant.TypeConfigEnum;
import com.deploysoft.yellowpepper.persistence.model.Account;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 19/09/20
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountConfigDto {
    private AccountConfigKeyDto id;
    private String value;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountConfigKeyDto {
        private TypeConfigEnum typeConfigEnum;
        private Long accountId;
    }
}
