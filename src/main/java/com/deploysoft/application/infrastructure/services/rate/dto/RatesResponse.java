package com.deploysoft.application.infrastructure.services.rate.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RatesResponse {
    private LinkedHashMap<String, BigDecimal> rates;
    private String base;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date;
}
