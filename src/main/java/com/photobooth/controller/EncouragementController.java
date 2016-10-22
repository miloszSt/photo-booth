package com.photobooth.controller;

import com.photobooth.controller.spec.AnimationInitializable;
import com.photobooth.navigator.Navigator;
import com.photobooth.util.FileUtils;
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
public class EncouragementController implements Initializable, AnimationInitializable {

    private static final String MEDIA_URL = "src/main/resources/animations/Pierwszy_PIONv2_converted.mp4";

    @FXML
    StackPane mediaPane;

    @FXML
    MediaView mediaView;

    String animationPath = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MediaPlayer mediaPlayer = initMediaPlayer();
        mediaView.setMediaPlayer(mediaPlayer);
    }

    private MediaPlayer initMediaPlayer() {
        Media media = new Media(Paths.get(animationPath.isEmpty() ? MEDIA_URL : animationPath).toUri().toString());
        MediaPlayer player = new MediaPlayer(media);
        player.setAutoPlay(true);
        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.play();

        return player;
    }

    @FXML
    public void handleMouseClick(Event event) {
        Navigator.goTo(Navigator.TAKE_PHOTO_VIEW);
    }

    @Override
    public void initAnimation(String animationPath) {
        this.animationPath = FileUtils.RESOURCES_FOLDER + animationPath;
    }
}
