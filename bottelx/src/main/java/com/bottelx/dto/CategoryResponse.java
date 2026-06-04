package com.bottelx.dto;


public class CategoryResponse {

    private String id;

    private String categoryName;

    private String categoryCode;

    private String description;

    private Boolean active;

    // =========================================
    // CONSTRUCTORS
    // =========================================

    public CategoryResponse() {
    }

    public CategoryResponse(
            String id,
            String categoryName,
            String categoryCode,
            String description,
            Boolean active
    ) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryCode = categoryCode;
        this.description = description;
        this.active = active;
    }

    // =========================================
    // GETTERS & SETTERS
    // =========================================

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    // =========================================
    // BUILDER
    // =========================================

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;

        private String categoryName;

        private String categoryCode;

        private String description;

        private Boolean active;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder categoryName(String categoryName) {
            this.categoryName = categoryName;
            return this;
        }

        public Builder categoryCode(String categoryCode) {
            this.categoryCode = categoryCode;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder active(Boolean active) {
            this.active = active;
            return this;
        }

        public CategoryResponse build() {

            return new CategoryResponse(
                    id,
                    categoryName,
                    categoryCode,
                    description,
                    active
            );
        }
    }
}
