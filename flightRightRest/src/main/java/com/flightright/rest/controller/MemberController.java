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
import com.flightright.rest.resource.UpdateMemberResource;
import com.flightright.rest.util.Util;
import static com.flightright.rest.util.Util.convertObjectToJson;
import com.flightright.rest.validation.ValidFile;
import com.flightright.service.FileProcessorService;
import com.flightright.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.groups.Default;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.flightright.rest.validation.service.ValidationService;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@RestController
@Validated(value = {Default.class})
@RequestMapping("/members")
@Slf4j
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
    
    @Autowired
    private ValidationService validationService;
    
    /**
     * Create new member
     * @param request
     * @param result
     * @return
     * @throws IOException 
     */
    @PostMapping(value = "/create", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ApiOperation(value = "Create a new member", notes = "This endpoint manages the cretation of a new member", nickname = "Create Member")
    public ResponseEntity createMember(@Valid @ModelAttribute MemberResource request, BindingResult result) throws IOException {
        
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
        return ResponseEntity.ok(ResponseFactory.createResponse(new ApiResponseFactory(ResponseFormat.SUCCESSFUL.getStatus(), ResponseFormat.SUCCESSFUL.getMessage())));
    }
    
    /**
     * Gets a member by ID
     * @param id
     * @return 
     */
    @GetMapping(value = "/get/{memberId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ApiOperation(value = "Get Member", notes = "This endpoint returns a profiled member", nickname = "Get Member By ID")
    public ResponseEntity getMember(@PathVariable("memberId") Long id) {
        Member member = memberService.findMember(id);
        if (null == member)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseFactory.createResponse(new ApiResponseFactory(ResponseFormat.MEMBER_DOES_NOT_EXIST.getStatus(), ResponseFormat.MEMBER_DOES_NOT_EXIST.getMessage())));
        return ResponseEntity.ok(member);
    }
    
    /**
     * Gets all members profiled in the system
     * @return 
     */
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ApiOperation(value = "Get All Members", notes = "This endpoint returns all the memnbers profiled in the system", nickname = "Get All Members")
    public List<Member> getAllMembers() {
        return memberService.findAll();
    }
    
    /**
     * Delete a member
     * @param id
     * @return 
     */
    @DeleteMapping(value = "/delete/{memberId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ApiOperation(value = "Delete Member", notes = "This endpoint deletes the profile of an unused member", nickname = "Delete Member")
    public ResponseEntity deleteMember(@PathVariable("memberId") Long id) {
        Member member = memberService.findMember(id);
        
        if (null == member)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseFactory.createResponse(new ApiResponseFactory(ResponseFormat.MEMBER_DOES_NOT_EXIST.getStatus(), ResponseFormat.MEMBER_DOES_NOT_EXIST.getMessage())));
        
        memberConsumerService.deleteMember(convertObjectToJson(member));
        return ResponseEntity.ok(ResponseFactory.createResponse(new ApiResponseFactory(ResponseFormat.SUCCESSFUL.getStatus(), ResponseFormat.SUCCESSFUL.getMessage())));
    }
    
    
    @PutMapping(value = "/update/{memberId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ApiOperation(value = "Update Member", notes = "This endpoint updates the profile of a profiled member", nickname = "Update Member")
    public ResponseEntity updateMember(@Valid @ModelAttribute UpdateMemberResource request,
                                       BindingResult result,
                                       @PathVariable("memberId") Long id) throws IOException {
        
        Member member = memberService.findMember(id);
        if (null == member)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseFactory.createResponse(new ApiResponseFactory(ResponseFormat.MEMBER_DOES_NOT_EXIST.getStatus(), ResponseFormat.MEMBER_DOES_NOT_EXIST.getMessage())));
        
        String fileName = null;
        if (null != request.getPicture()) {
            // validate file
            Set<ConstraintViolation<MultipartFile>> violations = validationService.validatePicture(request.getPicture(), ValidFile.class);
            if (null != violations && !violations.isEmpty())
                    throw new ConstraintViolationException(violations);
            
            // update picture
            fileName = fileProcessorService.updatePicture(request.getPicture().getInputStream(), 
                                                                 request.getPicture().getOriginalFilename().split("\\."),
                                                                 new StringBuilder(property.getPicturesFolder()).append("/").append(member.getPicture()).toString(),
                                                                 property.getPicturesFolder());
            if (null == fileName)
                return ResponseEntity.badRequest().body(ResponseFactory.createResponse(new ApiResponseFactory(ResponseFormat.PICTURE_UPDATE_ERROR.getStatus(), ResponseFormat.PICTURE_UPDATE_ERROR.getMessage())));
        }
        // send to the queue
        Util.updateMember(member, request, fileName);
        memberConsumerService.updateMember(convertObjectToJson(member));
        return ResponseEntity.ok(ResponseFactory.createResponse(new ApiResponseFactory(ResponseFormat.SUCCESSFUL.getStatus(), ResponseFormat.SUCCESSFUL.getMessage())));
    }
}
