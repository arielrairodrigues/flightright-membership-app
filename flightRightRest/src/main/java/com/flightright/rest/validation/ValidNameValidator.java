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
public class ValidNameValidator implements ConstraintValidator<ValidName, String> {
    
    private static final Pattern NAME_PATTERN = Pattern.compile("[a-zA-Z0-9\\-, ]+");

    @Override
    public boolean isValid(String t, ConstraintValidatorContext cvc) {
        return null != t && NAME_PATTERN.matcher(t).matches();
    }
    
}
