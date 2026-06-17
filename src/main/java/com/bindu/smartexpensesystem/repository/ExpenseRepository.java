package com.bindu.smartexpensesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.bindu.smartexpensesystem.entity.Expense;
import java.util.List;


public interface ExpenseRepository extends JpaRepository<Expense, Long> {
	List<Expense> findByCategory(String category);
	List<Expense> findByUserId(Long userId);
	List<Expense> findByCategoryIgnoreCase(String category);

}