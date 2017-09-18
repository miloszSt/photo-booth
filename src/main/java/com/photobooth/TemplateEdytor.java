package com.photobooth;

import com.photobooth.camera.CameraService;
import com.photobooth.templateEdytor.TemplateMainView;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;

public class TemplateEdytor extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
//        TemplateMainView templateMainView = new TemplateMainView(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
