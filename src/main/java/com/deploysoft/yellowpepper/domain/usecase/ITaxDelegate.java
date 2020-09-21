package com.deploysoft.yellowpepper.domain.usecase;

import java.math.BigDecimal;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 21/09/20
 **/
public interface ITaxDelegate {
    BigDecimal checkTax(BigDecimal amount);
}
