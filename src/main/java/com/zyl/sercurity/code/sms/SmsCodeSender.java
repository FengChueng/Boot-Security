package com.zyl.sercurity.code.sms;

/**
 * @Author: HanLong
 * @Date: Create in 2018/3/24 11:09
 * @Description:
 */
public interface SmsCodeSender {

    void send(String phone, String code);
}
