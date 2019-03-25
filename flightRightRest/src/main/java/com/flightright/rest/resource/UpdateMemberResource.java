/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.rest.resource;

import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
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
public class UpdateMemberResource {
    
    @Pattern(regexp = "[a-zA-Z0-9\\-, ]+", message = "Please provide the first name of this member")
    private String firstName;
    
    @Pattern(regexp = "[a-zA-Z0-9\\-, ]+", message = "Please provide the last name of this member")
    private String lastName;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Please enter the date of birth for this member")
    @Past(message = "Date of birth must be a past date")
    private Date dateOfBirth;
    
    @Pattern(regexp = "^\\d{6}$", message = "Please enter a valid postal code")
    private String postalCode;
    
    private MultipartFile picture;
}
