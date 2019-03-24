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
    private byte[] bytes;
    
    @Before
    public void setup() { 
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        try {
            bytes = IOUtils.toByteArray(this.getClass().getResource("/" + FILE_NAME));
        } catch (IOException ex) {
            log.error("Unable to write file to byte array", ex);
        }
    }
    
    
    @Test
//    @Ignore
    public void when_validMember_thenReturnOk() throws Exception {
        
        /**
         * {
                "responseCode": "00",
                "responseMessage": "Successful"
           }
         */
        String res = "{\"responseCode\":\"00\",\"responseMessage\":\"Successful\"}";
        
        //mock dependencies
        when(fileProcessorService.storePicture(any(), any(), any())).thenReturn(FILE_NAME);
        doNothing().when(memberConsumerService).saveMember(any());
        
        MockMultipartFile mockMultipartFile = new MockMultipartFile(FORM_NAME, bytes);
        
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
}
