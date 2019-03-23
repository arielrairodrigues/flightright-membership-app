/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.rest.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
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
}
