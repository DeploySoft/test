package com.deploysoft.application.domain.exception;

import com.deploysoft.application.domain.constant.StatusEnum;
import com.deploysoft.application.domain.dto.Response;
import com.deploysoft.application.domain.dto.TransferResponseDto;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.math.BigDecimal;

/**
 * @author : J. Andrés Boyacá Silva
 * @since : 8/11/2020, Tue
 **/
@Slf4j
@ControllerAdvice
public class Handler {

    @ExceptionHandler(TransferException.class)
    public ResponseEntity<Response> handlerTransferException(TransferException ex) {
        TransferResponseDto error = TransferResponseDto.builder()
                .status(StatusEnum.ERROR)
                .errors(new String[]{ex.getError().getMessage()})
                .taxCollected(new BigDecimal("0.00")).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Response> handlerFeignException(TransferException ex) {
        TransferResponseDto error = TransferResponseDto.builder()
                .status(StatusEnum.ERROR)
                .errors(new String[]{ex.getError().getMessage()})
                .taxCollected(new BigDecimal("0.00")).build();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handlerException(Exception ex) {
        log.error(ex.getLocalizedMessage());
        Response error = Response.builder()
                .status(StatusEnum.ERROR)
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

}
