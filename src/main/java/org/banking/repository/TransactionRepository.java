/*
 * Author: Shady Ahmed
 * Date: 2025-09-27
 * Project: Mobile Banking API
 * My Linked-in: https://www.linkedin.com/in/shady-ahmed97/.
 */
package org.banking.repository;

import org.banking.model.Transaction;
import org.banking.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByTransactionReference(String transactionReference);

    List<Transaction> findByFromAccountOrderByCreatedAtDesc(Account fromAccount);

    List<Transaction> findByToAccountOrderByCreatedAtDesc(Account toAccount);

    @Query("SELECT t FROM Transaction t WHERE t.fromAccount = :account OR t.toAccount = :account ORDER BY t.createdAt DESC")
    List<Transaction> findByAccountOrderByCreatedAtDesc(@Param("account") Account account);

    boolean existsByTransactionReference(String transactionReference);
}
