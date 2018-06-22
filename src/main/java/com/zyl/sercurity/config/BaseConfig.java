package com.zyl.sercurity.config;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zyl.sercurity.code.sms.DefaultSmsCodeSender;
import com.zyl.sercurity.code.sms.SmsCodeSender;
import com.zyl.sercurity.properties.SecurityProperties;

@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class BaseConfig {
    /**
     * Hibernate快速失败返回
     * @return
     */
    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure()
                .addProperty("hibernate.validator.fail_fast", "true").buildValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        return validator;
    }
    
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
    
    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeGenerator() {
        SmsCodeSender smsCodeSender = new DefaultSmsCodeSender();
        return smsCodeSender;
    }
}
