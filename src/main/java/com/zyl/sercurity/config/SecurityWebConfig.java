package com.zyl.sercurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.zyl.sercurity.service.UserDetailServiceImpl;
import com.zyl.sercurity.utils.TokenUtils;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityWebConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    
    
//    @Bean
//    public AuthenticationSuccessHandler authenticationSuccessHandler() {
//        return new MyAuthenctiationSuccessHandler();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        .servletApi().rolePrefix("") //设置Role_Prefix  "_ROLE" -> ""
        .and()
//        .formLogin().loginPage("/login").successHandler(successHandler)
        .authorizeRequests()
        .antMatchers("/","/*.html","/favicon.ico","/**/*.html","/**/*.css","/**/*.js","/login","/user/register/**", "/user/register", "/user/token","/user/token/refresh").permitAll()
        .anyRequest().authenticated()
        .and()
        .exceptionHandling().authenticationEntryPoint(this.authenticationEntryPoint)
//        .withObjectPostProcessor(objectPostProcessor)
//          .and()
      ;
//          .formLogin()
//          .loginPage("/login.html")
//          .permitAll()
//          .and()
//      .logout()
//          .permitAll();
        }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//        .cors().disable()
//        .csrf().disable()
////        .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler)
////        .and()
////        .sessionManagement()
////        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////        .and()
//        .authorizeRequests()
//        .antMatchers("/assets/").permitAll()
//        .antMatchers(HttpMethod.GET, "/","/*.html","/favicon.ico","/**/*.html","/**/*.css","/**/*.js").permitAll()
//        .antMatchers("/login","/register", "/user/register", "/user/token","/user/token/refresh").permitAll()
//        .anyRequest().authenticated()
////        .and()
////        .rememberMe().tokenValiditySeconds(604800)//记住我功能，cookies有限期是一周
////        .rememberMeParameter("remember-me")//登陆时是否激活记住我功能的参数名字，在登陆页面有展示
////        .rememberMeCookieName("workspace")
//        .and()
//        ;
//        
////        http.addFilter(new JWTLoginFilter(authenticationManager()))  
////        .addFilter(new JWTAuthenticationFilter(authenticationManager()));
//        
//     // 添加JWT filter  
////        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);  
////        http.addFilterBefore(authenticationTokenFilter(), FilterSecurityInterceptor.class);
//        
//      // 禁用缓存  
////        http.headers().cacheControl();  
//        
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder())
        ;
    }
    
    
}
