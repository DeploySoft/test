package com.deploysoft.yellowpepper.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 19/09/20
 **/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AccountDto extends Response {

    @NotNull
    @JsonProperty("account")
    private Long id;

    @JsonProperty("account_balance")
    private Long amount;

}
