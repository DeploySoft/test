package com.deploysoft.yellowpepper.persistence.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

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

}
