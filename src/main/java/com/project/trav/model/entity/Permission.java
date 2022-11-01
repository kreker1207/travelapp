package com.project.trav.model.entity;

public enum Permission {
    USERS("users"),
    ADMINS("admins");
    private final String permissionName;
    Permission(String permission){this.permissionName=permission;}
    public String getPermission(){return permissionName;}
}
