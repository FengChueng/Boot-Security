package com.zyl.sercurity.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.zyl.sercurity.pojo.UserDto;

public class PasswordMatchesValidator 
  implements ConstraintValidator<PasswordMatches, Object> { 
     
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {       
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){   
        UserDto user = (UserDto) obj;
        return user.getPassword().equals(user.getMatchingPassword());    
    }     
}