package com.deploysoft.yellowpepper.domain.exception;

import com.deploysoft.yellowpepper.domain.constant.ErrorEnum;
import lombok.Getter;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 20/09/20
 **/

@Getter
public class TaxException extends Exception {
    private ErrorEnum error;

    public TaxException(ErrorEnum error) {
        super(error.getMessage());
        this.error = error;
    }
}
