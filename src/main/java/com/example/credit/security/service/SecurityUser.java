package com.example.credit.security.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.credit.entities.RoleEntity;
import com.example.credit.entities.UserEntity;

import lombok.RequiredArgsConstructor;

/**
 * CustomUserDetail
 */
@RequiredArgsConstructor
public class SecurityUser implements UserDetails{

    private final UserEntity userEntity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        RoleEntity roles = userEntity.getRole();
        return List.of(new SimpleGrantedAuthority(roles.getRoleName()));
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUniquename();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    
}
