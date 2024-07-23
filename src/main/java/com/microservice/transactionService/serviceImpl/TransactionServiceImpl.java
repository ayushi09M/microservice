package com.microservice.transactionService.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.transactionService.entity.Transaction;
import com.microservice.transactionService.exception.InvalidTransactionException;
import com.microservice.transactionService.exception.ResourceNotFoundException;
import com.microservice.transactionService.exception.TransactionNotFoundException;
import com.microservice.transactionService.model.TransactionModel;
import com.microservice.transactionService.repository.TransactionRepository;
import com.microservice.transactionService.service.TransactionService;

/**
 * Implementation of the TransactionService interface providing methods for
 * interacting with transaction data.
 */
@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private ModelMapper modelMapper;

	private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

	/**
	 * Find transaction by its unique id.
	 *
	 * @param id The ID of the transaction to retrieve.
	 * @return The transaction with the specified ID, or null if not found.
	 */
	@Override
	public Transaction findTransactionById(long id) {
		logger.info("Finding transaction by ID: {}", id);

		// Optional Transaction
		return transactionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Transaction", "id", id));
	}

	/**
	 * Saves transaction details.
	 *
	 * @param transactionModel The model containing transaction details to be
	 *                         stored.
	 * @return The saved transaction information.
	 */

	@Override
	public Transaction saveTransactionDetails(TransactionModel transactionModel) {

		try {
			Transaction transaction = modelMapper.map(transactionModel, Transaction.class);
			logger.info("Saving transaction details: {}", transactionModel);
			return transactionRepository.save(transaction);
		} catch (Exception e) {
			logger.error("Error saving transaction details: {}", e.getMessage());
			throw new InvalidTransactionException("Invalid data set ");
		}
	}

	/**
	 * Retrieves all transactions associated with a given PAN.
	 *
	 * @param panNumber The PAN number for which transactions are to be retrieved.
	 * @return A list of transactions associated with the specified PAN number.
	 */
	@Override
	public List<Transaction> getAllTransactions(String panNumber) {

		logger.info("Retrieving all transactions for PAN number: {}", panNumber);
		List<Transaction> transactions = transactionRepository.findByPanNumber(panNumber);

		if (transactions.isEmpty()) {
			throw new TransactionNotFoundException("Transactions", "panNumber", panNumber);
		}

		return transactions;
	}
}