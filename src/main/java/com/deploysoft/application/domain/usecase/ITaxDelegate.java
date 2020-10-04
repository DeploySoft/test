package com.deploysoft.application.domain.usecase;

import com.deploysoft.application.domain.constant.CurrencyEnum;
import com.deploysoft.application.domain.constant.ErrorEnum;
import io.vavr.control.Either;

import java.math.BigDecimal;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 21/09/20
 **/
public interface ITaxDelegate {
    Either<Void,BigDecimal> checkTax(BigDecimal amount);

    Either<ErrorEnum, BigDecimal> convertAmount(CurrencyEnum currencyIn, CurrencyEnum currencyOut, BigDecimal amount);
}
