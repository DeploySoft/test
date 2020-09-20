package com.deploysoft.yellowpepper.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 19/09/20
 **/
@Builder
@AllArgsConstructor
public class Response {
    private String status;
    private String[] errors;
}
