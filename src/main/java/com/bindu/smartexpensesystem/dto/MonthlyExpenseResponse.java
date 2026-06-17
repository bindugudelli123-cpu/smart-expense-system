package com.bindu.smartexpensesystem.dto;

public class MonthlyExpenseResponse {

    private String month;
    private Double totalSpent;

    public MonthlyExpenseResponse() {
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Double getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(Double totalSpent) {
        this.totalSpent = totalSpent;
    }
}
