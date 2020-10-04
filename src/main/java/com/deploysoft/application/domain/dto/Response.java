package com.deploysoft.application.domain.dto;

import com.deploysoft.application.domain.constant.StatusEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 19/09/20
 **/
@Data
@SuperBuilder
@NoArgsConstructor
public class Response {
    @Builder.Default
    private StatusEnum status = StatusEnum.OK;
    @Builder.Default
    private String[] errors = {};
}
