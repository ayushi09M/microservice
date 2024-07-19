package com.microservice.transactionService.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.microservice.transactionService.entity.Transaction;
import com.microservice.transactionService.exception.InvalidTransactionException;
import com.microservice.transactionService.exception.ResourceNotFoundException;
import com.microservice.transactionService.exception.TransactionNotFoundException;
import com.microservice.transactionService.model.TransactionModel;
import com.microservice.transactionService.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

	@Mock
	private TransactionRepository transactionRepository;

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private TransactionServiceImpl transactionService;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindTransactionById_ExistingId_ReturnsTransaction() {
		// Arrange
		Transaction transaction = getTransaction();
		when(transactionRepository.findById(anyLong())).thenReturn(Optional.of(transaction));

		// Act
		Transaction foundTransaction = transactionService.findTransactionById(transaction.getId());

		// Assert
		assertNotNull(foundTransaction);
		assertEquals(transaction.getId(), foundTransaction.getId());
	}

	@Test
	void testFindTransactionById_NonExistingId_ThrowsResourceNotFoundException() {
		// Arrange
		long id = 456;
		when(transactionRepository.findById(id)).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(ResourceNotFoundException.class, () -> {
			transactionService.findTransactionById(id);
		});

		verify(transactionRepository, times(1)).findById(id);
	}

	@Test
	void testSaveTransactionDetails_ValidTransactionModel_ReturnsSavedTransaction() {
		// Arrange
		TransactionModel transactionModel = getTransactionModel();

		Transaction transaction = getTransaction();

		when(modelMapper.map(transactionModel, Transaction.class)).thenReturn(transaction);
		when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

		// Act
		Transaction savedTransaction = transactionService.saveTransactionDetails(transactionModel);

		// Assert
		assertNotNull(savedTransaction);
		assertEquals(transaction.getAmount(), savedTransaction.getAmount());
		verify(transactionRepository, times(1)).save(transaction);
	}

	@Test
	void testSaveTransactionDetails_InvalidTransactionModel_ThrowsInvalidTransactionException() {
		// Arrange
		TransactionModel transactionModel = getTransactionModel();

		when(modelMapper.map(transactionModel, Transaction.class)).thenThrow(RuntimeException.class);

		// Act & Assert
		assertThrows(InvalidTransactionException.class, () -> {
			transactionService.saveTransactionDetails(transactionModel);
		});

		verify(modelMapper, times(1)).map(transactionModel, Transaction.class);
		verify(transactionRepository, never()).save(any(Transaction.class));
	}

	@Test
	void testGetAllTransactions_ValidPan_ReturnsTransactions() {
		// Arrange
		String panNumber = "test123";
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(getTransaction());

		when(transactionRepository.findByPanNumber(panNumber)).thenReturn(transactions);

		// Act
		List<Transaction> newTransactions = transactionService.getAllTransactions(panNumber);

		// Assert
		assertNotNull(newTransactions);
		assertEquals(1, newTransactions.size());
		verify(transactionRepository, times(1)).findByPanNumber(panNumber);
	}

	@Test
	void testGetAllTransactions_EmptyTransactionsList_ThrowsTransactionNotFoundException() {
		// Arrange
		String panNumber = "FGHIJ5678K";
		List<Transaction> emptyTransactions = new ArrayList<>();

		when(transactionRepository.findByPanNumber(panNumber)).thenReturn(emptyTransactions);

		// Act & Assert
		assertThrows(TransactionNotFoundException.class, () -> {
			transactionService.getAllTransactions(panNumber);
		});

		verify(transactionRepository, times(1)).findByPanNumber(panNumber);
	}

	private Transaction getTransaction() {
		Transaction transaction = new Transaction();
		transaction.setId(1L);
		transaction.setAmount(10000.00);
		transaction.setPanNumber("test123");
		transaction.setPaymentMode("Paytm");
		transaction.setStatus("Success");
		return transaction;
	}

	private TransactionModel getTransactionModel() {
		TransactionModel transaction = new TransactionModel();
		transaction.setId(1);
		transaction.setAmount(10000.00);
		transaction.setPanNumber("test123");
		transaction.setPaymentMode("Paytm");
		transaction.setStatus("Success");
		return transaction;
	}
}