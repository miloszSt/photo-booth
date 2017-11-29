package com.photobooth.controller;

import com.photobooth.App;
import com.photobooth.camera.CameraService;
import com.photobooth.controller.spec.AnimationEndTransition;
import com.photobooth.controller.spec.AnimationInitializable;
import com.photobooth.navigator.Navigator;
import com.photobooth.util.Configuration;
import com.photobooth.util.ConfigurationUtil;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URL;
import java.nio.file.*;
import java.sql.Time;
import java.util.*;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

/**
 * @author mst
 */
public class TakePhotoController implements Initializable, AnimationInitializable, AnimationEndTransition {

    final static Logger logger = Logger.getLogger(TakePhotoController.class);

    private static final String MEDIA_URL = "src/main/resources/animations/odliczanie.mp4";

    @FXML
    MediaView mediaView;

    private List<String> animationPaths = new ArrayList<>();

    private Configuration configuration;

    private CameraService cameraService = App.getCameraService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configuration = ConfigurationUtil.initConfiguration();
        System.out.println("TakePhoto Controller");
    }

    @Override
    public void initAnimations(List<String> animationPaths) {

        this.animationPaths = animationPaths;
        MediaPlayer mediaPlayer = initMediaPlayer();
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setOnReady(() -> {
            double mediaHeight = mediaPlayer.getMedia().getHeight();
            Rotate rotationTransform = new Rotate(90, 0, 0);
            Translate translationTransform = new Translate(0, -mediaHeight);
            mediaView.getTransforms().addAll(rotationTransform, translationTransform);
        });
        mediaPlayer.setOnEndOfMedia(() -> {

            String photoFilePath = cameraService.takeImage();
            mediaPlayer.dispose();
            Navigator.goToPreview(photoFilePath);
        });
        //addFadeInTransition(mediaView);
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

    private boolean isPhotoFileExist(String photoFilePath) {
        File photo = new File(photoFilePath);
        return photo.exists();
    }

    @Override
    public void animateEndTransition() {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(1500), mediaView);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.statusProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Animation.Status.STOPPED) {
                Navigator.nextState();
            }
        });
        fadeOut.play();
    }
}
