package com.bottelx.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private String roleName;

    private String description;

    private boolean active = true;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "role_permissions",

            joinColumns = @JoinColumn(name = "role_id"),

            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<Permission> permissions = new HashSet<>();

    private Long createdAt;

    private Long updatedAt;

    public UUID getId() {
        return id;
    }

    public void setId(
            UUID id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(
            String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(
            String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(
            boolean active) {
        this.active = active;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(
            Set<Permission> permissions) {
        this.permissions = permissions;
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