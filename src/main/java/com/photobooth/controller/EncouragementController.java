package com.photobooth.controller;

import com.photobooth.controller.spec.AnimationEndTransition;
import com.photobooth.controller.spec.AnimationInitializable;
import com.photobooth.navigator.Navigator;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * @author mst
 */
public class EncouragementController implements Initializable, AnimationInitializable, AnimationEndTransition {

    private static final String MEDIA_URL = "src/main/resources/animations/Pierwszy_PIONv2_converted.mp4";

    @FXML
    //StackPane mediaPane;
    VBox mediaPane;

    @FXML
    MediaView mediaView;

    List<String> animationPaths = new ArrayList<>();

    Random rand = new Random();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mediaPane.setStyle("-fx-background-color: black");
        System.out.println("EncouragementController");
    }

    @Override
    public void initAnimations(List<String> animationPaths) {
        this.animationPaths = animationPaths;
        MediaPlayer mediaPlayer = initMediaPlayer();
        mediaView.setMediaPlayer(mediaPlayer);
        // set media view to fill all available space
        mediaView.fitWidthProperty().bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
        mediaView.fitHeightProperty().bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
        addFadeInTransition(mediaView);
    }

    private MediaPlayer initMediaPlayer() {
        int mediaIdx = getRandomMediaIdx();
        Media media = new Media(Paths.get(animationPaths.isEmpty() ? MEDIA_URL
                : animationPaths.get(mediaIdx)).toUri().toString());
        MediaPlayer player = new MediaPlayer(media);
        player.setAutoPlay(true);
        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.play();

        return player;
    }

    private int getRandomMediaIdx() {
        return rand.nextInt(animationPaths.size());
    }

    private void addFadeInTransition(MediaView mediaView) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(1500), mediaView);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
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

    @FXML
    public void handleMouseClick(Event event) {
        animateEndTransition();
    }
}
