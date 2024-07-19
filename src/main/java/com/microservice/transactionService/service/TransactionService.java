package com.microservice.transactionService.service;


import com.microservice.transactionService.entity.Transaction;
import com.microservice.transactionService.model.TransactionModel;

import java.util.List;

public interface TransactionService {

    Transaction findTransactionById(long id);

    Transaction saveTransactionDetails(TransactionModel transactionModel);

    List<Transaction> getAllTransactions(String panNumber);
}
