package com.deploysoft.yellowpepper.persistence.repositories;

import com.deploysoft.yellowpepper.persistence.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 20/09/20
 **/
@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.amount = :amount WHERE a.id = :accountId")
    void updateAmount(@Param("accountId") Long accountId, @Param("amount") BigDecimal amount);
}