/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.rest.resource;

import com.flightright.rest.validation.ValidFile;
import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
    
    @Pattern(regexp = "[a-zA-Z0-9\\-, ]+", message = "Please provide the first name of this member")
    private String firstName;
    
    @Pattern(regexp = "[a-zA-Z0-9\\-, ]+", message = "Please provide the last name of this member")
    private String lastName;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    
    @Pattern(regexp = "^\\d{6}$", message = "Please provide a valid postal code")
    private String postalCode;
    
    @NotNull(message = "Please provided the picture for this member")
    @ValidFile
    private MultipartFile picture;
}
