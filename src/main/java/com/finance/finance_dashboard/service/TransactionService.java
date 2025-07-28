package com.finance.finance_dashboard.service;

import com.finance.finance_dashboard.entity.Transaction;
import com.finance.finance_dashboard.entity.User;
import com.finance.finance_dashboard.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getUserTransactions(User user) {
        return transactionRepository.findByUser(user);
    }
}