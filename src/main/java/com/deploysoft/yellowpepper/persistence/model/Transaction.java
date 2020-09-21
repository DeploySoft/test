package com.deploysoft.yellowpepper.persistence.model;

import com.deploysoft.yellowpepper.domain.constant.TypeTransactionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 19/09/20
 **/
@Data
@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {

    @Id
    @EmbeddedId
    private TransactionId id;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TypeTransactionEnum typeTransactionEnum;

    private String description;

    @Data
    @Builder
    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TransactionId implements Serializable {

        @ManyToOne
        @JoinColumn(name = "account", nullable = false)
        private Account account;

        @Builder.Default
        private LocalDate date = LocalDate.now();

        @Builder.Default
        private LocalTime time =  LocalTime.now();
    }

}