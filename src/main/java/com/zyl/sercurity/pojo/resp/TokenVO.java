package com.zyl.sercurity.pojo.resp;

import java.io.Serializable;

public class TokenVO implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String accessToken;
    private Long expire;
    
//    private String access_token;  
//    private String token_type;  
//    private long expires_in; 
    
    public TokenVO(String accessToken, Long expire) {
        super();
        this.accessToken = accessToken;
        this.expire = expire;
    }
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public Long getExpire() {
        return expire;
    }
    public void setExpire(Long expire) {
        this.expire = expire;
    }
    
    
}
