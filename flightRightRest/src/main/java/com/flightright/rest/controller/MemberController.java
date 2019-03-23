/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.rest.controller;

import com.flightright.api.response.ApiResponseFactory;
import com.flightright.api.response.ResponseFactory;
import com.flightright.api.response.ResponseFormat;
import com.flightright.rest.config.ApplicationProperty;
import com.flightright.rest.jms.MemberConsumerService;
import com.flightright.rest.resource.MemberResource;
import com.flightright.service.FileProcessorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    @Autowired
    private FileProcessorService fileProcessorService;
    
    @Autowired
    private ApplicationProperty property;
    
    @Autowired
    private MemberConsumerService memberConsumerService;
    
    @PostMapping("/create")
    @ApiOperation(value = "Create a new member", notes = "This endpoint manages the upload of listings by providers in CSV format", nickname = "Upload Vehicle Listings in CSV")
    public ResponseEntity createMember(@Valid @ModelAttribute MemberResource request) {
        // save picture
        if (!fileProcessorService.storePicture(request.getPicture().getInputStream(), request.getPicture().getOriginalFilename().split("\\."), property.getPicturesFolder()))
            return ResponseEntity.badRequest().body(ResponseFactory.createResponse(new ApiResponseFactory(ResponseFormat.PICTURE_ERROR.getStatus(), ResponseFormat.PICTURE_ERROR.getMessage())));
    }
}
