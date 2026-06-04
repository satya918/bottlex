package com.bottelx.dto;


import java.util.UUID;

public class CompanyDto {

    private UUID id;
    private String companyName;
    private String companyCode;

    public CompanyDto() {
    }

    public CompanyDto(UUID id, String companyName, String companyCode) {
        this.id = id;
        this.companyName = companyName;
        this.companyCode = companyCode;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    // getters setters
}