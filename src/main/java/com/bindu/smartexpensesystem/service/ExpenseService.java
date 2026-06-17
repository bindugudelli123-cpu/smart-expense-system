package com.bindu.smartexpensesystem.service;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import com.bindu.smartexpensesystem.dto.CategorySummaryResponse;
import java.util.ArrayList;
import java.util.Comparator;
import com.bindu.smartexpensesystem.dto.DashboardResponse;
import com.bindu.smartexpensesystem.entity.Budget;
import com.bindu.smartexpensesystem.repository.BudgetRepository;
import com.bindu.smartexpensesystem.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bindu.smartexpensesystem.dto.ExpenseSummaryResponse;
import com.bindu.smartexpensesystem.dto.CategoryExpenseResponse;
import java.time.LocalDate;
import java.time.Month;
import com.bindu.smartexpensesystem.dto.MonthlyExpenseResponse;
import com.bindu.smartexpensesystem.dto.ExpenseRequest;
import com.bindu.smartexpensesystem.entity.User;
import com.bindu.smartexpensesystem.repository.UserRepository;

import com.bindu.smartexpensesystem.entity.Expense;
import com.bindu.smartexpensesystem.repository.ExpenseRepository;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;
    
    @Autowired
    private BudgetRepository budgetRepository;
    
    @Autowired
    private UserRepository userRepository;

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public Expense updateExpense(Long id, Expense updatedExpense) {

        Expense expense = expenseRepository.findById(id).orElseThrow();

        expense.setTitle(updatedExpense.getTitle());
        expense.setAmount(updatedExpense.getAmount());
        expense.setCategory(updatedExpense.getCategory());
        expense.setDescription(updatedExpense.getDescription());
        expense.setDate(updatedExpense.getDate());

        return expenseRepository.save(expense);
    }
    public String deleteExpense(Long id) {
        expenseRepository.deleteById(id);
        return "Expense Deleted Successfully";
    }
    
    public ExpenseSummaryResponse getExpenseSummary() {

        List<Expense> expenses = expenseRepository.findAll();

        double total = 0;

        for (Expense expense : expenses) {
            total += expense.getAmount();
        }

        ExpenseSummaryResponse response =
                new ExpenseSummaryResponse();

        response.setTotalExpenses(total);

        return response;
    }
    public CategoryExpenseResponse getCategorySummary(String category) {

        List<Expense> expenses =
                expenseRepository.findByCategory(category);

        double total = 0;

        for (Expense expense : expenses) {
            total += expense.getAmount();
        }

        CategoryExpenseResponse response =
                new CategoryExpenseResponse();

        response.setCategory(category);
        response.setTotalSpent(total);

        return response;
    }
    public MonthlyExpenseResponse getCurrentMonthSummary() {

        List<Expense> expenses = expenseRepository.findAll();

        double total = 0;

        Month currentMonth = LocalDate.now().getMonth();

        for (Expense expense : expenses) {

            if (expense.getDate() != null &&
                expense.getDate().getMonth() == currentMonth) {

                total += expense.getAmount();
            }
        }

        MonthlyExpenseResponse response =
                new MonthlyExpenseResponse();

        response.setMonth(currentMonth.toString());
        response.setTotalSpent(total);

        return response;
    }
    
    public Expense createExpense(ExpenseRequest request) {

    	User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new ResourceNotFoundException(
    	                        "User not found"));

        Expense expense = new Expense();

        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setDescription(request.getDescription());
        expense.setDate(request.getDate());

        expense.setUser(user);

        return expenseRepository.save(expense);
    }
    public List<Expense> getExpensesByUser(Long userId) {

        return expenseRepository.findByUserId(userId);
    }
    
    public CategorySummaryResponse getTopCategory() {

        List<Expense> expenses = expenseRepository.findAll();

        Map<String, Double> categoryTotals =
                new HashMap<>();

        for (Expense expense : expenses) {

            String category = expense.getCategory();

            categoryTotals.put(
                    category,
                    categoryTotals.getOrDefault(category, 0.0)
                            + expense.getAmount());
        }

        String topCategory = "";
        double maxAmount = 0;

        for (Map.Entry<String, Double> entry
                : categoryTotals.entrySet()) {

            if (entry.getValue() > maxAmount) {

                maxAmount = entry.getValue();
                topCategory = entry.getKey();
            }
        }

        CategorySummaryResponse response =
                new CategorySummaryResponse();

        response.setCategory(topCategory);
        response.setTotalSpent(maxAmount);

        return response;
    }
    
    public List<CategorySummaryResponse> getCategoryRanking() {

        List<Expense> expenses = expenseRepository.findAll();

        Map<String, Double> categoryTotals =
                new HashMap<>();

        for (Expense expense : expenses) {

            String category = expense.getCategory();

            categoryTotals.put(
                    category,
                    categoryTotals.getOrDefault(category, 0.0)
                            + expense.getAmount());
        }

        List<CategorySummaryResponse> responses =
                new ArrayList<>();

        for (Map.Entry<String, Double> entry
                : categoryTotals.entrySet()) {

            CategorySummaryResponse response =
                    new CategorySummaryResponse();

            response.setCategory(entry.getKey());
            response.setTotalSpent(entry.getValue());

            responses.add(response);
        }

        responses.sort(
                Comparator.comparing(
                        CategorySummaryResponse::getTotalSpent)
                        .reversed());

        return responses;
    }
    
    public DashboardResponse getDashboard() {

        List<Expense> expenses = expenseRepository.findAll();
        List<Budget> budgets = budgetRepository.findAll();

        double totalExpenses = 0;
        double totalBudget = 0;

        for (Expense expense : expenses) {
            totalExpenses += expense.getAmount();
        }

        for (Budget budget : budgets) {

            if (budget.getLimitAmount() != null) {
                totalBudget += budget.getLimitAmount();
            }
        }

        CategorySummaryResponse topCategory =
                getTopCategory();

        DashboardResponse response =
                new DashboardResponse();

        response.setTotalExpenses(totalExpenses);
        response.setTotalBudget(totalBudget);
        response.setRemainingBudget(
                totalBudget - totalExpenses);

        response.setTopCategory(
                topCategory.getCategory());

        return response;
    }
    
    public List<Expense> searchByCategory(String category) {

        return expenseRepository
                .findByCategoryIgnoreCase(category);
    }
    
    public Page<Expense> getExpensesPage(
            int page,
            int size) {

        return expenseRepository.findAll(
                PageRequest.of(page, size));
    }
    public List<Expense> getExpensesSorted(
            String field) {

        return expenseRepository.findAll(
                Sort.by(field));
    }
}