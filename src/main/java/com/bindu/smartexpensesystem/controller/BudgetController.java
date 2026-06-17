package com.bindu.smartexpensesystem.controller;

import java.util.List;
import com.bindu.smartexpensesystem.dto.BudgetAlertResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bindu.smartexpensesystem.entity.Budget;
import com.bindu.smartexpensesystem.service.BudgetService;
import com.bindu.smartexpensesystem.dto.BudgetAnalysisResponse;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @GetMapping("/budgets")
    public List<Budget> getAllBudgets() {
        return budgetService.getAllBudgets();
    }

    @PostMapping("/budgets")
    public Budget createBudget(@RequestBody Budget budget) {
        return budgetService.createBudget(budget);
    }

    @PutMapping("/budgets/{id}")
    public Budget updateBudget(@PathVariable Long id,
                               @RequestBody Budget budget) {

        return budgetService.updateBudget(id, budget);
    }

    @DeleteMapping("/budgets/{id}")
    public String deleteBudget(@PathVariable Long id) {

        return budgetService.deleteBudget(id);
    }
    
    @GetMapping("/budget/check/{category}")
    public BudgetAnalysisResponse checkBudget(
            @PathVariable String category) {

        return budgetService.analyzeBudget(category);
    }
    @GetMapping("/budget/alerts")
    public List<BudgetAlertResponse> getBudgetAlerts() {

        return budgetService.getBudgetAlerts();
    }
}
