package com.zyl.sercurity.config;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.zyl.sercurity.filter.JwtAuthenticationTokenFilter;
import com.zyl.sercurity.sercurity.MyAuthenctiationSuccessHandler;
import com.zyl.sercurity.service.UserDetailServiceImpl;
import com.zyl.sercurity.utils.TokenUtils;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityWebConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    private EntryPointUnauthorizedHandler entryPointUnauthorizedHandler;
    @Autowired
    private RestAccessDeniedHandler restAccessDeniedHandler;
    
    @Bean
    public SessionRegistry getSessionRegistry(){
        SessionRegistry sessionRegistry=new SessionRegistryImpl();
        return sessionRegistry;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenUtils tokenUtils() {
        return new TokenUtils();
    }
    
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailServiceImpl();
    }
    
    
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new MyAuthenctiationSuccessHandler();
    }
    

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        .antMatchers("/css/**", "/js/**", "/login","/register", "/user/register", "/user/token","/user/token/refresh").permitAll()
//        .antMatchers("/hello", "/user/info/", "/user/token/refresh").authenticated()
        .anyRequest().authenticated()
//        .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {  
//            public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {  
//                fsi.setSecurityMetadataSource(filterInvocationSecurityMetadataSource());  
//                fsi.setAccessDecisionManager(accessDecisionManager());  
//                try {
//                    fsi.setAuthenticationManager(authenticationManagerBean());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }  
//                return fsi;  
//            }  
//        })
        .and().formLogin().loginPage("/login")
//        .loginProcessingUrl("/user/token")
        .defaultSuccessUrl("/index").permitAll()
        .successHandler(authenticationSuccessHandler())
        .and().rememberMe().tokenValiditySeconds(604800)//记住我功能，cookies有限期是一周
        .rememberMeParameter("remember-me")//登陆时是否激活记住我功能的参数名字，在登陆页面有展示
        .rememberMeCookieName("workspace")
        .and()
        //以下这句就可以控制单个用户只能创建一个session，也就只能在服务器登录一次     
//        .httpBasic().disable().anonymous()
        //使用JWT,不需要csrf
//        .and().csrf().disable() // 关闭CSRF
//        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//不需要session
//      .and()
//      .sessionManagement().maximumSessions(1).expiredUrl("/login")//不需要session
//      .sessionRegistry(getSessionRegistry())
//      
        ;
        
     // 添加JWT filter  
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);  
      // 禁用缓存  
        http.headers().cacheControl();  
        
        http.exceptionHandling().authenticationEntryPoint(entryPointUnauthorizedHandler).accessDeniedHandler(restAccessDeniedHandler);
//        http.addFilterBefore(authenticationTokenFilter(), FilterSecurityInterceptor.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
        .passwordEncoder(passwordEncoder())
        ;
        // or
//         auth.authenticationProvider(authenticationProvider());
    }
    
    
}
