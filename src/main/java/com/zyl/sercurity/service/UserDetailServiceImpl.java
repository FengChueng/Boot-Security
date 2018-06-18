package com.zyl.sercurity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zyl.sercurity.pojo.User;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    

    @Autowired
    private UserService userService;
    
//    @Autowired
//    private PasswordEncoder passwordEncoder;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username:" + username);
        
        User user = userService.getUser(username);
        if(user == null) {
            throw new UsernameNotFoundException(username + "not register");
        }
        //根据username查询数据库,获取账号密码
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole());
        
        UserDetailsImpl userDetailsImpl = new UserDetailsImpl(username,user.getPassword(),authorities);
        return userDetailsImpl;
    }
}
