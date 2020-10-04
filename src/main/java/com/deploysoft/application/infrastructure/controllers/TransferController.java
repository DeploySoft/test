package com.deploysoft.application.infrastructure.controllers;

import com.deploysoft.application.domain.dto.TransferRequestDto;
import com.deploysoft.application.domain.exception.TransferException;
import com.deploysoft.application.domain.usecase.ITransferDelegate;
import com.deploysoft.application.domain.dto.TransferResponseDto;
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
