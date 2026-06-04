package com.bottelx.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "manufacturer_users")
public class ManufacturerUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;

    private String email;

    private String password;

    private String role;

    public ManufacturerUser() {
    }

    public Long getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(
        String companyName
    ) {
        this.companyName = companyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(
        String email
    ) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(
        String password
    ) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(
        String role
    ) {
        this.role = role;
    }
}
