package com.deploysoft.application.infrastructure.services.rate;

import com.deploysoft.application.infrastructure.services.rate.dto.RatesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = "ExchangeRate", url = "${app.urls.rates}")
public interface IExchangeRates {
    /**
     * @param base item value
     * @return {@link RatesResponse}
     */
    @GetMapping(value = "/latest?base={base}", produces = APPLICATION_JSON_VALUE)
    RatesResponse getItem(@PathVariable(value = "base") String base);

}
