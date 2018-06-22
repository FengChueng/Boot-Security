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

@Service("smsUserDetailServiceImpl")
public class SmsUserDetailServiceImpl implements UserDetailsService {
    

    @Autowired
    private UserService userService;
    
//    @Autowired
//    private PasswordEncoder passwordEncoder;
    
    @Override
    public UserDetails loadUserByUsername(String mobilePhone) throws UsernameNotFoundException {
        System.out.println("mobilePhone:" + mobilePhone);

        String username = "admin";
        if("18380586504".equals(mobilePhone)) {
            username = "zyl";
        }
        User user = userService.getUser(username);
        if(user == null) {
            throw new UsernameNotFoundException(mobilePhone + "not register");
        }
        //根据username查询数据库,获取账号密码
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole());
        
        UserDetailsImpl userDetailsImpl = new UserDetailsImpl(mobilePhone,user.getPassword(),authorities);
        return userDetailsImpl;
    }
}
