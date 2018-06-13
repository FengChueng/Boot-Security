package com.zyl.sercurity.pojo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.zyl.sercurity.annotation.PasswordMatches;
import com.zyl.sercurity.annotation.ValidEmail;

@PasswordMatches
public class UserDto implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @NotNull
    @NotEmpty
    private String username;
     
    @NotNull
    @NotEmpty
    private String lastName;
     
    @NotNull
    @NotEmpty
    private String password;
    
    private String matchingPassword;
     
    
    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
     
    
    
    
}