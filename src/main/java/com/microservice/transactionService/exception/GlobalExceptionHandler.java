package com.microservice.transactionService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

//This handles all types of exception on a common place.
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    //handle resource not found with give ID type error
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                        WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "USER_NOT_FOUND"
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    //handle transaction not found for given PAN Num
    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleTransactionNotFoundException(TransactionNotFoundException exception,
                                                                        WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false),
                "No Transaction Details for given PAN Number!!"
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }



    //handle exception if above two were not match
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobleException(Exception exception, WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
            LocalDateTime.now(),
            exception.getMessage(),
            webRequest.getDescription(false),
            "NTERNAL_SERVER_ERROR"
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
