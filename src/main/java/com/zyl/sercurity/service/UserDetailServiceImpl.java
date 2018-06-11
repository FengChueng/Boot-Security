package com.zyl.sercurity.service;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * @author lengleng
 * @date 2017/10/26
 * <p>
 */
@Service("userDetailService")
public class UserDetailServiceImpl implements UserDetailsService {
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username:" + username);
        
        String password = "123456";
        String authritizationStr = "MANAGER";
        
        //根据username查询数据库,获取账号密码
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authritizationStr);
        
        return new UserDetailsImpl(username,password,authorities);
    }
}
