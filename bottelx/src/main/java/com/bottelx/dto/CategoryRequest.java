package com.bottelx.dto;


public class CategoryRequest {

    private String categoryName;

    private String categoryCode;

    private String description;


   

    // =========================================
    // GETTERS & SETTERS
    // =========================================

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
