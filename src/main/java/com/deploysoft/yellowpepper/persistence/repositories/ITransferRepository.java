package com.deploysoft.yellowpepper.persistence.repositories;

import com.deploysoft.yellowpepper.persistence.model.Transaction;
import com.deploysoft.yellowpepper.persistence.model.TransactionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 20/09/20
 **/
@Repository
public interface ITransferRepository extends JpaRepository<Transaction, TransactionId> {
}
