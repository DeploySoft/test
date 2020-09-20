package com.deploysoft.yellowpepper.persistence.model;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 20/09/20
 **/
@Data
@Embeddable
public class TransactionId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Account account;
    private LocalDateTime localDateTime = LocalDateTime.now();
}