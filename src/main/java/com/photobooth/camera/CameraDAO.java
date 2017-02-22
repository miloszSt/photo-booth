package com.photobooth.camera;

import com.photobooth.util.Configuration;
import com.photobooth.util.ConfigurationUtil;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CameraDAO {
    final static Logger logger = Logger.getLogger(CameraDAO.class);

    public static final String COMMAND_LINE_CAMERA_DRIVER = "C:\\Program Files (x86)\\digiCamControl\\CameraControlCmd.exe";
    public static final String PHOTO_SAVE_FOLDER_ARG = " /folder ";
    public static final String PHOTO_SAVE_FILENAME_ARG = " /filename ";
    public static final String CAPTURE_PHOTO_ARG = " /capture";
    public static final String PHOTO_EXTENSION = ".jpg";

    public String captureImageForUser() throws IOException, InterruptedException {
        Configuration configuration = ConfigurationUtil.initConfiguration(); // TODO mst use configuration not hardcoded paths
        logger.info("About to take a photo " + LocalDateTime.now());
        Runtime rt = Runtime.getRuntime();

        DateTimeFormatter yyyymmdd_hHmm = DateTimeFormatter.ofPattern("YYYYMMDD_HHmmSS");
        LocalDateTime now = LocalDateTime.now();
        String fileName = now.format(yyyymmdd_hHmm);
        logger.info("Sent command " + LocalDateTime.now());
        Process pr = rt.exec(createCommandForTakingPhoto("C:\\photoBooth\\currentPhotos", fileName));
        pr.waitFor();
        logger.info("Photo taken "  + LocalDateTime.now() + " as " + fileName);

        return getPhotoFilePath(fileName);
    }

    private String createCommandForTakingPhoto(String currentPhotosFolderPath, String fileName) {
        return COMMAND_LINE_CAMERA_DRIVER
                + PHOTO_SAVE_FOLDER_ARG + currentPhotosFolderPath
                + PHOTO_SAVE_FILENAME_ARG + fileName + PHOTO_EXTENSION
                + CAPTURE_PHOTO_ARG;
    }

    // TODO mst add path from configuration
    private String getPhotoFilePath(String fileName) {
        return "C:\\photoBooth\\currentPhotos\\" + fileName + PHOTO_EXTENSION;
    }
}
