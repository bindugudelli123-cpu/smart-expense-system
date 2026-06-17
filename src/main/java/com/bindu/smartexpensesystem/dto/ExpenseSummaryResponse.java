package com.bindu.smartexpensesystem.dto;

public class ExpenseSummaryResponse {

    private Double totalExpenses;

    public ExpenseSummaryResponse() {
    }

    public Double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(Double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }
}