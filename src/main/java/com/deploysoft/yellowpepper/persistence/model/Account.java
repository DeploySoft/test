package com.deploysoft.yellowpepper.persistence.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 19/09/20
 **/
@Data
@Entity
@Table(name = "account")
public class Account implements Serializable {
    @Id
    private Long id;

    @Min(value = 0)
    private BigDecimal amount = new BigDecimal(0);

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "account", referencedColumnName = "id")
    private List<AccountConfig> accountConfig;
}
