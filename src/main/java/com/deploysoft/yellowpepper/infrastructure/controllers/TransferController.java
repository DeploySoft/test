package com.deploysoft.yellowpepper.infrastructure.controllers;

import com.deploysoft.yellowpepper.infrastructure.services.rate.IExchangeRates;
import com.deploysoft.yellowpepper.infrastructure.services.rate.dto.RatesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 19/09/20
 **/
@RestController
public class TransferController {

    @Autowired
    private IExchangeRates iExchangeRates;


    @GetMapping("/test/{itemId}")
    public ResponseEntity getItem(@PathVariable("itemId") String itemId) {
        ResponseEntity<RatesResponse> item = iExchangeRates.getItem(itemId);
        RatesResponse body = item.getBody();

        return iExchangeRates.getItem(itemId);
    }

    @GetMapping("/items/{itemId}/children")
    public ResponseEntity getItemWithChildren(@PathVariable("itemId") String itemId) {
        return null;
    }


}
