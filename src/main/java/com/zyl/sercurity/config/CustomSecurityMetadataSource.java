package com.zyl.sercurity.config;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

/**
 * Created by liyd on 16/12/9.
 */
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {


    
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        Map<String, Collection<ConfigAttribute>> metadataSource = CustomSecurityContext.getMetadataSource();
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
       // 返回当前 url  所需要的权限
         return metadataSource.get(requestUrl);
    }

//    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
//        FilterInvocation fi = (FilterInvocation) object;
//        Map<String, Collection<ConfigAttribute>> metadataSource = CustomSecurityContext.getMetadataSource();
//        
//        for (Map.Entry<String, Collection<ConfigAttribute>> entry : metadataSource.entrySet()) {
//            String uri = entry.getKey();
//            RequestMatcher requestMatcher = new AntPathRequestMatcher(uri);
//            if (requestMatcher.matches(fi.getHttpRequest())) {
//                return entry.getValue();
//            }
//        }
//        return null;
//    }

    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
