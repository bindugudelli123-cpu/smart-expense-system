package com.bindu.smartexpensesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bindu.smartexpensesystem.entity.Budget;
import java.util.Optional;



public interface BudgetRepository extends JpaRepository<Budget, Long> {
	Optional<Budget> findByCategory(String category);

}