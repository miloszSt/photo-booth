package com.photobooth.controller;

import com.photobooth.camera.CameraService;
import com.photobooth.navigator.Navigator;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

/**
 * @author mst
 */
public class TakePhotoController implements Initializable {

    private static final String MEDIA_URL = "src/main/resources/odliczanie.mp4";

    @FXML
    MediaView mediaView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MediaPlayer mediaPlayer = initMediaPlayer();
        mediaView.setMediaPlayer(mediaPlayer);
        takePhoto();
    }

    private MediaPlayer initMediaPlayer() {
        Media media = new Media(Paths.get(MEDIA_URL).toUri().toString());
        MediaPlayer player = new MediaPlayer(media);
        player.setAutoPlay(true);
        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.play();

        return player;
    }

    private void takePhoto() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Jada swinie jadą");
                try {
                    new CameraService().takeImageForUser("Janusz");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        // TODO should be synchronized with thread
        Navigator.goTo(Navigator.GALLERY_VIEW);
    }
}
