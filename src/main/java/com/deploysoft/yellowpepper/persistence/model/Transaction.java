package com.deploysoft.yellowpepper.persistence.model;

import com.deploysoft.yellowpepper.domain.constant.TypeTransactionEnum;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
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

    private Long amount;

    @Enumerated(EnumType.STRING)
    private TypeTransactionEnum typeTransactionEnum;

    @Data
    @Embeddable
    public static class TransactionId implements Serializable {

        @ManyToOne
        @JoinColumn(name = "account", nullable = false)
        private Account account;

        private LocalDate date = LocalDate.now();

        private LocalTime time =  LocalTime.now();
    }

}