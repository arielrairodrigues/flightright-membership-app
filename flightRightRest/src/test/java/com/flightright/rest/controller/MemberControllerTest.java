/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.rest.controller;

import com.flightright.rest.config.ApplicationProperty;
import com.flightright.rest.jms.MemberConsumerService;
import static com.flightright.rest.util.Util.convertObjectToJson;
import static com.flightright.rest.util.Util.mockMembers;
import com.flightright.service.FileProcessorService;
import com.flightright.service.MemberService;
import java.io.IOException;
import javax.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.apache.commons.io.IOUtils;
import org.hamcrest.Matchers;
import static org.junit.Assert.assertEquals;
import org.junit.Ignore;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@RunWith(SpringRunner.class)
@WebMvcTest(MemberController.class)
@Slf4j
public class MemberControllerTest {
    
    @Autowired
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext wac; 
    
    @MockBean
    private FileProcessorService fileProcessorService;
    
    @MockBean
    private ApplicationProperty property;
    
    @MockBean
    private MemberConsumerService memberConsumerService;
    
    @MockBean
    private MemberService memberService;
    
    @MockBean
    private Validator validator;
    
    private static final String FORM_NAME = "picture";
    private static final String FILE_NAME = "cert2.jpg";
    private static final String LARGE_FILE = "large.jpg";
    private byte[] bytes;
    
    @Before
    public void setup() { 
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    
    
    @Test
    @Ignore
    public void when_validMember_thenReturnOk() throws Exception {
        
        /**
         * {
                "responseCode": "00",
                "responseMessage": "Successful"
           }
         */
        String res = "{\"responseCode\":\"00\",\"responseMessage\":\"Successful\"}";
        normalImageSize();
        
        //mock dependencies
        when(fileProcessorService.storePicture(any(), any(), any())).thenReturn(FILE_NAME);
        doNothing().when(memberConsumerService).saveMember(any());
        
        MockMultipartFile mockMultipartFile = new MockMultipartFile(FORM_NAME, bytes);
        log.info("The size of the image is {}", mockMultipartFile.getSize());
        
         MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/members/create")
                                                                        .file(mockMultipartFile)
                                                                        .param("dateOfBirth", "2021-09-06")
                                                                        .param("firstName", "ela333")
                                                                        .param("postalCode", "100212")
                                                                        .param("lastName", "dfdfdfdffdf");
         
         MvcResult result = mvc.perform(requestBuilder)
                            .andDo(print())
                            .andExpect(status().isOk())
                            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                            .andExpect(jsonPath("$.responseCode", Matchers.is("00")))
                            .andExpect(jsonPath("$.responseMessage", Matchers.is("Successful"))).andReturn();
         
         // verify that the services are called
         verify(fileProcessorService, times(1)).storePicture(any(), any(), any());
         verify(memberConsumerService, times(1)).saveMember(any());
         assertEquals(result.getResponse().getContentAsString(), res);
    }
    
    /**
     * Test get all members
     * @throws Exception 
     */
    @Test
//    @Ignore
    public void when_getAllMembers_thenReturnOk() throws Exception {
        
        /**
         * [
                {
                    "id": 1,
                    "firstName": "Nonso",
                    "lastName": "Megafu",
                    "dateOfBirth": null,
                    "postalCode": "100212",
                    "picture": "picture.jgp",
                    "createdAt": null,
                    "updatedAt": null
                }
            ]
         */
        String res = convertObjectToJson(mockMembers());
        when(memberService.findAll()).thenReturn(mockMembers());
        
        MvcResult result = mvc.perform(get("/members/")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                        .andExpect(jsonPath("$.[0].firstName", Matchers.is("Nonso")))
                        .andExpect(jsonPath("$.[0].lastName", Matchers.is("Megafu"))).andReturn();
        
        // verify that the services are called
         verify(memberService, times(1)).findAll();
         assertEquals(result.getResponse().getContentAsString(), res);
    }
    
    /**
     * Test Case for get member
     * @throws Exception 
     */
    @Test
    public void when_getMemberById_thenReturnOk() throws Exception {
        /**
         * 
         * {
                "id": 1,
                "firstName": "Nonso",
                "lastName": "Megafu",
                "dateOfBirth": null,
                "postalCode": "100212",
                "picture": "picture.jgp",
                "createdAt": null,
                "updatedAt": null
            }
         */
        String res = convertObjectToJson(mockMembers().get(0));
        when(memberService.findMember(1L)).thenReturn(mockMembers().get(0));
        
        MvcResult result = mvc.perform(get("/members/get/1")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                        .andExpect(jsonPath("$.firstName", Matchers.is("Nonso")))
                        .andExpect(jsonPath("$.lastName", Matchers.is("Megafu"))).andReturn();
        
        // verify that the services are called
         verify(memberService, times(1)).findMember(any());
         assertEquals(result.getResponse().getContentAsString(), res);
    }
    
    /**
     * Test case for xml return type
     * @throws Exception 
     */
    @Test
    public void when_acceptTypeIsXml_thenReturnXml() throws Exception {
        /**
         * 
         * <Member>
                <id>1</id>
                <firstName>ela333</firstName>
                <lastName>dfdfdfdffdf</lastName>
                <dateOfBirth>2014-09-06</dateOfBirth>
                <postalCode>100212</postalCode>
                <picture>cert2_fq546GB9x3zh.jpg</picture>
                <createdAt>2019-03-25T06:03:11.022+0000</createdAt>
                <updatedAt>2019-03-25T06:03:11.022+0000</updatedAt>
            </Member>
         */
        String res = "<Member><id>1</id><firstName>Nonso</firstName><lastName>Megafu</lastName><dateOfBirth/><postalCode>100212</postalCode><picture>picture.jgp</picture><createdAt/><updatedAt/></Member>";
        when(memberService.findMember(1L)).thenReturn(mockMembers().get(0));
        
        MvcResult result = mvc.perform(get("/members/get/1")
                        .accept(MediaType.APPLICATION_XML))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(content().contentType("application/xml;charset=UTF-8")).andReturn();
        
        // verify that the services are called
         verify(memberService, times(1)).findMember(any());
         assertEquals(result.getResponse().getContentAsString(), res);
    }
    
    /**
     * Test case for delete member
     * @throws Exception 
     */
    @Test
    public void when_deleteMember_thenReturnOk() throws Exception {
        /**
         * {
                "responseCode": "00",
                "responseMessage": "Successful"
           }
         */
        String res = "{\"responseCode\":\"00\",\"responseMessage\":\"Successful\"}";
        when(memberService.findMember(1L)).thenReturn(mockMembers().get(0));
        
        MvcResult result = mvc.perform(delete("/members/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                        .andExpect(jsonPath("$.responseCode", Matchers.is("00")))
                        .andExpect(jsonPath("$.responseMessage", Matchers.is("Successful"))).andReturn();
        
        // verify that the services are called
         verify(memberService, times(1)).findMember(any());
         assertEquals(result.getResponse().getContentAsString(), res);
    }
    
    private void normalImageSize() {
        try {
            bytes = IOUtils.toByteArray(this.getClass().getResource("/" + FILE_NAME));
        } catch (IOException ex) {
            log.error("Unable to write file to byte array", ex);
        }
    }
    
    private void largeImageSize() {
        try {
            bytes = IOUtils.toByteArray(this.getClass().getResource("/" + LARGE_FILE));
        } catch (IOException ex) {
            log.error("Unable to write file to byte array", ex);
        }
    }
}
