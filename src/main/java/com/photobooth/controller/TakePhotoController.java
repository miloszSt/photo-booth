package com.photobooth.controller;

import com.photobooth.camera.CameraService;
import com.photobooth.controller.spec.AnimationInitializable;
import com.photobooth.navigator.Navigator;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author mst
 */
public class TakePhotoController implements Initializable, AnimationInitializable {

    private static final String MEDIA_URL = "src/main/resources/animations/odliczanie.mp4";

    @FXML
    MediaView mediaView;

    private List<String> animationPaths = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("TakePhoto Controller");
    }

    @Override
    public void initAnimations(List<String> animationPaths) {
        takePhoto();
        this.animationPaths = animationPaths;
        MediaPlayer mediaPlayer = initMediaPlayer();
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setOnReady(() -> {
            double mediaHeight = mediaPlayer.getMedia().getHeight();
            Rotate rotationTransform = new Rotate(90, 0, 0);
            Translate translationTransform = new Translate(0, -mediaHeight);
            mediaView.getTransforms().addAll(rotationTransform, translationTransform);
        });
        addFadeInTransition(mediaView);
    }

    private MediaPlayer initMediaPlayer() {
        int mediaIdx = getRandomMediaIdx();
        Media media = new Media(Paths.get(animationPaths.isEmpty() ? MEDIA_URL : animationPaths.get(mediaIdx)).toUri().toString());
        MediaPlayer player = new MediaPlayer(media);
        player.setAutoPlay(true);
        player.setCycleCount(1);
        player.play();

        return player;
    }

    private int getRandomMediaIdx() {
        Random rand = new Random();
        return rand.nextInt(animationPaths.size());
    }

    private void addFadeInTransition(MediaView mediaView) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(1500), mediaView);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    private void takePhoto() {
        Task<Void> takePhotoTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                System.out.println("Wywolany " + new Date());
                Thread.sleep(2500);
                System.out.println("pospane" + new Date());

                new CameraService().takeImage();
                System.out.println("zrobione" + new Date());
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

}
