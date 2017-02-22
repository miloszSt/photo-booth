package com.photobooth.controller;

import com.photobooth.navigator.Navigator;
import com.photobooth.util.ConfigurationUtil;
import com.photobooth.util.FileUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author mst
 */
public class GalleryController implements Initializable {
    final static Logger logger = Logger.getLogger(GalleryController.class);
    // TODO do zmiany na katalog, w którym będą przechowywane zdjęcia
    private final static int COLS = 1;
    private final static double IMG_SPACING = 15.0;

    @FXML
    BorderPane viewContainer;

    @FXML
    VBox galleryContainer;

    @FXML
    HBox buttonsContainer;

    @FXML
    Button btnReject;

    private double galleryHeight;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initGalleryContainer();

        File folder = new File(ConfigurationUtil.initConfiguration().getCurrentPhotosPath());
        List<File> images = Arrays.asList(folder.listFiles());
        createImageViews(images);
    }

    private void initGalleryContainer() {
        galleryHeight = Screen.getPrimary().getVisualBounds().getHeight() * 0.9;
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        galleryContainer.setPrefHeight(galleryHeight);
        galleryContainer.setSpacing(IMG_SPACING);
        galleryContainer.setPadding(new Insets(30, 0, 0, 0));
        buttonsContainer.setPrefHeight(primaryScreenBounds.getHeight() * 0.1);
    }

    private void createImageViews(List<File> images) {
        double imageHeight = calculateImgHeight(images.size() / COLS);
        images.forEach(imgFile -> {
            ImageView imageView = createImageView(imgFile, imageHeight);
            galleryContainer.getChildren().add(imageView);
        });
    }

    private double calculateImgHeight(int size) {
        double spacing = 2 * 15.0 /* container spacing */
                + ((size - 1) * IMG_SPACING) / COLS /* spacing between images */;
        return (galleryHeight - spacing)/size;
    }

    private ImageView createImageView(File imgFile, double imageHeight) {
        ImageView imageView = null;
        try {
            Image image = new Image(new FileInputStream(imgFile), 0, imageHeight, true, true);
            imageView = new ImageView(image);
            imageView.setFitHeight(imageHeight);
        } catch (FileNotFoundException e) {
            logger.error(e);
        }

        return imageView;
    }

    public void handleAccept(ActionEvent actionEvent) {
        Navigator.nextState();
    }

    public void handleReject(ActionEvent actionEvent) {
        Navigator.start();
    }
}
