package com.photobooth.controller;

import com.photobooth.camera.CameraService;
import com.photobooth.controller.spec.AnimationInitializable;
import com.photobooth.navigator.Navigator;
import com.photobooth.util.FileUtils;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

/**
 * @author mst
 */
public class TakePhotoController implements Initializable, AnimationInitializable {

    private static final String MEDIA_URL = "src/main/resources/animations/odliczanie.mp4";

    @FXML
    MediaView mediaView;

    private String animationPath = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("TakePhoto Controller");
        MediaPlayer mediaPlayer = initMediaPlayer();
        mediaView.setMediaPlayer(mediaPlayer);
        // set media view to fill all available space
        mediaView.fitWidthProperty().bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
        mediaView.fitHeightProperty().bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
        takePhoto();
    }

    private MediaPlayer initMediaPlayer() {
        Media media = new Media(Paths.get(animationPath.isEmpty() ? MEDIA_URL : animationPath).toUri().toString());
        MediaPlayer player = new MediaPlayer(media);
        player.setAutoPlay(true);
        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.play();

        return player;
    }

    private void takePhoto() {
        Task<Void> takePhotoTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(4000);
                new CameraService().takeImage();

                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                System.out.println("Success from task!");
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Navigator.nextState();
                    }
                });
            }
        };
        Thread thread = new Thread(takePhotoTask);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void initAnimation(String animationPath) {
        this.animationPath = FileUtils.RESOURCES_FOLDER + animationPath;
    }
}
