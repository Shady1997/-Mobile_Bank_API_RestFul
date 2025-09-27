/*
 * Author: Shady Ahmed
 * Date: 2025-09-27
 * Project: Mobile Banking API
 * My Linked-in: https://www.linkedin.com/in/shady-ahmed97/.
 */
package org.banking.service;

import org.banking.dto.AccountDto;
import org.banking.exception.ResourceNotFoundException;
import org.banking.model.Account;
import org.banking.model.User;
import org.banking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@Transactional
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserService userService;

    public Account createAccount(AccountDto accountDto) {
        User user = userService.getUserById(accountDto.getUserId());

        String accountNumber = generateAccountNumber();
        while (accountRepository.existsByAccountNumber(accountNumber)) {
            accountNumber = generateAccountNumber();
        }

        Account account = new Account(
                accountNumber,
                user,
                accountDto.getAccountType(),
                accountDto.getBalance()
        );
        account.setCreditLimit(accountDto.getCreditLimit());
        account.setStatus(accountDto.getStatus());

        return accountRepository.save(account);
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public List<Account> getAccountsByUserId(Long userId) {
        userService.getUserById(userId); // Verify user exists
        return accountRepository.findByUserId(userId);
    }

    public Account updateAccount(Long id, AccountDto accountDto) {
        Account account = getAccountById(id);

        account.setAccountType(accountDto.getAccountType());
        account.setBalance(accountDto.getBalance());
        account.setCreditLimit(accountDto.getCreditLimit());
        account.setStatus(accountDto.getStatus());

        return accountRepository.save(account);
    }

    public void deleteAccount(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new ResourceNotFoundException("Account not found with id: " + id);
        }
        accountRepository.deleteById(id);
    }

    public Account findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with number: " + accountNumber));
    }

    private String generateAccountNumber() {
        Random random = new Random();
        return String.format("%012d", random.nextLong() % 1000000000000L);
    }
}