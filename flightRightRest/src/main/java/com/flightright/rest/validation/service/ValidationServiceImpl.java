/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.rest.validation.service;

import java.util.Set;
import javax.validation.ConstraintViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@Service
public class ValidationServiceImpl implements ValidationService {
    
    private final Validator validator;
    
    @Autowired
    public ValidationServiceImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public Set<ConstraintViolation<MultipartFile>> validatePicture(MultipartFile file, Class<?> clazz) {
        return validator.validate(file, clazz);
    }
    
}
