package com.deploysoft.yellowpepper.dto;

import com.deploysoft.yellowpepper.domain.CurrencyEnum;
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
public class TransferDTO {
    private BigDecimal amount;
    private CurrencyEnum currency;
    private AccountDTO originAccount;
    private AccountDTO destinationAccount;
    private String description;
}
