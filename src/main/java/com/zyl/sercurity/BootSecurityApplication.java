package com.zyl.sercurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@Configuration
@EnableWebSecurity
public class BootSecurityApplication extends WebSecurityConfigurerAdapter{

    public static void main(String[] args) {
        SpringApplication.run(BootSecurityApplication.class, args);
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello,boot-sercurity";
    }

    @GetMapping("/user/register/{username}")
    public String register(@PathVariable String username) {
        return "register succussful : " + username;
    }
    
    @GetMapping("/user/login")
    public String login(@PathVariable String username) {
        return "register succussful : " + username;
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
        .and()
        .httpBasic().disable().anonymous()
        .and()
        .authorizeRequests().anyRequest().authenticated()
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
    }
    

}
