package com.finance.finance_dashboard.controllers;

import com.finance.finance_dashboard.entity.Transaction;
import com.finance.finance_dashboard.entity.User;
import com.finance.finance_dashboard.service.TransactionService;
import com.finance.finance_dashboard.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final UserService userService;

    public TransactionController(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<Transaction> addTransaction(@RequestParam String username,
                                                      @RequestBody Transaction transaction) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        transaction.setUser(user);
        Transaction savedTransaction = transactionService.saveTransaction(transaction);
        return ResponseEntity.ok(savedTransaction);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<Transaction>> getUserTransactions(@PathVariable String username) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Transaction> transactions = transactionService.getUserTransactions(user);
        return ResponseEntity.ok(transactions);
    }
}
