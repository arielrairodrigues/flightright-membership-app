/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.rest.validation;

import com.flightright.rest.config.ApplicationProperty;
import static com.flightright.rest.util.Util.validFileExtension;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
public class ValidFileValidator implements ConstraintValidator<ValidFile, MultipartFile> {
    
    private final ApplicationProperty property;
    
    public ValidFileValidator(ApplicationProperty property) {
        this.property = property;
    }

    @Override
    public boolean isValid(MultipartFile t, ConstraintValidatorContext cvc) {
        // do some validation checks
        if (null == t)
            return customMessage(cvc, "Please provide the picture for this member");
        
        if (t.isEmpty() || null == t.getOriginalFilename() || t.getOriginalFilename().trim().isEmpty()) 
            return customMessage(cvc, "Please provide the picture for this member");

        // ensure that the size does not exceed the maximum size
        if (t.getSize() > property.getMaxPictureSize())
            return customMessage(cvc, new StringBuilder("Maximum allowable picture size exceeded: ").append(property.getMaxPictureSize())
                                                                                                    .append(" bytes").toString());

        return validFileExtension(t);
    }
    
    private boolean customMessage(ConstraintValidatorContext cvc, String message) {
        cvc.disableDefaultConstraintViolation();
        cvc.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return false;
    }
    
}
