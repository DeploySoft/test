package com.deploysoft.yellowpepper.infrastructure.controllers;

import com.deploysoft.yellowpepper.domain.dto.TransferRequestDto;
import com.deploysoft.yellowpepper.domain.dto.TransferResponseDto;
import com.deploysoft.yellowpepper.domain.exception.TransferException;
import com.deploysoft.yellowpepper.domain.usecase.ITransferDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 19/09/20
 **/
@RestController
public class TransferController {

    @Autowired
    private ITransferDelegate iTransferDelegate;


    @PostMapping("/transfer")
    public ResponseEntity<TransferResponseDto> getItem(@Valid @RequestBody TransferRequestDto transferRequestDto) throws TransferException {
        return iTransferDelegate.doTransfer(transferRequestDto);
    }


}
