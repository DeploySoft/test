package com.deploysoft.application.persistence.model;

import com.deploysoft.application.domain.constant.TypeConfigEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private AccountConfigKey id;

    private String value;

    @Data
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountConfigKey implements Serializable {

        @Column(name = "account_id")
        private Long accountId;

        @Enumerated(EnumType.STRING)
        @Column(name = "type_config")
        private TypeConfigEnum typeConfigEnum;
    }
}
