/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.rest.controller;

import com.flightright.api.response.ApiResponseFactory;
import com.flightright.api.response.ResponseFactory;
import com.flightright.api.response.ResponseFormat;
import com.flightright.persistence.model.Member;
import com.flightright.rest.config.ApplicationProperty;
import com.flightright.rest.jms.MemberConsumerService;
import com.flightright.rest.resource.MemberResource;
import static com.flightright.rest.util.Util.convertObjectToJson;
import com.flightright.service.FileProcessorService;
import com.flightright.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @Autowired
    private MemberService memberService;
    
    @PostMapping(value = "/create", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ApiOperation(value = "Create a new member", notes = "This endpoint manages the cretation of a new member", nickname = "Create Member")
    public ResponseEntity createMember(@Valid @ModelAttribute MemberResource request) throws IOException {
        // save picture
        String fileName = fileProcessorService.storePicture(request.getPicture().getInputStream(), request.getPicture().getOriginalFilename().split("\\."), property.getPicturesFolder());
        if (null == fileName)
            return ResponseEntity.badRequest().body(ResponseFactory.createResponse(new ApiResponseFactory(ResponseFormat.PICTURE_ERROR.getStatus(), ResponseFormat.PICTURE_ERROR.getMessage())));
        
        Member member = new Member.Builder().setDateOfBirth(request.getDateOfBirth())
                                            .setFirstName(request.getFirstName())
                                            .setLastName(request.getLastName())
                                            .setPostalCode(request.getPostalCode())
                                            .setPicture(fileName).build();
        // send to the queue
        memberConsumerService.saveMember(convertObjectToJson(member));
        return ResponseEntity.badRequest().body(ResponseFactory.createResponse(new ApiResponseFactory(ResponseFormat.SUCCESSFUL.getStatus(), ResponseFormat.SUCCESSFUL.getMessage())));
    }
    
    @GetMapping(value = "/get-member/{memberId}")
    public ResponseEntity getMember(@PathVariable @Min(value = 1, message = "Invalid member ID provided") Long id) {
        Member member = memberService.findMember(id);
        if (null == member)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseFactory.createResponse(new ApiResponseFactory(ResponseFormat.MEMBER_DOES_NOT_EXIST.getStatus(), ResponseFormat.MEMBER_DOES_NOT_EXIST.getMessage())));
        return ResponseEntity.ok(member);
    }
}
