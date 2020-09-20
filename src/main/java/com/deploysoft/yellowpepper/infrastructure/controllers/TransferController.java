package com.deploysoft.yellowpepper.infrastructure.controllers;

import com.deploysoft.yellowpepper.domain.dto.TransferRequestDto;
import com.deploysoft.yellowpepper.domain.exception.TransferException;
import com.deploysoft.yellowpepper.domain.usecase.ITransferDelegate;
import com.deploysoft.yellowpepper.infrastructure.services.rate.IExchangeRates;
import com.deploysoft.yellowpepper.infrastructure.services.rate.dto.RatesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 19/09/20
 **/
@RestController
public class TransferController {

    @Autowired
    private IExchangeRates iExchangeRates;

    @Autowired
    private ITransferDelegate iTransferDelegate;


    @PostMapping("/transfer")
    public ResponseEntity getItem(@Valid @RequestBody TransferRequestDto transferRequestDto) throws TransferException {
        iTransferDelegate.doTransfer(transferRequestDto);
        return null;
    }

    @GetMapping("/items/{itemId}")
    public ResponseEntity getItemWithChildren(@PathVariable("itemId") String itemId) {
        ResponseEntity<RatesResponse> item = iExchangeRates.getItem(itemId);
        RatesResponse body = item.getBody();

        return iExchangeRates.getItem(itemId);
    }


}
