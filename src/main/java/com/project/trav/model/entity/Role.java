package com.project.trav.model.entity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    USER(Set.of(Permission.USERS)),
    ADMIN(Set.of(Permission.ADMINS));
    private final Set<Permission> permissions;

    Role(Set<Permission> permissions){this.permissions=permissions;}
    public Set<Permission> getPermissions(){return permissions;}
    public Set<SimpleGrantedAuthority> grantedAuthorities(){
        return getPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }




}
