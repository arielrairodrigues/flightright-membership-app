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
            return saveImage(file, fileName, picturePath);
        } catch (IOException e) {
            log.error("Unable to save picture with name {}", fileName, e);
        }
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public String updatePicture(InputStream file, String[] fileDetails, String oldFileName, String picturePath) {
        String fileName = renameFile(fileDetails);
        try {
            Path path = Paths.get(oldFileName);
            Files.deleteIfExists(path);
            return saveImage(file, fileName, picturePath);
        } catch (IOException e) {
            log.error("Unable to update picture with file name {}", oldFileName, e);
        }
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public String renameFile(String[] fileDetails) {
        return new StringBuilder(fileDetails[0]).append("_")
                                                .append(generateFilePrefix(12, true, true))
                                                .append(fileDetails[1]).toString();
    }
    
    /** {@inheritDoc} */
    private String generateFilePrefix(int length, boolean useLetters, boolean useNumbers) {
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }

    /** {@inheritDoc} */
    @Override
    public void deletePicture(String fileName) {
        Path path = Paths.get(fileName);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            log.error("Unable to delete the file {}", path, e);
        }
    }
    
    private String saveImage(InputStream file, String fileName, String pictureFolderpath) throws IOException {
        Files.copy(file, Paths.get(pictureFolderpath, fileName), StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }
    
}
