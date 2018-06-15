package com.zyl.sercurity.api;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.DeferredResult.DeferredResultHandler;
import org.springframework.web.context.request.async.WebAsyncTask;
import org.springframework.web.servlet.ModelAndView;

import com.zyl.sercurity.pojo.User;
import com.zyl.sercurity.pojo.UserDto;
import com.zyl.sercurity.service.UserService;
import com.zyl.sercurity.utils.TokenDetailImpl;
import com.zyl.sercurity.utils.TokenUtils;

@Controller
public class UserApi {
    @Autowired
    private UserService userService;

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
    @PostMapping("/user/register")
    public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid UserDto accountDto, BindingResult result,
            WebRequest request, Errors errors) {
        User registered = new User();
        if (!result.hasErrors()) {
            registered = userService.register(accountDto.getUsername(),accountDto.getPassword());
        }
        if (result.hasErrors()) {
            return new ModelAndView("registration", "user", accountDto);
        }
        return new ModelAndView("login", "user", accountDto);
    }

    @ResponseBody
    @PostMapping(value="/user/token")
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
    
    
    
    private ExecutorService executorService = Executors.newFixedThreadPool(10);
    
    @ResponseBody
    @GetMapping("/testdeferred")
    public DeferredResult<String> testDeferred() throws InterruptedException, ExecutionException, Exception{
        DeferredResult<String> deferredResult = new DeferredResult<>(2000L);
        
        Future<String> submit = executorService.submit(new Callable<String>() {

            @Override
            public String call() throws Exception {
                Thread.sleep(3000);
                String result = "休眠3s";
                deferredResult.setResult(result);
                return result;
            }
        });
//        
//        String string = submit.get(5, TimeUnit.SECONDS);
//        System.out.println(string);
        
        //调用完成后
        deferredResult.onCompletion(new Runnable() {
            
            @Override
            public void run() {
                //
                deferredResult.setResult("hello 成功");
                
            }
        });
        
      //调用超时
        deferredResult.onTimeout(new Runnable() {
            
            @Override
            public void run() {
                deferredResult.setResult("超时返回");
            }
        });
        return deferredResult;
    }
    
    @ResponseBody
    @GetMapping("/testWebAsyncTask")
    public WebAsyncTask<String> testWebAsyncTask() throws InterruptedException, ExecutionException, Exception{
        WebAsyncTask<String> deferredResult = new WebAsyncTask<>(3000L,new Callable<String>() {

            @Override
            public String call() throws Exception {
                Thread.sleep(3000);
                String result = "休眠3s";
                return result;
            }
        });
//        
//        String string = submit.get(5, TimeUnit.SECONDS);
//        System.out.println(string);
        
        //调用完成后
        deferredResult.onCompletion(new Runnable() {
            
            @Override
            public void run() {
                System.out.println("请求完成");
            }
        });
        
      //调用超时
        deferredResult.onTimeout(new Callable<String>() {

            @Override
            public String call() throws Exception {
                return "超时返回";
            }
            
        });
        return deferredResult;
    }
    

}
