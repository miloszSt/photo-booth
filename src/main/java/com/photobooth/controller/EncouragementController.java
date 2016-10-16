package com.photobooth.controller;

import com.photobooth.navigator.Navigator;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

/**
 * @author mst
 */
public class EncouragementController implements Initializable {

    private static final String MEDIA_URL = "src/main/resources/Pierwszy_PIONv2_converted.mp4";

    @FXML
    StackPane mediaPane;

    @FXML
    MediaView mediaView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //mediaPane.setStyle("-fx-border-color: black;");
        MediaPlayer mediaPlayer = initMediaPlayer();
        mediaView.setMediaPlayer(mediaPlayer);
    }



    private MediaPlayer initMediaPlayer() {
        Media media = new Media(Paths.get(MEDIA_URL).toUri().toString());
        MediaPlayer player = new MediaPlayer(media);
        player.setAutoPlay(true);
        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.play();

        return player;
    }

    @FXML
    public void handleMouseClick(Event event) {
        //Navigator.goTo(Navigator.TAKE_PHOTO_VIEW);
        Navigator.goTo(Navigator.GALLERY_VIEW);
    }
}
