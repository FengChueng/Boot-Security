package com.zyl.sercurity.properties;

/**
 * @Author: HanLong
 * @Date: Create in 2018/3/24 14:16
 * @Description:    验证码
 */
public class SmsCodeProperties {

    /**
     * 验证码长度
     */
    protected int length = 6;

    /**
     * 过期时间
     */
    protected int expireIn = 60;

    protected String url;

    public int getLength() {
        return length;
    }
    public void setLength(int lenght) {
        this.length = lenght;
    }
    public int getExpireIn() {
        return expireIn;
    }
    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
