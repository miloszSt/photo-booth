package com.photobooth.camera;

import com.photobooth.util.Configuration;
import com.photobooth.util.ConfigurationUtil;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class CameraDAO {

    public static final String COMMAND_LINE_CAMERA_DRIVER = "C:\\Program Files (x86)\\digiCamControl\\CameraControlCmd.exe";
    public void captureImageForUser() throws IOException, InterruptedException {
        Configuration configuration = ConfigurationUtil.initConfiguration();
        System.out.println("About to take a photo " + LocalDateTime.now());
        Runtime rt = Runtime.getRuntime();

        DateTimeFormatter yyyymmdd_hHmm = DateTimeFormatter.ofPattern("YYYYMMDD_HHmm");
        LocalDateTime now = LocalDateTime.now();
        String fileName = now.format(yyyymmdd_hHmm);
//        Process pr = rt.exec(COMMAND_LINE_CAMERA_DRIVER + " /folder " + configuration.getCurrentPhotosPath() +  "/filename "+fileName+".jpg /capture");
        Process pr = rt.exec(COMMAND_LINE_CAMERA_DRIVER + " /folder C:\\photoBooth\\currentPhotos /filename "+fileName+".jpg  /capture");
        System.out.println("Sent command " + LocalDateTime.now());
        pr.waitFor();
        System.out.println("Photo taken "  + LocalDateTime.now());
    }
}
