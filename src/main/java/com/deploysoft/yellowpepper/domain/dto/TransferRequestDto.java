package com.deploysoft.yellowpepper.domain.dto;

import com.deploysoft.yellowpepper.domain.constant.CurrencyEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 19/09/20
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequestDto {
    private BigDecimal amount;
    private CurrencyEnum currency;
    private AccountDto originAccount;
    private AccountDto destinationAccount;
    private String description;
}
