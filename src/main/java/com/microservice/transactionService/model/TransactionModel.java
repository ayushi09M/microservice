package com.microservice.transactionService.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@Data
public class TransactionModel {
    private int id;
    private String panNumber;
    private String paymentMode;
    private double amount;
    private Timestamp timestamp;
    private String status;
}

