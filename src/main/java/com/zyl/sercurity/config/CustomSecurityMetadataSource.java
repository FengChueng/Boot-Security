package com.zyl.sercurity.config;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Collection;
import java.util.Map;

/**
 * Created by liyd on 16/12/9.
 */
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {


    
    
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object;


//        String requestURI = fi.getRequest().getRequestURI();
//        String contextPath = fi.getRequest().getContextPath();
//        if (StringUtils.length(contextPath) > 0){
//            requestURI = StringUtils.substring(requestURI,contextPath.length());
//        }

        Map<String, Collection<ConfigAttribute>> metadataSource = CustomSecurityContext.getMetadataSource();
        
        for (Map.Entry<String, Collection<ConfigAttribute>> entry : metadataSource.entrySet()) {
            String uri = entry.getKey();
            RequestMatcher requestMatcher = new AntPathRequestMatcher(uri);
            if (requestMatcher.matches(fi.getHttpRequest())) {
                return entry.getValue();
            }
        }

        return null;
    }

    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
