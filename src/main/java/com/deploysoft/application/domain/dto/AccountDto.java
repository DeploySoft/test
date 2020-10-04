package com.deploysoft.application.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 19/09/20
 **/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AccountDto extends Response {

    @JsonProperty("account")
    private Long id;

    @JsonProperty("account_balance")
    private BigDecimal amount;

    @JsonIgnore
    private BigDecimal amount_converted;

    @JsonIgnore
    private List<AccountConfigDto> accountConfig;

}
