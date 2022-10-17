package com.project.trav.domain.entity;

public enum Permission {
    USERS_READ("users:read"),
    USERS_WRITE("users:write");
    private final String permissionName;
    Permission(String permission){this.permissionName=permission;}
    public String getPermission(){return permissionName;}
}
