package com.zyl.sercurity.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zyl.sercurity.pojo.User;

@Service
public class UserService {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    
    private static final Map<String, User> userlist = new HashMap<>();
    static {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User("admin",encoder.encode("123456"),"ROLE_ADMIN");
        User user1 = new User("zyl",encoder.encode("123456"),"ROLE_USER");
        
        userlist.put(user.getUsername(), user);
        userlist.put(user1.getUsername(), user1);
        
    }
    
    public User getUser(String userName) {
        User user = userlist.get(userName);
        return user;
    }

    public User register(String username, String password) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = userlist.put(username, new User(username, encoder.encode(password), "ROLE_USER"));
        return user;
    }
    
//    public String login(String username, String password) {  
//        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);  
//        // Perform the security  
//        final Authentication authentication = authenticationManager.authenticate(upToken);  
//        SecurityContextHolder.getContext().setAuthentication(authentication);  
//        // Reload password post-security so we can generate token  
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);  
//        final String token = jwtTokenUtil.generateToken(userDetails);  
//        return token;  
//    }  
//  
//    public String refresh(String oldToken) {  
//        final String token = oldToken.substring(tokenHead.length());  
//        String username = jwtTokenUtil.getUsernameFromToken(token);  
//        SysUser user = (SysUser) userDetailsService.loadUserByUsername(username);  
//        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())){  
//            return jwtTokenUtil.refreshToken(token);  
//        }  
//        return null;  
//    }  
    
    
}
