package com.photobooth.camera;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CameraService {
    private CameraDAO cameraDAO;

    public CameraService() {
        this.cameraDAO = new CameraDAO();
    }

    public String takeImage() throws IOException, InterruptedException {
        return cameraDAO.captureImageForUser();
    }
}
