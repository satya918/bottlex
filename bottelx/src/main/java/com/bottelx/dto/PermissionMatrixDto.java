package com.bottelx.dto;

import java.util.List;
import java.util.Map;

public class PermissionMatrixDto {

    private List<String> roles;
    private List<String> permissions;

    private Map<String, List<String>> rolePermissions;

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public Map<String, List<String>> getRolePermissions() {
        return rolePermissions;
    }

    public void setRolePermissions(Map<String, List<String>> rolePermissions) {
        this.rolePermissions = rolePermissions;
    }



    
}
