package com.deploysoft.yellowpepper.domain.usecase.impl;

import com.deploysoft.yellowpepper.domain.config.TaxConfig;
import com.deploysoft.yellowpepper.domain.constant.CurrencyEnum;
import com.deploysoft.yellowpepper.domain.constant.ErrorEnum;
import com.deploysoft.yellowpepper.domain.usecase.ITaxDelegate;
import com.deploysoft.yellowpepper.infrastructure.services.rate.IExchangeRates;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 21/09/20
 **/
@Component
@AllArgsConstructor
public class TaxDelegateImpl implements ITaxDelegate {

    private final TaxConfig taxConfig;
    private final IExchangeRates iExchangeRates;

    @Override
    public Either<Void,BigDecimal> checkTax(BigDecimal amount) {
        if (amount.compareTo(new BigDecimal(1000)) >= 0) {
            return Either.right(amount.divide(new BigDecimal(100)).multiply(taxConfig.getTaxGreaterThan1000()));
        } else {
            return Either.right(amount.divide(new BigDecimal(100)).multiply(taxConfig.getNormalTax()));
        }
    }

    public Either<ErrorEnum, BigDecimal> convertAmount(CurrencyEnum currencyIn, CurrencyEnum currencyOut, BigDecimal amount) {
        return Option.ofOptional(this.iExchangeRates.getItem(currencyIn.name()).getRates().entrySet().stream()
                .filter(key -> currencyOut.name().equals(key.getKey()))
                .map(Map.Entry::getValue)
                .findFirst())
                .toEither(ErrorEnum.INVALID_CURRENCY)
                .map(tax -> convert(amount.multiply(tax).doubleValue(), 10000));
    }

    private BigDecimal convert(double num, int till) {
        return BigDecimal.valueOf((num * till / (double) till));
    }
}
