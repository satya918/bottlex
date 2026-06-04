package com.bottelx.dto;

import java.util.List;
import java.util.UUID;

public class RoleDto {

    private UUID id;
    private String roleName;
    private String description;
    private List<PermissionDto> permissions;

    public RoleDto() {
    }

    public RoleDto(UUID id, String roleName, String description, List<PermissionDto> permissions) {
        this.id = id;
        this.roleName = roleName;
        this.description = description;
        this.permissions = permissions;
    }

    public List<PermissionDto> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionDto> permissions) {
        this.permissions = permissions;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // getters setters
}
