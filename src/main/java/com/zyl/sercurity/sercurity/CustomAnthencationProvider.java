package com.zyl.sercurity.sercurity;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSourceAware;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.zyl.sercurity.pojo.User;
import com.zyl.sercurity.service.UserService;

@Component
public class CustomAnthencationProvider implements AuthenticationProvider{
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        System.err.println("用户名：" + username);
        System.err.println("密码：" + password);

        User user = userService.getUser(username);
        if (user == null) {
            throw new BadCredentialsException("Username not found.");
        }

        // 加密过程在这里体现
        
        
        
        if (passwordEncoder.matches(user.getPassword(), passwordEncoder.encode(password))) {
            System.out.println("密码错误");
            throw new BadCredentialsException("Wrong password.");
        }
        // Collection<? extends GrantedAuthority> authorities =
        // userService.loadUserByUsername(username).getAuthorities();
        // 根据username查询数据库,获取账号密码
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_admin");
        return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // return false;
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}