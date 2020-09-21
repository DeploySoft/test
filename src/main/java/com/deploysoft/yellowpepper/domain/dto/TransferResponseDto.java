package com.deploysoft.yellowpepper.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 19/09/20
 **/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TransferResponseDto extends  Response{

    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
    private BigDecimal taxCollected;
}
