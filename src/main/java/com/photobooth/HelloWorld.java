package com.photobooth;

import com.photobooth.camera.CameraService;
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

public class HelloWorld extends Application {

    // private static final String MEDIA_URL = "src/main/resources/car-speakers.swf";
    private static final String MEDIA_URL = "src/main/resources/Pierwszy_PIONv2_converted.mp4";
    private static final String LICZNIK = "src/main/resources/odliczanie.mp4";
//    private static final String MEDIA_URL = "src/main/resources/The Simpsons Movie - Trailer.mp4";
    @Override
    public void start(Stage primaryStage) throws Exception {
        //primaryStage.setFullScreen(true);
        //primaryStage.setFullScreenExitHint("");
       System.out.print( com.sun.javafx.runtime.VersionInfo.getRuntimeVersion());


        Group root = new Group();
        primaryStage.setTitle("Hello world!");


        Scene costam = new Scene(root, 900, 900);
        Button button = new Button("costam");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Group root2 = new Group();
                Scene scene = new Scene(root2, 500, 500);

                // create media player
                Media media = new Media(Paths.get(MEDIA_URL).toUri().toString());
                MediaPlayer player = new MediaPlayer(media);
                player.setAutoPlay(true);
                player.setCycleCount(MediaPlayer.INDEFINITE);

                player.play();



                // create media view
                MediaView mediaView = new MediaView(player);
                ((Group)scene.getRoot()).getChildren().add(mediaView);


                primaryStage.setScene(scene);

                mediaView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("Klikłeś gnido, będzie fotka");

                        // create media player
                        Media media = new Media(Paths.get(LICZNIK).toUri().toString());
                        MediaPlayer player = new MediaPlayer(media);
                        player.setAutoPlay(true);
                        player.setCycleCount(MediaPlayer.INDEFINITE);
                        player.play();


                        mediaView.setMediaPlayer(player);

                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(3500l);
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
                    }
                });
            }
        });
        root.getChildren().add(button);

        primaryStage.setScene(costam);
        primaryStage.show();



    }

    public static void main(String[] args) {
        launch(args);
    }
}
