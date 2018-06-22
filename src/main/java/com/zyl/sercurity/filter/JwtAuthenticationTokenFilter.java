
package com.zyl.sercurity.filter;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zyl.sercurity.exception.InvalidTokenException;
import com.zyl.sercurity.pojo.resp.ErrorCode;
import com.zyl.sercurity.pojo.resp.ErrorResponse;
import com.zyl.sercurity.utils.JwtTokenUtil;

/**
 * 可参考：org.springframework.security.web.authentication.www.BasicAuthenticationFilter
 * @author Administrator
 *
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final String FILTER_APPLIED = "__spring_security_jwtAuthenticationTokenFilter_filterApplied";

    @Resource(name="userDetailServiceImpl")
    private UserDetailsService userDetailsService;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        if (request.getAttribute(FILTER_APPLIED) != null) {
            chain.doFilter(request, response);
            return ;
        }
        
        
        
        String token = request.getHeader(this.tokenHeader);
        if (token == null) {
            token = request.getParameter("token");
            if (token == null) {
                chain.doFilter(request, response);
                return;
            }
        }
        String username = jwtTokenUtil.getUsernameFromToken(token);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtTokenUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }else if(username == null) {
//            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
//            mapper.writeValue(response.getWriter(),
//                    ErrorResponse.of("无效的Token,请重新登录", ErrorCode.JWT_TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED));
//            return;
            throw new InvalidTokenException("无效的Token,请重新登录");
        }
        request.setAttribute(FILTER_APPLIED,true);
        chain.doFilter(request, response);
    }
}
