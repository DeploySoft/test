package com.deploysoft.application.domain.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 20/09/20
 **/
@Getter
@Setter
@Configuration
@ConfigurationProperties("app.tax")
public class TaxConfig {
    private BigDecimal taxGreaterThan1000;
    private BigDecimal normalTax;
}
