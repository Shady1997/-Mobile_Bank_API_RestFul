/*
 * Author: Shady Ahmed
 * Date: 2025-09-27
 * Project: Mobile Banking API
 * My Linked-in: https://www.linkedin.com/in/shady-ahmed97/.
 */
package org.banking.dto;

import org.banking.model.Account.AccountType;
import org.banking.model.Account.AccountStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class AccountDto {
    private Long id;
    private String accountNumber;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Account type is required")
    private AccountType accountType;

    @NotNull(message = "Balance is required")
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal balance;

    private BigDecimal creditLimit = BigDecimal.ZERO;
    private AccountStatus status = AccountStatus.ACTIVE;

    // Constructors
    public AccountDto() {
    }

    public AccountDto(Long userId, AccountType accountType, BigDecimal balance) {
        this.userId = userId;
        this.accountType = accountType;
        this.balance = balance;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }
}