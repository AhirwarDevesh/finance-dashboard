package com.finance.finance_dashboard.controllers;

import com.finance.finance_dashboard.entity.Transaction;
import com.finance.finance_dashboard.entity.User;
import com.finance.finance_dashboard.service.TransactionService;
import com.finance.finance_dashboard.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
//@CrossOrigin(origins = "http://localhost:3000")
public class DashboardController {

    private final TransactionService transactionService;
    private final UserService userService;

    public DashboardController(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @GetMapping("/{username}/summary")
    public ResponseEntity<Map<String, Double>> getSummary(@PathVariable String username) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Transaction> transactions = transactionService.getUserTransactions(user);

        double totalIncome = transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("income"))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double totalExpense = transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("expense"))
                .mapToDouble(Transaction::getAmount)
                .sum();

        Map<String, Double> summary = new HashMap<>();
        summary.put("Total Income", totalIncome);
        summary.put("Total Expense", totalExpense);
        summary.put("Balance", totalIncome - totalExpense);

        return ResponseEntity.ok(summary);
    }
}