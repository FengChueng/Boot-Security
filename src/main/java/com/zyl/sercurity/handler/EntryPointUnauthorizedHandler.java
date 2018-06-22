package com.zyl.sercurity.handler;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zyl.sercurity.exception.InvalidTokenException;
import com.zyl.sercurity.exception.ValidateCodeException;
import com.zyl.sercurity.pojo.resp.ErrorCode;
import com.zyl.sercurity.pojo.resp.ErrorResponse;

/**
 * 自定401返回值
 *
 * @author hackyo
 * Created on 2017/12/9 20:10.
 */
@Component
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {
    @Autowired
    private ObjectMapper mapper;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
      response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setStatus(401);
        System.out.println("EntryPointUnauthorizedHandler:"+e.getMessage());
        response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        if(e instanceof InvalidTokenException){
            mapper.writeValue(response.getWriter(),
                    ErrorResponse.of("Token失效,请重新登录", ErrorCode.JWT_TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED));
//            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
        } else if(e instanceof ValidateCodeException){
            mapper.writeValue(response.getWriter(),
                    ErrorResponse.of(e.getMessage(), ErrorCode.JWT_TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED));
//            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
        } else {
            mapper.writeValue(response.getWriter(),
                    ErrorResponse.of("身份验证失败", ErrorCode.JWT_TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED));
//            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
        }
    }

}