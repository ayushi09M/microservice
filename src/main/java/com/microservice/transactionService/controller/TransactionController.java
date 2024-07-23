package com.microservice.transactionService.controller;

import com.microservice.transactionService.entity.Transaction;
import com.microservice.transactionService.model.TransactionModel;
import com.microservice.transactionService.service.TransactionService;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "transaction-controller", description = "These APIs provide a set of operations which are performed on the trancation service.")
@RestController()
@RequestMapping("transactionservice-svc")
@CrossOrigin("*")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ModelMapper modelMapper;
    /**\
     * Retrieving the transaction details by its Unique Id.
     *
     * @param id provided here to retrieve respective transaction details.
     * @return the transaction with specified ID
     */

    @Operation(summary = "Find by id", description = "Fetch transaction by customer id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Transaction fetched successfully"),
            @ApiResponse(code = 500, message = "Error in fetching transaction.")
    })
    @GetMapping("/findById/{id}")
    public ResponseEntity<TransactionModel> findTransactionByID(
            @ApiParam(name = "id", value = "Customer Id to fetch transaction", required = true)
            @PathVariable long id) {
        logger.info("Request received to find transaction by ID: {}", id);
        Transaction transaction = transactionService.findTransactionById(id);
        if (transaction != null) {
            logger.info("Transaction found for ID {}: {}", id, transaction);

            TransactionModel transactionModel = modelMapper.map(transaction, TransactionModel.class);
            return new ResponseEntity<>(transactionModel, HttpStatus.OK);
        } else {
            logger.error("Transaction not found for ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

//    /**
//     * Stores transaction details
//     *
//     * @param transactionModel The model containing transaction details to be stored.
//     * @return The saved transaction information.
//     */
//
//    @Operation(summary = "Save transaction", description = "Save transaction in the transaction table.")
//    @ApiResponses(value = {
//            @ApiResponse(code = 201, message = "Trancation saved successfully")
//    })
//    @PostMapping("/saveTransaction")
//    public ResponseEntity<Transaction> storeTransactionDetails(@RequestBody TransactionModel transactionModel) {
//        logger.info("Request received to save transaction details: {}", transactionModel);
//        Transaction savedTransaction = transactionService.saveTransactionDetails(transactionModel);
//        logger.info("Saved Transaction details: {}", savedTransaction);
//        return new ResponseEntity<>(savedTransaction,HttpStatus.CREATED);
//    }


    @Operation(summary = "Save transaction", description = "Save transaction in the transaction table.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Transaction saved successfully"),
            @ApiResponse(code = 400, message = "Invalid transaction details"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping("/saveTransaction")
    public ResponseEntity<Transaction> storeTransactionDetails(@RequestBody TransactionModel transactionModel) {
        logger.info("Request received to save transaction details: {}", transactionModel);
        try {
            Transaction savedTransaction = transactionService.saveTransactionDetails(transactionModel);
            logger.info("Saved Transaction details: {}", savedTransaction);
            return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid transaction details: {}", transactionModel, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Error saving transaction details: {}", transactionModel, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Retrieves all transactions associated with a given PAN (Permanent Account Number).
     * This endpoint is used for communication with the CIBIL service.
     *
     * @param panNumber The PAN number for which transactions are to be retrieved.
     * @return A list of transactions associated with the specified PAN number.
     */
    @Operation(summary = "Fetch transaction", description = "Fetch transaction by pan number")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Transaction fetched successfully"),
            @ApiResponse(code = 500, message = "Error in fetching transaction.")
    })
    @GetMapping("/pan/{panNumber}")
    public ResponseEntity<List<TransactionModel>> getAllTransactions(
            @ApiParam(name = "panNumber", value = "Pan number to fetch transaction", required = true)
            @PathVariable String panNumber) {
        logger.info("Request received to get all transactions for PAN number: {}", panNumber);
        List<Transaction> transactions = transactionService.getAllTransactions(panNumber);
        if (transactions != null && !transactions.isEmpty()) {
            logger.info("Transactions found for PAN {}: {}", panNumber, transactions);

            //Converting List<Transaction> to List<TransactionModel>
            List<TransactionModel> transactionModels = transactions.stream()
                    .map(transaction -> modelMapper.map(transaction, TransactionModel.class))
                    .collect(Collectors.toList());

             return new ResponseEntity<>(transactionModels, HttpStatus.OK);
        } else {
            logger.error("No transactions found for PAN: {}", panNumber);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
