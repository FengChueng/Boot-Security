package com.zyl.sercurity.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zyl.sercurity.utils.TokenDetailImpl;
import com.zyl.sercurity.utils.TokenUtils;

@Controller
public class UserApi {
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private TokenUtils tokenUtils;

    @ResponseBody
    @GetMapping("/hello")
    public String hello() {
        return "hello,boot-sercurity";
    }

    @ResponseBody
    @GetMapping("/user/register/{username}")
    public String register(@PathVariable String username) {
        return "register succussful : " + username;
    }

//    @ResponseBody
    @PostMapping("/user/token")
    public String token(String username, String userpasswd,ModelAndView modelAndView) {
        //验证账号密码
        String token = tokenUtils.generateToken(new TokenDetailImpl(username));
//        return token;
        modelAndView.addObject("token", token);
        return "index";
    }

//    @ResponseBody
//    @GetMapping("/user/info/{token}")
//    public String userinfo(@PathVariable String token) {
//        String username = tokenUtils.getUsernameFromToken(token);
//        return "hello:" + username;
//    }
    
    @ResponseBody
    @GetMapping("/user/token/refresh")
    public String refreshToken(@RequestParam String token) {
        return "123";
    }
    
}