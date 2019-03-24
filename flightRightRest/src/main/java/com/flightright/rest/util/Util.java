/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.rest.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightright.persistence.model.Member;
import com.flightright.rest.resource.MemberResource;
import com.flightright.rest.resource.UpdateMemberResource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@Slf4j
public final class Util {
    
    private Util() {} // prevents this class from being initialized
    
    /**
     * Converts an object to string
     * @param <T>
     * @param object
     * @return 
     */
    public static <T> String convertObjectToJson(T object) {
        try {
            ObjectMapper objMapper = new ObjectMapper();
            return objMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Unable to convert object to a string", e);
        }
        return null;
    }
    
    /**
     * Convert a string to object
     * @param <T>
     * @param data
     * @param clazz
     * @return 
     */
    public static <T> T convertStringToObject(String data, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            T obj = mapper.readValue(data, clazz);
            return obj;
        } catch (IOException e) {
            log.error("Unable to parse json string {}", data, e);
        }
        return null;
    }
    
    public static boolean validFileExtension(MultipartFile file) {
        String extension = file.getOriginalFilename().split("\\.")[1];
        return (null != extension && !extension.trim().isEmpty() && 
                                    (extension.trim().equalsIgnoreCase("jpg") || extension.trim().equalsIgnoreCase("jpeg") || extension.trim().equalsIgnoreCase("png")));
    }
    
    public static void updateMember(Member member, UpdateMemberResource request, String fileName) {
        member.setDateOfBirth(request.getDateOfBirth());
        member.setFirstName(request.getFirstName());
        member.setLastName(request.getLastName());
        if (null != fileName)
            member.setPicture(fileName);
        member.setPostalCode(request.getPostalCode());
    }
    
    /**
     * Mock the list of members
     * @return 
     */
    public static List<Member> mockMembers() {
        Member member = new Member(1L, "Nonso", "Megafu", null, "100212", "picture.jgp", null, null);
        List<Member> members = new ArrayList<>();
        members.add(member);
        return members;
    }
}
