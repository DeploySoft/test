package com.deploysoft.yellowpepper.domain.usecase;

import com.deploysoft.yellowpepper.domain.constant.CurrencyEnum;
import com.deploysoft.yellowpepper.domain.constant.ErrorEnum;
import io.vavr.control.Either;

import java.math.BigDecimal;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 21/09/20
 **/
public interface ITaxDelegate {
    BigDecimal checkTax(BigDecimal amount);

    Either<ErrorEnum, BigDecimal> convertAmount(CurrencyEnum currencyIn, CurrencyEnum currencyOut, BigDecimal amount);
}
