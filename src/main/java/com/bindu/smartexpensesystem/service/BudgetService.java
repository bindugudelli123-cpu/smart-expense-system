package com.bindu.smartexpensesystem.service;
import java.util.ArrayList;
import java.util.List;
import com.bindu.smartexpensesystem.dto.BudgetAlertResponse;

import com.bindu.smartexpensesystem.dto.BudgetAnalysisResponse;
import com.bindu.smartexpensesystem.entity.Expense;
import com.bindu.smartexpensesystem.repository.ExpenseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bindu.smartexpensesystem.entity.Budget;
import com.bindu.smartexpensesystem.repository.BudgetRepository;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;
    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }

    public Budget createBudget(Budget budget) {
        return budgetRepository.save(budget);
    }

    public Budget updateBudget(Long id, Budget updatedBudget) {

        Budget budget = budgetRepository.findById(id).orElseThrow();

        budget.setCategory(updatedBudget.getCategory());
        budget.setLimitAmount(updatedBudget.getLimitAmount());

        return budgetRepository.save(budget);
    }

    public String deleteBudget(Long id) {

        budgetRepository.deleteById(id);

        return "Budget Deleted Successfully";
    }
    public BudgetAnalysisResponse analyzeBudget(String category) {

        Budget budget = budgetRepository
                .findByCategory(category)
                .orElseThrow();

        List<Expense> expenses =
                expenseRepository.findByCategory(category);

        double totalSpent = 0;

        for (Expense expense : expenses) {
            totalSpent += expense.getAmount();
        }

        double remaining =
                budget.getLimitAmount() - totalSpent;

        BudgetAnalysisResponse response =
                new BudgetAnalysisResponse();

        response.setCategory(category);
        response.setBudget(budget.getLimitAmount());
        response.setSpent(totalSpent);
        response.setRemaining(remaining);

        if (remaining >= 0) {
            response.setStatus("WITHIN_LIMIT");
        } else {
            response.setStatus("EXCEEDED");
        }

        return response;
    }
    public List<BudgetAlertResponse> getBudgetAlerts() {

        List<Budget> budgets = budgetRepository.findAll();

        List<BudgetAlertResponse> responses =
                new ArrayList<>();

        for (Budget budget : budgets) {

            List<Expense> expenses =
                    expenseRepository.findByCategory(
                            budget.getCategory());

            double totalSpent = 0;

            for (Expense expense : expenses) {
                totalSpent += expense.getAmount();
            }

            BudgetAlertResponse response =
                    new BudgetAlertResponse();

            response.setCategory(budget.getCategory());
            response.setBudget(budget.getLimitAmount());
            response.setSpent(totalSpent);

            if (totalSpent > budget.getLimitAmount()) {
                response.setStatus("EXCEEDED");
            } else {
                response.setStatus("WITHIN_LIMIT");
            }

            responses.add(response);
        }

        return responses;
    }
}