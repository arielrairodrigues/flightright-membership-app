/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.rest.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@Documented
@Constraint(validatedBy = ValidPostalCodeValidator.class)
@Target( {ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPostalCode {
    String message() default "Please provide a valid postal code";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
