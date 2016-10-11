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

    public void takeImageForUser(String user) throws IOException, InterruptedException {
        cameraDAO.captureImageForUser(user);
    }

    public List<BufferedImage> getImagesForUser(String user) throws IOException {
        ArrayList<BufferedImage> images = new ArrayList<>();
        List<File> userFiles = cameraDAO.getUserFiles(user);

        for (File file : userFiles) {
            images.add(ImageIO.read(file));
        }

        return images;
    }


}
