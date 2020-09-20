package com.deploysoft.yellowpepper.persistence.repositories;

import com.deploysoft.yellowpepper.persistence.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 20/09/20
 **/
@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {
}