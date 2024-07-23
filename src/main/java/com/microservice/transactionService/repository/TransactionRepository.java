package com.microservice.transactionService.repository;


import com.microservice.transactionService.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    public Optional<Transaction> findById(long id);
    public List<Transaction> findByPanNumber(String panNumber);
}



