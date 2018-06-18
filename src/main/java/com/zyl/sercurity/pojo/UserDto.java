package com.zyl.sercurity.pojo;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.ScriptAssert;

import com.zyl.sercurity.annotation.PasswordMatches;
import com.zyl.sercurity.annotation.ValidEmail;

//@PasswordMatches()
@ScriptAssert(lang ="javascript", script ="_this.password.equals(_this.matchingPassword)",message="密码不一致")
public class UserDto implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @NotEmpty(message="用户名不能为空")
    private String username;
     
//    @NotEmpty(message="用户名不能为空")
    private String lastName;
     
    @NotEmpty(message="密码不能为空")
    private String password;
    
    @NotEmpty(message="请输入重复输入密码")
    private String matchingPassword;
     
//    @AssertTrue(message="密码不一致")
//    private boolean isValid() {
//        return this.password.equals(this.matchingPassword);
//    }
    
//    @ValidEmail
//    @Email
//    @NotNull
//    @NotEmpty
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