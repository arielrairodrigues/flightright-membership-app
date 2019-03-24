/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.rest.validation;

import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
public class ValidPostalCodeValidator implements ConstraintValidator<ValidPostalCode, String> {

    private static final Pattern POST_CODE_PATTERN = Pattern.compile("^\\d{6}$");
    
    @Override
    public boolean isValid(String t, ConstraintValidatorContext cvc) {
        return null != t && POST_CODE_PATTERN.matcher(t).matches();
    }
    
}
