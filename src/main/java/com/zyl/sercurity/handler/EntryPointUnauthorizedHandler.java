package com.zyl.sercurity.handler;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定401返回值
 *
 * @author hackyo
 * Created on 2017/12/9 20:10.
 */
@Component
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setStatus(401);
        System.out.println(e.getMessage());
        if(e instanceof UsernameNotFoundException) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        }else {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
        }
    }

}