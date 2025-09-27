/*
 * Author: Shady Ahmed
 * Date: 2025-09-27
 * Project: Mobile Banking API
 * My Linked-in: https://www.linkedin.com/in/shady-ahmed97/.
 */
package org.banking.service;

import org.banking.dto.TransactionDto;
import org.banking.exception.ResourceNotFoundException;
import org.banking.exception.InsufficientFundsException;
import org.banking.model.Transaction;
import org.banking.model.Account;
import org.banking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    public Transaction createTransaction(TransactionDto transactionDto) {
        Account fromAccount = accountService.getAccountById(transactionDto.getFromAccountId());
        Account toAccount = transactionDto.getToAccountId() != null ?
                accountService.getAccountById(transactionDto.getToAccountId()) : null;

        String transactionReference = generateTransactionReference();
        while (transactionRepository.existsByTransactionReference(transactionReference)) {
            transactionReference = generateTransactionReference();
        }

        // Validate sufficient funds for withdrawal/transfer
        if (transactionDto.getTransactionType() == Transaction.TransactionType.WITHDRAWAL ||
                transactionDto.getTransactionType() == Transaction.TransactionType.TRANSFER) {
            BigDecimal totalAmount = transactionDto.getAmount().add(transactionDto.getFee());
            if (fromAccount.getBalance().compareTo(totalAmount) < 0) {
                throw new InsufficientFundsException("Insufficient funds in account");
            }
        }

        Transaction transaction = new Transaction(
                transactionReference,
                fromAccount,
                toAccount,
                transactionDto.getAmount(),
                transactionDto.getTransactionType(),
                transactionDto.getDescription()
        );
        transaction.setFee(transactionDto.getFee());

        // Process transaction
        processTransaction(transaction);

        return transactionRepository.save(transaction);
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getTransactionsByAccountId(Long accountId) {
        Account account = accountService.getAccountById(accountId);
        return transactionRepository.findByAccountOrderByCreatedAtDesc(account);
    }

    public Transaction findByTransactionReference(String reference) {
        return transactionRepository.findByTransactionReference(reference)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with reference: " + reference));
    }

    private void processTransaction(Transaction transaction) {
        Account fromAccount = transaction.getFromAccount();
        Account toAccount = transaction.getToAccount();
        BigDecimal amount = transaction.getAmount();
        BigDecimal fee = transaction.getFee();

        try {
            switch (transaction.getTransactionType()) {
                case DEPOSIT:
                    fromAccount.setBalance(fromAccount.getBalance().add(amount));
                    break;
                case WITHDRAWAL:
                    fromAccount.setBalance(fromAccount.getBalance().subtract(amount).subtract(fee));
                    break;
                case TRANSFER:
                    if (toAccount == null) {
                        throw new IllegalArgumentException("To account is required for transfers");
                    }
                    fromAccount.setBalance(fromAccount.getBalance().subtract(amount).subtract(fee));
                    toAccount.setBalance(toAccount.getBalance().add(amount));
                    break;
                case PAYMENT:
                    fromAccount.setBalance(fromAccount.getBalance().subtract(amount).subtract(fee));
                    break;
            }

            transaction.setStatus(Transaction.TransactionStatus.COMPLETED);
            transaction.setProcessedAt(LocalDateTime.now());
        } catch (Exception e) {
            transaction.setStatus(Transaction.TransactionStatus.FAILED);
            throw e;
        }
    }

    private String generateTransactionReference() {
        return "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}