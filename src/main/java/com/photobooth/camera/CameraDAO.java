package com.photobooth.camera;

import com.photobooth.util.Configuration;
import com.photobooth.util.ConfigurationUtil;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class CameraDAO {
    final static Logger logger = Logger.getLogger(CameraDAO.class);

    public static final String COMMAND_LINE_CAMERA_DRIVER = "C:\\Program Files (x86)\\digiCamControl\\CameraControlCmd.exe";
    public void captureImageForUser() throws IOException, InterruptedException {
        Configuration configuration = ConfigurationUtil.initConfiguration();
        logger.info("About to take a photo " + LocalDateTime.now());
        Runtime rt = Runtime.getRuntime();

        DateTimeFormatter yyyymmdd_hHmm = DateTimeFormatter.ofPattern("YYYYMMDD_HHmmSS");
        LocalDateTime now = LocalDateTime.now();
        String fileName = now.format(yyyymmdd_hHmm);
//        Process pr = rt.exec(COMMAND_LINE_CAMERA_DRIVER + " /folder " + configuration.getCurrentPhotosPath() +  "/filename "+fileName+".jpg /capture");
        Process pr = rt.exec(COMMAND_LINE_CAMERA_DRIVER + " /filename C:\\photoBooth\\currentPhotos\\"+fileName+".jpg  /capture");
        logger.info("Sent command " + LocalDateTime.now());
        pr.waitFor();
        logger.info("Photo taken "  + LocalDateTime.now() + " as " + fileName);
    }
}
