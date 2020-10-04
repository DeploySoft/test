package com.deploysoft.application.domain.dto;

import com.deploysoft.application.domain.constant.TypeConfigEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
