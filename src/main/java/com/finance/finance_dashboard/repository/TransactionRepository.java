package com.finance.finance_dashboard.repository;

import com.finance.finance_dashboard.entity.Transaction;
import com.finance.finance_dashboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
    List<Transaction> findByUserAndType(User user, String type); // income or expense
}