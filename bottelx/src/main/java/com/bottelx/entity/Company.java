package com.bottelx.entity;

 
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String companyName;

    @Column(unique = true)
    private String companyCode;

    @Column(unique = true)
    private String gstNumber;

    private String logo;

    private String website;

    private String contactEmail;

    private String contactPhone;

    private String address;

    private boolean active = true;

    private boolean deleted = false;

    private Long createdAt;

    private Long updatedAt;

    public UUID getId() {
        return id;
    }

    public void setId(
            UUID id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(
            String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(
            String companyCode) {
        this.companyCode = companyCode;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(
            String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(
            String logo) {
        this.logo = logo;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(
            String website) {
        this.website = website;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(
            String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(
            String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(
            String address) {
        this.address = address;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(
            boolean active) {
        this.active = active;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(
            boolean deleted) {
        this.deleted = deleted;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(
            Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(
            Long updatedAt) {
        this.updatedAt = updatedAt;
    }
}