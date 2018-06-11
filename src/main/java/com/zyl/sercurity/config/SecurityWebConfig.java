package com.zyl.sercurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.zyl.sercurity.filter.AuthenticationTokenFilter;
import com.zyl.sercurity.utils.TokenUtils;

@Configuration
@EnableWebSecurity
public class SecurityWebConfig extends WebSecurityConfigurerAdapter{
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    
    @Bean
    public PasswordEncoder  passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public TokenUtils tokenUtils() {
        return new TokenUtils();
    }
    
    @Bean
    public AuthenticationTokenFilter authenticationTokenFilter() throws Exception {
        AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter();
        authenticationTokenFilter.setAuthenticationManager(authenticationManager());
        return authenticationTokenFilter;
    }
    
    
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        .antMatchers("/css/**","/js/**","/user/login","/user/register","/user/token").permitAll()
        .antMatchers("/hello","/user/info/","/user/token/refresh").authenticated()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/user/login")
        .defaultSuccessUrl("/")
//        .loginProcessingUrl("/user/token")  // 自定义的登录接口
        .permitAll()
        .and()
        .httpBasic().disable().anonymous()
        .and()
        .csrf().disable() //关闭CSRF
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.formLogin()                    //  定义当需要用户登录时候，转到的登录页面。
//        .and()
//        .authorizeRequests()        // 定义哪些URL需要被保护、哪些不需要被保护
//        .anyRequest()               // 任何请求,登录后可以访问
//        .authenticated();
        
        http.addFilterBefore(authenticationTokenFilter(), AuthenticationTokenFilter.class);
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("admin").password("123456").roles("USER");
        auth.userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder());
//        or
//        auth.authenticationProvider(authenticationProvider);
        
    }
}
