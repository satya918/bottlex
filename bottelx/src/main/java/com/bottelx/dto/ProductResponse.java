package com.bottelx.dto;


public class ProductResponse {

    private String id;

    private String productName;

    private String productCode;

    private String sku;

    private String description;

    private Double price;

    private Integer stockQuantity;

    private Boolean active;

    private String manufacturer;

    private String categoryName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public ProductResponse() {
    }

    // =========================
    // ALL ARGS CONSTRUCTOR
    // =========================

    public ProductResponse(
            String id,
            String productName,
            String productCode,
            String sku,
            String description,
            Double price,
            Integer stockQuantity,
            Boolean active,
            String manufacturer,
            String categoryName
    ) {
        this.id = id;
        this.productName = productName;
        this.productCode = productCode;
        this.sku = sku;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.active = active;
        this.manufacturer = manufacturer;
        this.categoryName = categoryName;
    }

     public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;

        private String productName;

        private String productCode;

        private String sku;

        private String description;

        private Double price;

        private Integer stockQuantity;

        private Boolean active;

        private String manufacturer;

        private String categoryName;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder productName(String productName) {
            this.productName = productName;
            return this;
        }

        public Builder productCode(String productCode) {
            this.productCode = productCode;
            return this;
        }

        public Builder sku(String sku) {
            this.sku = sku;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder price(Double price) {
            this.price = price;
            return this;
        }

        public Builder stockQuantity(Integer stockQuantity) {
            this.stockQuantity = stockQuantity;
            return this;
        }

        public Builder active(Boolean active) {
            this.active = active;
            return this;
        }

        public Builder manufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
            return this;
        }

        public Builder categoryName(String categoryName) {
            this.categoryName = categoryName;
            return this;
        }

        public ProductResponse build() {

            return new ProductResponse(
                    id,
                    productName,
                    productCode,
                    sku,
                    description,
                    price,
                    stockQuantity,
                    active,
                    manufacturer,
                    categoryName
            );
        }
    }
}
