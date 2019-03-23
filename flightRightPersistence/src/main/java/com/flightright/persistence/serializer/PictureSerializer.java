/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.persistence.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@Component
//@ConditionalOnMissingBean
public class PictureSerializer extends JsonSerializer<String> {
    
    @Value("${flightright.pictures-folder}")
    private String pictureFolder;

    @Override
    public void serialize(String t, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
        jg.writeString(new StringBuilder(pictureFolder).append("/").append(t).toString());
    }
    
}
