/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.rest.resource;

import com.flightright.rest.validation.ValidDateOfBirth;
import com.flightright.rest.validation.ValidFile;
import com.flightright.rest.validation.ValidName;
import com.flightright.rest.validation.ValidPostalCode;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberResource {
    
    @ValidName(message = "Please provide the first name of this member")
    private String firstName;
    
    @ValidName(message = "Please provide the last name of this member")
    private String lastName;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ValidDateOfBirth
    private Date dateOfBirth;
    
    @ValidPostalCode(message = "Please provide a valid postal code")
    private String postalCode;
    
    @ValidFile
    private MultipartFile picture;
}
