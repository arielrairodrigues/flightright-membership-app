/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.rest.controller;

import com.flightright.rest.resource.MemberResource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@RestController("/member")
@Slf4j
@Validated
@Api(value = "Member-Controller", description = "To manage all the endpoints for membership", tags = {"Create Member, Read Member, Update Member, Delete Member"})
public class MemberController {
    
    @PostMapping("/create")
    @ApiOperation(value = "Create a new member", notes = "This endpoint manages the upload of listings by providers in CSV format", nickname = "Upload Vehicle Listings in CSV")
    public ResponseEntity createMember(@Valid @ModelAttribute MemberResource request) {
        // save 
    }
}
