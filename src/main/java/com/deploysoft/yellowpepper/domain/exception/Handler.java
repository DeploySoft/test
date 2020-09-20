package com.deploysoft.yellowpepper.domain.exception;

import com.deploysoft.yellowpepper.domain.constant.StatusEnum;
import com.deploysoft.yellowpepper.domain.dto.Response;
import com.deploysoft.yellowpepper.domain.dto.TransferResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.math.BigDecimal;

/**
 * @author : J. Andrés Boyacá Silva
 * @since : 8/11/2020, Tue
 **/
@ControllerAdvice
public class Handler {

    @ExceptionHandler(TransferException.class)
    public ResponseEntity<Response> handlerFeignException(TransferException ex) {
        TransferResponseDto error = TransferResponseDto.builder()
                .status(StatusEnum.ERROR)
                .errors(new String[]{ex.getError().getMessage()})
                .taxCollected(new BigDecimal("0.00")).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
}
