package com.photobooth.navigator;

import com.photobooth.controller.AppController;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Obsługuje przechodzenie pomiędzy poszczególnymi widokami.
 *
 * @mst
 */
public class Navigator {

    public static final String APP_VIEW = "/view/app.fxml";
    public static final String ENCOURAGMENT_VIEW = "/view/encouragment.fxml";
    public static final String TAKE_PHOTO_VIEW = "/view/takephoto.fxml";
    public static final String GALLERY_VIEW = "/view/gallery.fxml";

    private static AppController appController;

    private static Stage appContainer;

    public static void setAppController(AppController appController) {
        Navigator.appController = appController;
    }

    public static void goTo(String fxmlViewPath) {
        try {
            appController.setContent(FXMLLoader.load(Navigator.class.getResource(fxmlViewPath)));
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static Stage getAppContainer() {
        return appContainer;
    }

    public static void setAppContainer(Stage stage) {
        appContainer = stage;
    }
}
