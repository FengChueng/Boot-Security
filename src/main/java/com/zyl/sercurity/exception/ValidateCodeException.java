package com.zyl.sercurity.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Author: HanLong
 * @Date: Create in 2018/3/21 21:11
 * @Description:
 */
public class ValidateCodeException extends AuthenticationException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
