package com.finance.finance_dashboard.repository;

import com.finance.finance_dashboard.entity.Transaction;
import com.finance.finance_dashboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
    List<Transaction> findByUserAndType(User user, String type); // income or expense
}