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
import lombok.extern.slf4j.Slf4j;
import static org.hamcrest.Matchers.startsWith;
import org.junit.AfterClass;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
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
    private static final String PICTURE_PATH = "c:/flightright/members/test/pictures";
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
        savePicture();
    }
    
    /**
     * Test case for update member picture
     * @throws Exception 
     */
    @Test
    public void when_updatePicture_thenReturnFileName() throws Exception {
        // save file
        String fileName = savePicture();
        String updateFileName = "update_test_file";
        // update 
        String updatedFileName = fileProcessorService.updatePicture(is, new String[]{updateFileName, FILE_EXTENSION}, fileName, PICTURE_PATH);
        
        assertNotNull(updatedFileName);
        assertThat(updatedFileName, startsWith(updateFileName));
        assertFalse(Files.exists(Paths.get(PICTURE_PATH, fileName)));
        assertTrue(Files.exists(Paths.get(PICTURE_PATH, updatedFileName)));
    }
    
    /**
     * Delete Picture Test case
     * @throws Exception 
     */
    @Test
    public void when_deletePicture_thenDoNothing() throws Exception {
        // save file
        String fileName = savePicture();
        
        // delete the picture
        fileProcessorService.deletePicture(new StringBuilder(PICTURE_PATH).append("/")
                                            .append(fileName).toString());
        assertFalse(Files.exists(Paths.get(PICTURE_PATH, fileName)));
    }
    
    private String savePicture() {
        String fileName = fileProcessorService.storePicture(is, new String[]{FILE_NAME, FILE_EXTENSION}, PICTURE_PATH);
        assertNotNull(fileName);
        assertThat(fileName, startsWith(FILE_NAME));
        assertTrue(Files.exists(Paths.get(PICTURE_PATH, fileName)));
        return fileName;
    }
    
    /**
     * Clean up after testing
     */
    @AfterClass
    public static void cleanUp() {
        String file = null;
        try {
            Files.list(Paths.get(PICTURE_PATH)).filter(Files::isRegularFile).forEach(f -> {
                try {
                    Files.deleteIfExists(f);
                } catch (IOException ex) {
                    log.error("Unable to delete the file {}", f, ex);
                }
            });
        } catch (IOException ex) {
            log.error("Unable to delete the file {} after testing", file, ex);
        }
    }
}
