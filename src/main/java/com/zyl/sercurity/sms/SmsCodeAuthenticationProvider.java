package com.zyl.sercurity.sms;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @Author: HanLong
 * @Date: Create in 2018/3/24 15:31
 * @Description:  类似于{@link DaoAuthenticationProvider}
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private static final String String = null;
    private UserDetailsService userDetailsService;

    /**
     * 身份逻辑验证
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;

        /**
         * 调用 {@link UserDetailsService}
         */
        String principal = (java.lang.String) authenticationToken.getPrincipal();
        UserDetails user = userDetailsService.loadUserByUsername(principal);
        if (user == null) {
            throw new UsernameNotFoundException("无法获取用户信息");
        }
        SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(user, user.getAuthorities());

        authenticationResult.setDetails(authenticationToken.getDetails());

        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

}
