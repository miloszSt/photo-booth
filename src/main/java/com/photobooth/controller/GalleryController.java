package com.photobooth.controller;

import com.photobooth.navigator.Navigator;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

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

    // TODO do zmiany na katalog, w którym będą przechowywane zdjęcia
    //private static final String IMAGES_PATH = "src/main/resources/images";
    private static final String IMAGES_PATH = "images";

    @FXML
    BorderPane viewContainer;

    @FXML
    TilePane galleryContainer;

    @FXML
    HBox buttonsContainer;

    @FXML
    Button btnReject;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File folder = FileUtils.load(IMAGES_PATH);
        List<File> images = Arrays.asList(folder.listFiles());
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        galleryContainer.setPadding(new Insets(15, 15, 15, 15));
        galleryContainer.setPrefHeight(primaryScreenBounds.getHeight() * 0.9);
        galleryContainer.setMaxWidth(primaryScreenBounds.getWidth() * 0.4);
        buttonsContainer.setPrefHeight(primaryScreenBounds.getHeight() * 0.1);
        galleryContainer.setPrefColumns(1);
        galleryContainer.setAlignment(Pos.CENTER);
        createImageViews(images);
    }

    private void createImageViews(List<File> images) {
        images.forEach(imgFile -> {
            ImageView imageView = createImageView(imgFile);
            galleryContainer.getChildren().addAll(imageView);
        });
    }

    private ImageView createImageView(File imgFile) {
        ImageView imageView = null;
        try {
            Image image = new Image(new FileInputStream(imgFile), 600, 0, true, true);
            imageView = new ImageView(image);
            imageView.setFitWidth(600);
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent mouseEvent) {

                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){

                        if(mouseEvent.getClickCount() == 2){
                            try {
                                BorderPane borderPane = new BorderPane();
                                ImageView imageView = new ImageView();
                                Image image = new Image(new FileInputStream(imgFile));
                                imageView.setImage(image);
                                imageView.setStyle("-fx-background-color: BLACK");
                                imageView.setFitHeight(Navigator.getAppContainer().getHeight() - 10);
                                imageView.setPreserveRatio(true);
                                imageView.setSmooth(true);
                                imageView.setCache(true);
                                borderPane.setCenter(imageView);
                                borderPane.setStyle("-fx-background-color: BLACK");
                                Stage newStage = new Stage();
                                newStage.setWidth(Navigator.getAppContainer().getWidth());
                                newStage.setHeight(Navigator.getAppContainer().getHeight());
                                newStage.setTitle(imgFile.getName());
                                Scene scene = new Scene(borderPane, Color.BLACK);
                                newStage.setScene(scene);
                                newStage.show();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return imageView;
    }

    public void handleAccept(ActionEvent actionEvent) {
        // wysyłamy/drukujemy
    }

    public void handleReject(ActionEvent actionEvent) {
        Navigator.start();
    }
}
