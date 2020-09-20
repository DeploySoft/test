package com.deploysoft.yellowpepper.domain.usecase;

import com.deploysoft.yellowpepper.domain.dto.TransferRequestDto;
import com.deploysoft.yellowpepper.domain.dto.TransferResponseDto;
import org.springframework.http.ResponseEntity;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 19/09/20
 **/
public interface ITransferDelegate {
    ResponseEntity<TransferResponseDto> doTransfer(TransferRequestDto transferRequestDto);
}
