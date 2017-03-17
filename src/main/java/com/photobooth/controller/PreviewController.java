package com.photobooth.controller;

import com.photobooth.controller.spec.PhotoInitializable;
import com.photobooth.navigator.Navigator;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Screen;
import javafx.util.Duration;
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
        logger.info("Preview state");
        initGalleryContainer();
    }

    private void initGalleryContainer() {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        logger.info("Display width: " + primaryScreenBounds.getWidth());
        logger.info("Display heigth: " + primaryScreenBounds.getHeight());
        galleryHeight = primaryScreenBounds.getHeight() * 0.9;
        galleryContainer.setPrefHeight(galleryHeight);
        galleryContainer.setPadding(new Insets(30, 0, 0, 0));
    }

    @Override
    public void initPhoto(String photoFilePath) {
        this.photoFilePath = photoFilePath;
        createImageView();
    }

    private void createImageView() {
        ImageView imageView = null;
        logger.info(photoFilePath);
        File imgFile = new File(photoFilePath);
        try {
            Image image = new Image(new FileInputStream(imgFile));
            imageView = new ImageView(image);
            Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
            imageView.setFitWidth(primaryScreenBounds.getWidth());
            imageView.setFitHeight(primaryScreenBounds.getHeight());
            imageView.setPreserveRatio(true);
        } catch (FileNotFoundException e) {
            logger.error(e);
        }

        galleryContainer.getChildren().add(imageView);

        // go to next state after 2500 miliseconds
        PauseTransition delay = new PauseTransition(Duration.millis(2500));
        delay.setOnFinished(event -> Navigator.nextState());
        delay.play();
    }
}
