package com.bottelx.dto;

import java.util.List;
import java.util.UUID;

public class UserDto {

    private UUID id;
    private String email;
    private String userName;
    private String firstName;
    private String lastName;
    private String phone;
    private CompanyDto company;

    private List<RoleDto> roles;
    private boolean active;

    public UserDto() {
    }

    public UserDto(UUID id, String email, String userName, String firstName, String lastName, String phone,
            CompanyDto company,
            List<RoleDto> roles, boolean active) {
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.company = company;
        this.roles = roles;
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public CompanyDto getCompany() {
        return company;
    }

    public List<RoleDto> getRoles() {
        return roles;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCompany(CompanyDto company) {
        this.company = company;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }

    // getters setters
}
