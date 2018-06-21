package com.zyl.sercurity.code;

import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeGenerator {

    /**
     * 生成图片验证码
     *
     * @param request 请求
     * @return ImageCode实例对象
     */
    ValidateCode generate(ServletWebRequest request);
}