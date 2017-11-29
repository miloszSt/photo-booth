package com.photobooth.controller;

import com.photobooth.controller.spec.AnimationInitializable;
import com.photobooth.navigator.Navigator;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * @author mst
 */
public class PlayOnceController implements Initializable, AnimationInitializable {

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
        this.animationPaths = animationPaths;
        MediaPlayer mediaPlayer = initMediaPlayer();
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setOnReady(() -> {
            double mediaHeight = mediaPlayer.getMedia().getHeight();
            Rotate rotationTransform = new Rotate(90, 0, 0);
            Translate translationTransform = new Translate(0, -mediaHeight);
            mediaView.getTransforms().addAll(rotationTransform, translationTransform);
        });
    }

    private MediaPlayer initMediaPlayer() {
        int mediaIdx = getRandomMediaIdx();
        Media media = new Media(Paths.get(animationPaths.isEmpty() ? MEDIA_URL
                : animationPaths.get(mediaIdx)).toUri().toString());
        MediaPlayer player = new MediaPlayer(media);
        player.setAutoPlay(true);
        player.setCycleCount(1);
        player.play();

        player.setOnEndOfMedia(() -> {
            Navigator.nextState();
            player.dispose();
        });

        return player;
    }

    private int getRandomMediaIdx() {
        Random rand = new Random();
        return rand.nextInt(animationPaths.size());
    }

}
