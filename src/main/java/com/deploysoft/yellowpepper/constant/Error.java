package com.deploysoft.yellowpepper.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 19/09/20
 **/
@Getter
@AllArgsConstructor
public enum Error {
    INSUFFICIENT_FUNDS("insufficient-funds"),
    LIMIT_EXCEEDED("limit_exceeded");
    private final String message;

}
