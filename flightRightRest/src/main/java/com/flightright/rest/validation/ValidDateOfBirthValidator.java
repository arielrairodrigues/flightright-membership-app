/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.rest.validation;

import java.util.Date;
import java.util.Set;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.Past;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@Slf4j
public class ValidDateOfBirthValidator implements ConstraintValidator<ValidDateOfBirth, Date> {

    private final Validator validator;
    
    public ValidDateOfBirthValidator(Validator validator) {
        this.validator = validator;
    }
    
    @Override
    public boolean isValid(Date t, ConstraintValidatorContext cvc) {
        if (null == t) 
            return false;
        
        Set<ConstraintViolation<Date>> violations = validator.validate(t, Past.class);
        return null == violations || violations.isEmpty();
    }
    
}
