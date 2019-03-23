/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.service;

import java.io.InputStream;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
public interface FileProcessorService {
    
    /**
     * Creates the pictures folder if it does not exist
     * @param picturesPath 
     */
    void createPicturesFolder(String picturesPath);
    
    /**
     * Stores a member picture 
     * @param file
     * @param picturePath 
     */
    void storePicture(InputStream file, String picturePath);
    
    /**
     * Updates a member picture
     * @param file
     * @param oldFileName
     * @param picturePath 
     */
    void updatePicture(InputStream file, String oldFileName, String picturePath);
}
