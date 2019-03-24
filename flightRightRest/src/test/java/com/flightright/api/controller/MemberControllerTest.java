/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.api.controller;

import com.flightright.rest.config.ApplicationProperty;
import com.flightright.rest.controller.MemberController;
import com.flightright.rest.jms.MemberConsumerService;
import com.flightright.service.FileProcessorService;
import com.flightright.service.MemberService;
import java.io.File;
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
import org.apache.commons.io.FileUtils;
import org.hamcrest.Matchers;
import static org.junit.Assert.assertEquals;
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
    private File file;
    private byte[] bytes;
    
    @Before
    public void setup() { 
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        file = new File(FILE_NAME);
        try {
            bytes = FileUtils.readFileToByteArray(file);
        } catch (IOException ex) {
            log.error("Unable to write file to byte array", ex);
        }
    }
    
    
    @Test
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
        
        MockMultipartFile mockMultipartFile = new MockMultipartFile(FORM_NAME,FILE_NAME,
                                                            MediaType.MULTIPART_FORM_DATA_VALUE, bytes);
        
         MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.fileUpload("/members/create")
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
}
