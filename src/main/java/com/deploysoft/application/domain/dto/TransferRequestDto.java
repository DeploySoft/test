package com.deploysoft.application.domain.dto;

import com.deploysoft.application.domain.constant.CurrencyEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 19/09/20
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequestDto {

    @NotNull
    private BigDecimal amount;
    private BigDecimal tax;
    @NotNull
    private CurrencyEnum currency;
    @NotNull
    private Long originAccount;
    @NotNull
    private Long destinationAccount;
    private String description;
}

