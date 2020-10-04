package com.deploysoft.application.domain.dto;

import com.deploysoft.application.domain.constant.CurrencyEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 19/09/20
 **/
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TransferResponseDto extends Response {

    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
    private BigDecimal taxCollected;

    @JsonIgnore
    private Map<String, Object> objectMap;

    public  Map<String, Object> setObjectMap (CurrencyEnum currency, BigDecimal value) {
        Map<String, Object> mapped = toMap();
        mapped.put(currency.name(), value);
        return mapped;
    }

    private Map<String, Object> toMap() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        return mapper.convertValue(this, new TypeReference<Map<String, Object>>() {
        });
    }

}
