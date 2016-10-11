package com.photobooth.camera;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class CameraDAO {

    public static final String COMMAND_LINE_CAMERA_DRIVER = "C:\\Program Files (x86)\\digiCamControl\\CameraControlCmd.exe";
    public static final String PICTURES_MAIN_DIR = "c:\\fotki\\";

    public void captureImageForUser(String user) throws IOException, InterruptedException {
        System.out.println("About to take a photo " + LocalDateTime.now());
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec(COMMAND_LINE_CAMERA_DRIVER + " /folder " + PICTURES_MAIN_DIR + user + " /capture");
        System.out.println("Sent command " + LocalDateTime.now());
        pr.waitFor();

        System.out.println("Photo taken "  + LocalDateTime.now());
    }

    public List<File> getUserFiles(String user){
        File directory = new File(PICTURES_MAIN_DIR + user);
        return Arrays.asList(directory.listFiles());
    }
}
