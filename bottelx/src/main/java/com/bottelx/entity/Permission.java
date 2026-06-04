package com.bottelx.entity;

 
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "permissions")
public class Permission {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(
            unique = true,
            nullable = false
    )
    private String permissionName;

    private String description;

    private boolean active = true;

    private Long createdAt;

    private Long updatedAt;

    public UUID getId() {
        return id;
    }

    public void setId(
            UUID id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(
            String permissionName) {
        this.permissionName = permissionName;
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
