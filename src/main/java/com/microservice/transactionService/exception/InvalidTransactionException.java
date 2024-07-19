package com.microservice.transactionService.exception;

public class InvalidTransactionException extends RuntimeException{


    public InvalidTransactionException(String message) {
        super(message);
    }
}
