package com.photobooth;

import com.photobooth.camera.CameraService;
import com.photobooth.controller.AppController;

import java.io.IOException;

/**
 * Entry point of application
 *
 * @author MS
 */
public class Main {

    private final static CameraService cameraService = new CameraService();

    public static void main(String[] args) throws IOException, InterruptedException {
       AppController controller = new AppController();
       controller.startApp();

//        cameraService.takeImageForUser("Janusz");
    }
}
