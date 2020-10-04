package com.deploysoft.application.domain.usecase;

import com.deploysoft.application.domain.dto.TransferRequestDto;
import com.deploysoft.application.domain.exception.TransferException;
import com.deploysoft.application.domain.dto.TransferResponseDto;
import org.springframework.http.ResponseEntity;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 19/09/20
 **/
public interface ITransferDelegate {
    ResponseEntity<TransferResponseDto> doTransfer(TransferRequestDto transferRequestDto) throws TransferException;
}
