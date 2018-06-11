package com.zyl.sercurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@Configuration
@EnableWebSecurity
public class BootSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootSecurityApplication.class, args);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
         return new BCryptPasswordEncoder();
     }

}
