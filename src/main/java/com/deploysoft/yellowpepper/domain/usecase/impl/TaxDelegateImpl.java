package com.deploysoft.yellowpepper.domain.usecase.impl;

import com.deploysoft.yellowpepper.domain.config.TaxConfig;
import com.deploysoft.yellowpepper.domain.usecase.ITaxDelegate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 21/09/20
 **/
@Component
public class TaxDelegateImpl implements ITaxDelegate {

    private final TaxConfig taxConfig;

    public TaxDelegateImpl(TaxConfig taxConfig) {
        this.taxConfig = taxConfig;
    }

    @Override
    public BigDecimal checkTax(BigDecimal amount) {
        if (amount.compareTo(new BigDecimal(1000)) >= 0) {
            return amount.divide(new BigDecimal(100)).multiply(taxConfig.getTaxGreaterThan1000());
        } else {
            return amount.divide(new BigDecimal(100)).multiply(taxConfig.getNormalTax());
        }
    }
}
