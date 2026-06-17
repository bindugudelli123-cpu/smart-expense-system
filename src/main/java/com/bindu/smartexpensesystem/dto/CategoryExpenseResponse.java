package com.bindu.smartexpensesystem.dto;

public class CategoryExpenseResponse {

    private String category;
    private Double totalSpent;

    public CategoryExpenseResponse() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(Double totalSpent) {
        this.totalSpent = totalSpent;
    }
}