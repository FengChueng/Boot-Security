package com.zyl.sercurity.properties;

/**
 * @Author: HanLong
 * @Date: Create in 2018/3/21 22:07
 * @Description:    图片验证码配置
 */
public class ImageProperties extends SmsCodeProperties{
    
    /**
     * 图片验证码默认长度为4
     */
    public ImageProperties() {setLength(4);}
    
//    /**
//     * 验证码长度
//     */
//    protected int length = 4;
//
//    /**
//     * 过期时间
//     */
//    protected int expireIn = 60;
//
//    protected String url;
//
//    public int getLength() {
//        return length;
//    }
//    public void setLength(int lenght) {
//        this.length = lenght;
//    }
//    public int getExpireIn() {
//        return expireIn;
//    }
//    public void setExpireIn(int expireIn) {
//        this.expireIn = expireIn;
//    }
//    public String getUrl() {
//        return url;
//    }
//    public void setUrl(String url) {
//        this.url = url;
//    }

    /**
     * 图片的宽
     */
    private int width = 60;

    /**
     * 图片的高
     */
    private int height = 40;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
