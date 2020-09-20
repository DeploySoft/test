package com.deploysoft.yellowpepper.persistence.model;

import com.deploysoft.yellowpepper.domain.constant.TypeTransactionEnum;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

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

}
