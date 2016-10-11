package com.photobooth;

import com.photobooth.controller.AppController;
import com.photobooth.navigator.Navigator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Entry point of application
 *
 * @author mst
 */
public class App extends Application {

    private static final String FULL_SCREEN_HINT = "";

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint(FULL_SCREEN_HINT);

        primaryStage.setScene(loadAppView());

        primaryStage.show();
    }

    private Scene loadAppView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Navigator.APP_VIEW));
        // TODO pliki fxml w resource/view pozniej mozna zmienic
        Pane appPane = loader.load();
        AppController appController = loader.getController();

        Navigator.setAppController(appController);
        Navigator.goTo(Navigator.ENCOURAGMENT_VIEW);
        return new Scene(appPane);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        launch(args);
    }
}
