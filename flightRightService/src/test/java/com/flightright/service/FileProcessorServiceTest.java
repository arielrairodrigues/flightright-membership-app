/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.service;

import com.flightright.service.impl.FileProcessorServiceImpl;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import static org.hamcrest.Matchers.startsWith;
import org.junit.After;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@Slf4j
public class FileProcessorServiceTest {
    
    private FileProcessorService fileProcessorService;
    private InputStream is;
    private static final String PICTURE_PATH = "c:/flightright/members/pictures";
    private static final String FILE_NAME = "test_file";
    private static final String FILE_EXTENSION = "jpg";
    
    @Before
    public void setup() {
        fileProcessorService = new FileProcessorServiceImpl();
        is = this.getClass().getResourceAsStream("/cert2.jpg");
    }
    
    /**
     * Test case to store file
     * @throws Exception 
     */
    @Test
    public void when_storePicture_thenReturnFileName() throws Exception {
        String fileName = fileProcessorService.storePicture(is, new String[]{FILE_NAME, FILE_EXTENSION}, PICTURE_PATH);
        assertNotNull(fileName);
        assertThat(fileName, startsWith(FILE_NAME));
    }
    
    /**
     * Clean up after testing
     */
    @After
    public void cleanUp() {
        String file = null;
        try {
            file = new StringBuilder(PICTURE_PATH).append("/cert2.jpg").toString();
            Files.deleteIfExists(Paths.get(file));
        } catch (IOException ex) {
            log.error("Unable to delete the file {} after testing", file, ex);
        }
    }
}
