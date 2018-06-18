package com.zyl.sercurity.exception;

import org.springframework.security.core.AuthenticationException;


/**
 * 过期的Token
 * 
 * @author Levin
 *
 * @since 2017-05-25
 */
public class ExpiredTokenException extends AuthenticationException {
    private static final long serialVersionUID = -5959543783324224864L;
    
    private String token;

    public ExpiredTokenException(String msg) {
        super(msg);
    }

    public ExpiredTokenException(String token, String msg, Throwable t) {
        super(msg, t);
        this.token = token;
    }

    public String token() {
        return this.token;
    }
}
