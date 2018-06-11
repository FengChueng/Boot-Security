package com.zyl.sercurity.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails{
    private static final long serialVersionUID = 1L;
    private String name;
    private String password;
    private List<GrantedAuthority> grantedAuthorities;
    
    public UserDetailsImpl(String name, String password,List<GrantedAuthority> grantedAuthorities) {
        super();
        this.name = name;
        this.password = password;
        this.grantedAuthorities = grantedAuthorities;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return grantedAuthorities;
    }
    @Override
    public String getUsername() {
        return this.name;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
    
    
    
}
