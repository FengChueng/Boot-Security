package com.zyl.sercurity.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.util.CollectionUtils;

/**
 * Created by liyd on 16/12/9.
 */
public class CustomSecurityContext {


    private static final Map<String, Collection<ConfigAttribute>> METADATA_SOURCE_MAP = new HashMap<String, Collection<ConfigAttribute>>();

    public static Map<String, Collection<ConfigAttribute>> getMetadataSource() {

        if (CollectionUtils.isEmpty(METADATA_SOURCE_MAP)) {

            synchronized (CustomSecurityContext.class) {

                loadMetadataSource();
            }
        }
        return new HashMap<String, Collection<ConfigAttribute>>(METADATA_SOURCE_MAP);
    }


    private static void loadMetadataSource() {

        if (!CollectionUtils.isEmpty(METADATA_SOURCE_MAP)) {
            return;
        }

        try {
            ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resourcePatternResolver.getResources("classpath*:auth.properties");
            if (ArrayUtils.isEmpty(resources)) {
                System.out.println("null");
                return;
            }

            Properties properties = new Properties();
            for (Resource resource : resources) {
                properties.load(resource.getInputStream());
            }

            for (Map.Entry<Object, Object> entry : properties.entrySet()) {

                String key = (String) entry.getKey();
                String value = (String) entry.getValue();

                String[] values = StringUtils.split(value, ",");

                Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
                ConfigAttribute configAttribute = new SecurityConfig(key);
                configAttributes.add(configAttribute);

                for (String v : values) {
                    METADATA_SOURCE_MAP.put(StringUtils.trim(v), configAttributes);
                }
            }
            System.out.println(METADATA_SOURCE_MAP.size());
            
            
        } catch (IOException e) {
            throw new RuntimeException("加载MetadataSource失败", e);
        }
    }

}
