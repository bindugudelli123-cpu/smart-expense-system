package com.bindu.smartexpensesystem.dto;

public class CategorySummaryResponse {

    private String category;
    private Double totalSpent;

    public CategorySummaryResponse() {
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