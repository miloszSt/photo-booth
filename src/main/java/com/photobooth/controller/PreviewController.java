package com.photobooth.controller;

import com.photobooth.controller.spec.PhotoInitializable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Photo preview controller.
 */
public class PreviewController implements Initializable, PhotoInitializable {

    private final static Logger logger = Logger.getLogger(PreviewController.class);

    @FXML
    VBox galleryContainer;

    private String photoFilePath;

    private double galleryHeight;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initGalleryContainer();
    }

    private void initGalleryContainer() {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        galleryHeight = primaryScreenBounds.getHeight() * 0.9;
        galleryContainer.setPrefHeight(galleryHeight);
        galleryContainer.setPadding(new Insets(30, 0, 0, 0));
    }

    @Override
    public void initPhoto(String photoFilePath) {
        this.photoFilePath = photoFilePath;
        this.photoFilePath = "C:\\photoBooth\\currentPhotos\\test.JPG";

        createImageView();
    }

    private void createImageView() {
        ImageView imageView = null;
        logger.info(photoFilePath);
        File imgFile = new File(photoFilePath);
        try {
            Image image = new Image(new FileInputStream(imgFile), 0, galleryHeight, true, true);
            imageView = new ImageView(image);
            imageView.setFitHeight(galleryHeight);
        } catch (FileNotFoundException e) {
            logger.error(e);
        }

        galleryContainer.getChildren().add(imageView);
    }
}
