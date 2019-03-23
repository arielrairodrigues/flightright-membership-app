/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.service.impl;

import com.flightright.service.FileProcessorService;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@Service
@Slf4j
public class FileProcessorServiceImpl implements FileProcessorService{

    /** {@inheritDoc} */
    @Override
    public void createPicturesFolder(String picturesPath) {
        Path folder = Paths.get(picturesPath);
        
        if (!Files.exists(folder)) {
            try {
                Files.createDirectories(folder);
            } catch (IOException ex) {
                log.error("Unable to create the pictures folder: {}", folder, ex);
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public String storePicture(InputStream file, String[] fileDetails, String picturePath) {
        String fileName = renameFile(fileDetails);
        try {
            // create the pictures folder if it does not exist
            createPicturesFolder(picturePath);
            Files.copy(file, Paths.get(picturePath, fileName), StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            log.error("Unable to save picture with name {}", fileName, e);
        }
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public boolean updatePicture(InputStream file, String oldFileName, String picturePath) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String renameFile(String[] fileDetails) {
        return new StringBuilder(fileDetails[0]).append("_")
                                                .append(generateFilePrefix(12, true, true))
                                                .append(fileDetails[1]).toString();
    }
    
    private String generateFilePrefix(int length, boolean useLetters, boolean useNumbers) {
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }

    @Override
    public boolean deletePicture(String fileName) {
        Path path = Paths.get(fileName);
        try {
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            log.error("Unable to delete the file {}", path, e);
        }
        return false;
    }
    
}
