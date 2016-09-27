package com.photobooth;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.nio.file.Paths;

public class HelloWorld extends Application {

    // private static final String MEDIA_URL = "src/main/resources/car-speakers.swf";
    private static final String MEDIA_URL = "src/main/resources/1_PION.mp4";

    @Override
    public void start(Stage primaryStage) throws Exception {
        //primaryStage.setFullScreen(true);
        //primaryStage.setFullScreenExitHint("");

        Group root = new Group();
        Scene scene = new Scene(root, 540, 210);
        primaryStage.setTitle("Hello world!");

        // create media player
        Media media = new Media(Paths.get(MEDIA_URL).toUri().toString());
        MediaPlayer player = new MediaPlayer(media);
        player.setAutoPlay(true);
        player.play();

        // create media view
        MediaView mediaView = new MediaView(player);
        ((Group)scene.getRoot()).getChildren().add(mediaView);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
