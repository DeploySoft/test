package com.deploysoft.yellowpepper.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 19/09/20
 **/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AccountDto extends Response {

    @JsonProperty("account")
    private Long id;

    @JsonProperty("account_balance")
    private BigDecimal amount;

}
