package com.picpaydesafio.demopicpaydesafio.infrastructure.transation.repository;

import com.picpaydesafio.demopicpaydesafio.infrastructure.transation.entity.TransactionEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionJpaRepository extends JpaRepository<TransactionEntity, Long> {

}
