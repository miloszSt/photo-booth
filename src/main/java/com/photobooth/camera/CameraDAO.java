package com.photobooth.camera;

import com.photobooth.util.Configuration;
import com.photobooth.util.ConfigurationUtil;
import edsdk.api.CanonCamera;
import edsdk.utils.CanonConstants;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CameraDAO {
    final static Logger logger = Logger.getLogger(CameraDAO.class);

    public static final String COMMAND_LINE_CAMERA_DRIVER = "C:\\Program Files (x86)\\digiCamControl\\CameraControlCmd.exe";
    public static final String PHOTO_SAVE_FILENAME_ARG = " /filename ";
    public static final String CAPTURE_PHOTO_ARG = " /capture";
    public static final String PHOTO_EXTENSION = ".jpg";


    private final CanonCamera canonCamera;

    public CameraDAO(){
        canonCamera = new CanonCamera();
        canonCamera.openSession();

    }
    public String captureImageForUser() {

        logger.info("About to take a photo " + LocalDateTime.now());

        DateTimeFormatter yyyymmdd_hHmm = DateTimeFormatter.ofPattern("YYYYMMdd_HHmmss");
        LocalDateTime now = LocalDateTime.now();
        String fileName = now.format(yyyymmdd_hHmm) + ".jpg";
        File photo = takePhoto("C:\\photoBooth\\currentPhotos\\", fileName);
        return photo.getAbsolutePath();
    }

    public File takePhoto(String directory, String fileName){
//        CanonCamera slr = new CanonCamera();
//        slr.openSession();
        CanonConstants.EdsAFMode autoFocusMode = canonCamera.getAutoFocusMode();
        File photo = canonCamera.shoot(CanonConstants.EdsSaveTo.kEdsSaveTo_Host,10, new File(directory + fileName))[0];

        return photo;
    }

    public void tearDown() {
        canonCamera.closeSession();
    }
}
