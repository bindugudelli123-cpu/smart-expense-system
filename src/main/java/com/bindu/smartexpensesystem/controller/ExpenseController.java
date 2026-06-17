package com.bindu.smartexpensesystem.controller;

import java.util.List;
import com.bindu.smartexpensesystem.dto.CategorySummaryResponse;
import com.bindu.smartexpensesystem.dto.DashboardResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.bindu.smartexpensesystem.dto.ExpenseSummaryResponse;
import com.bindu.smartexpensesystem.dto.CategoryExpenseResponse;
import com.bindu.smartexpensesystem.dto.MonthlyExpenseResponse;
import com.bindu.smartexpensesystem.dto.ExpenseRequest;

import com.bindu.smartexpensesystem.entity.Expense;
import com.bindu.smartexpensesystem.service.ExpenseService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/expenses")
    public List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @PostMapping("/expenses")
    public Expense createExpense(
            @Valid @RequestBody ExpenseRequest request) {

        return expenseService.createExpense(request);
    }

    @PutMapping("/expenses/{id}")
    public Expense updateExpense(@PathVariable Long id,
                                 @RequestBody Expense expense) {

        return expenseService.updateExpense(id, expense);
    }

    @DeleteMapping("/expenses/{id}")
    public String deleteExpense(@PathVariable Long id) {

        return expenseService.deleteExpense(id);
    }
    @GetMapping("/expenses/summary")
    public ExpenseSummaryResponse getExpenseSummary() {

        return expenseService.getExpenseSummary();
    }
    @GetMapping("/expenses/category/{category}")
    public CategoryExpenseResponse getCategorySummary(
            @PathVariable String category) {

        return expenseService.getCategorySummary(category);
    }
    @GetMapping("/expenses/monthly-summary")
    public MonthlyExpenseResponse getMonthlySummary() {

        return expenseService.getCurrentMonthSummary();
    }
    @GetMapping("/users/{userId}/expenses")
    public List<Expense> getExpensesByUser(
            @PathVariable Long userId) {

        return expenseService.getExpensesByUser(userId);
    }
    @GetMapping("/expenses/top-category")
    public CategorySummaryResponse getTopCategory() {

        return expenseService.getTopCategory();
    }
    @GetMapping("/expenses/category-ranking")
    public List<CategorySummaryResponse> getCategoryRanking() {

        return expenseService.getCategoryRanking();
    }
    
    @GetMapping("/dashboard")
    public DashboardResponse getDashboard() {

        return expenseService.getDashboard();
    }
    
    @GetMapping("/expenses/search")
    public List<Expense> searchByCategory(
            @RequestParam String category) {

        return expenseService.searchByCategory(category);
    }
    
    @GetMapping("/expenses/page")
    public Page<Expense> getExpensesPage(

            @RequestParam int page,
            @RequestParam int size) {

        return expenseService
                .getExpensesPage(page, size);
    }
    @GetMapping("/expenses/sort")
    public List<Expense> getExpensesSorted(

            @RequestParam String field) {

        return expenseService
                .getExpensesSorted(field);
    }
}
