package com.photobooth.navigator;

import com.photobooth.controller.AppController;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

/**
 * Obsługuje przechodzenie pomiędzy poszczególnymi widokami.
 *
 * @mst
 */
public class Navigator {

    public static final String APP_VIEW = "/view/app.fxml";
    public static final String ENCOURAGMENT_VIEW = "/view/encouragment.fxml";
    public static final String TAKE_PHOTO_VIEW = "/view/app.fxml";

    private static AppController appController;

    public static void setAppController(AppController appController) {
        Navigator.appController = appController;
    }

    public static void loadContentView(String fxmlViewPath) {
        System.out.println("Load view from path: " + fxmlViewPath);
        try {
            appController.setContent(FXMLLoader.load(Navigator.class.getResource(fxmlViewPath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
