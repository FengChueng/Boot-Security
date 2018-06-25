package com.zyl.sercurity.config;

import javax.annotation.Resource;
import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import com.zyl.sercurity.code.ValidateCodeFilter;
import com.zyl.sercurity.filter.JWTLoginFilter;
import com.zyl.sercurity.filter.JwtAuthenticationTokenFilter;
import com.zyl.sercurity.handler.EntryPointUnauthorizedHandler;
import com.zyl.sercurity.handler.RestAccessDeniedHandler;
import com.zyl.sercurity.sms.SmsCodeAuthenticationFilter;
import com.zyl.sercurity.sms.SmsCodeAuthenticationProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityWebConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private JWTLoginFilter jwtLoginFilter;
    
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    
    @Autowired
    private ValidateCodeFilter validateCodeFilter;
    
//    @Autowired
//    private AuthenticationEntryPoint authenticationEntryPoint;
//    
////    @Autowired
//    private AccessDeniedHandler accessDeniedHandler;
    
    @Resource(name="userDetailServiceImpl")
    private UserDetailsService userDetailsService;
    
    @Resource(name="smsUserDetailServiceImpl")
    private UserDetailsService smsUserDetailsService;
    
    
    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler myAuthenticationFailureHandler;
    
    @Bean
    public Filter filter() throws Exception {
        SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
        smsCodeAuthenticationFilter.setAuthenticationManager(authenticationManager());
        smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        smsCodeAuthenticationFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
        return smsCodeAuthenticationFilter;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new EntryPointUnauthorizedHandler();
    }
    
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new RestAccessDeniedHandler();
    }
    
    
//    @Bean
//    public AuthenticationSuccessHandler authenticationSuccessHandler() {
//        return new MyAuthenctiationSuccessHandler();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint()).accessDeniedHandler(accessDeniedHandler())
        .and()
        .csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        .servletApi().rolePrefix("") //设置Role_Prefix  "_ROLE" -> ""
        .and()
        .formLogin().loginPage("/authentication/require")
        .and()
        .authorizeRequests()
//        .antMatchers("/","/*.html","/favicon.ico","/**/*.html","/**/*.css","/**/*.js","/login","/user/register/**", "/user/register", "/user/token","/user/token/refresh").permitAll()
        .antMatchers("/*.html","/favicon.ico","/**/*.html","/**/*.css","/**/*.js","/login","/user/register/**", "/user/register", "/user/token","/user/token/refresh").permitAll()
        .antMatchers("/authentication/require", "/code/image","/code/sms").permitAll()
        .anyRequest().authenticated()
        ;
        
        http.addFilterBefore(jwtLoginFilter, UsernamePasswordAuthenticationFilter.class);
//        http.addFilterBefore(jwtAuthenticationTokenFilter,UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationTokenFilter,FilterSecurityInterceptor.class);
        
        
        http.addFilterBefore(validateCodeFilter, AbstractPreAuthenticatedProcessingFilter.class);
        
        http.addFilterBefore(filter(),UsernamePasswordAuthenticationFilter.class);
//        http.requestCache().requestCache(new NullRequestCache());
//      // 禁用缓存  
//        http.headers().cacheControl();
        
//    .and()
//    .rememberMe().tokenValiditySeconds(604800)//记住我功能，cookies有限期是一周
//    .rememberMeParameter("remember-me")//登陆时是否激活记住我功能的参数名字，在登陆页面有展示
//    .rememberMeCookieName("workspace")
        }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
        smsCodeAuthenticationProvider.setUserDetailsService(smsUserDetailsService);
        return smsCodeAuthenticationProvider;
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        
//        auth.authenticationProvider(authenticationProvider());
        
        auth.userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder());
    }
    
    
}
