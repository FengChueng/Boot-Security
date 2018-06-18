package com.zyl.sercurity.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.zyl.sercurity.pojo.User;
import com.zyl.sercurity.pojo.UserDto;
import com.zyl.sercurity.service.UserService;

@Controller
public class UserApi {
    @Autowired
    private UserService userService;

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

    @ResponseBody
    @PostMapping(value="/user/register",consumes=MediaType.APPLICATION_JSON_VALUE)
    public String registerUserAccount(@RequestBody @Valid UserDto accountDto, BindingResult result,
            WebRequest request, Errors errors) {
        User registered = new User();
        if (!result.hasErrors()) {
            registered = userService.register(accountDto.getUsername(),accountDto.getPassword());
        }
        if (result.hasErrors()) {
            List<ObjectError> allErrors = result.getAllErrors();
            allErrors.forEach(action -> System.out.println(action.getDefaultMessage()));
            return "error";
        }
        return "sucess";
    }

    @ResponseBody
    @PostMapping("/user/token")
    public String token(@RequestBody User user) {
       String token = userService.login(user.getUsername(), user.getPassword());
        return token;
    }

    // @ResponseBody
    // @GetMapping("/user/info/{token}")
    // public String userinfo(@PathVariable String token) {
    // String username = tokenUtils.getUsernameFromToken(token);
    // return "hello:" + username;
    // }

    @ResponseBody
    @GetMapping("/user/token/refresh")
    public String refreshToken(@RequestParam String token) {
        return userService.refreshToken(token);
    }
}
