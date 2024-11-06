package com.picpaydesafio.demopicpaydesafio.infrastructure.repositories.interfaces;

import com.picpaydesafio.demopicpaydesafio.infrastructure.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionJpaRepository extends JpaRepository<TransactionEntity, Long> {

}
