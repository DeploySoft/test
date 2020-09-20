package com.deploysoft.yellowpepper.persistence.model;

import com.deploysoft.yellowpepper.domain.constant.TypeConfigEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 19/09/20
 **/
@Data
@Entity
@Table(name = "account_config")
public class AccountConfig implements Serializable {
    @Id
    @EmbeddedId
    private AccountConfigId accountConfigId;

    private String value;

    @Data
    @Embeddable
    public static class AccountConfigId implements Serializable {

        @Enumerated(EnumType.STRING)
        private TypeConfigEnum typeConfigEnum;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "account", nullable = false)
        @JsonBackReference
        private Account account;
    }
}
