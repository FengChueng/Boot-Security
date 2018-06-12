package com.zyl.sercurity.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zyl.sercurity.pojo.User;

@Service
public class UserService {
    private static final Map<String, User> userlist = new HashMap<>();
    static {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User("admin",encoder.encode("123456"),"ADMIN");
        User user1 = new User("zyl",encoder.encode("123456"),"USER");
        
        userlist.put(user.getUsername(), user);
        userlist.put(user1.getUsername(), user1);
        
    }
    
    public User getUser(String userName) {
        User user = userlist.get(userName);
        return user;
    }
    
}
