package com.zyl.sercurity.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.zyl.sercurity.utils.TokenUtils;

public class AuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * json web token 在请求头的名字
     */
    @Value("${token.header}")
    private String tokenHeader;
    
    /**
     * 辅助操作 token 的工具类
     */
    @Autowired
    private TokenUtils tokenUtils;

    /**
     * Spring Security 的核心操作服务类 在当前类中将使用 UserDetailsService 来获取 userDetails 对象
     */
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String token = request.getHeader(this.tokenHeader);
        System.out.println("toekn:" + token);
        String username = this.tokenUtils.getUsernameFromToken(token);
        // username 并且本次会话的权限还未被写入
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            // 检查用户带来的 token 是否有效
            // 包括 token 和 userDetails 中用户名是否一样， token 是否过期
            if(this.tokenUtils.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            if (!userDetails.isEnabled()){
                res.setCharacterEncoding("UTF-8");
                res.setContentType("application/json;charset=UTF-8");
                res.getWriter().print("{\"code\":\"452\",\"data\":\"\",\"message\":\"账号处于黑名单\"}");
                return;
            }
        }
        chain.doFilter(req, res);
    }
}
