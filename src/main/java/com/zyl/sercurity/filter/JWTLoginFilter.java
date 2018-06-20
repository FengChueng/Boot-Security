package com.zyl.sercurity.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zyl.sercurity.exception.AuthMethodNotSupportedException;
import com.zyl.sercurity.exception.ExpiredTokenException;
import com.zyl.sercurity.exception.InvalidTokenException;
import com.zyl.sercurity.pojo.User;
import com.zyl.sercurity.pojo.resp.ErrorCode;
import com.zyl.sercurity.pojo.resp.ErrorResponse;
import com.zyl.sercurity.pojo.resp.TokenVO;
import com.zyl.sercurity.service.UserDetailsImpl;
import com.zyl.sercurity.utils.JwtTokenUtil;

/**
 * 验证用户名密码正确后，生成一个token，并将token返回给客户端
 * 该类继承自UsernamePasswordAuthenticationFilter，重写了其中的2个方法 attemptAuthentication
 * ：接收并解析用户凭证。 successfulAuthentication ：用户成功登录后，这个方法会被调用，我们在这个方法里生成token。
 * 
 * @author zhaoxinguo on 2017/9/12.
 */
@Component
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.expiration}")
    private Long expiration;

    private ObjectMapper mapper;
    private JwtTokenUtil jwtTokenUtil;

    public JWTLoginFilter() {
        super(new AntPathRequestMatcher("/user/token", "POST"));//指定拦截的url和请求方式
    }

    @Autowired
    public void init(AuthenticationManager authenticationManager, ObjectMapper objectMapper,
            JwtTokenUtil jwtTokenUtil) {
        setAuthenticationManager(authenticationManager);
        this.mapper = objectMapper;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    // 接收并解析用户凭证
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {
            User user = mapper.readValue(req.getInputStream(), User.class);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user.getUsername(), user.getPassword());
            UserDetailsImpl userDetailsImpl = new UserDetailsImpl(user.getUsername(), user.getPassword(),
                    new ArrayList<>());
            authentication.setDetails(userDetailsImpl);
            Authentication authenticate = this.getAuthenticationManager().authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            return authenticate;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 用户成功登录后，这个方法会被调用，我们在这个方法里生成token
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
            Authentication auth) throws IOException, ServletException {
        System.out.println("登录成功");
        Object details = auth.getDetails();
        if (details instanceof UserDetails) {
            String token = jwtTokenUtil.generateToken((UserDetails) auth.getDetails());
            mapper.writeValue(res.getWriter(), new TokenVO(token, expiration));

        } else {
            throw new InvalidTokenException("用户信息无效");
        }
    }

    /**
     * 登录失败
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException e) throws IOException, ServletException {
//         response.setStatus(HttpStatus.UNAUTHORIZED.value());
         response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        if (e instanceof BadCredentialsException || e instanceof UsernameNotFoundException) {
            mapper.writeValue(response.getWriter(),
                    ErrorResponse.of("用户名或密码错误", ErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED));
        } else if (e instanceof ExpiredTokenException) {
            mapper.writeValue(response.getWriter(),
                    ErrorResponse.of("Token过期,请重新登录", ErrorCode.JWT_TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED));
        } else if (e instanceof AuthMethodNotSupportedException) {
            mapper.writeValue(response.getWriter(),
                    ErrorResponse.of(e.getMessage(), ErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED));
        }else {
            mapper.writeValue(response.getWriter(),
                    ErrorResponse.of("Authentication failed", ErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED));
        }
    }

}
