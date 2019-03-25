/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.rest.validation.service;

import java.util.Set;
import javax.validation.ConstraintViolation;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
public interface ValidationService {
    /**
     * Validate Picture
     * @param file
     * @param clazz
     * @return 
     */
    Set<ConstraintViolation<MultipartFile>> validatePicture(MultipartFile file, Class<?> clazz);
}
