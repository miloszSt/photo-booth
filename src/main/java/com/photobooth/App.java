package com.photobooth;

import com.photobooth.controller.AppController;
import com.photobooth.navigator.Navigator;
import com.photobooth.util.Configuration;
import com.photobooth.util.ConfigurationUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Entry point of application
 *
 * @author mst
 */
public class App extends Application {

    private static final String FULL_SCREEN_HINT = "";
    private Configuration configuration;

    @Override
    public void start(Stage primaryStage) throws Exception {
        configuration = ConfigurationUtil.initConfiguration();
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint(FULL_SCREEN_HINT);
        Navigator.setAppContainer(primaryStage);
        primaryStage.setScene(loadAppView());
        primaryStage.show();
    }

    private Scene loadAppView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Navigator.APP_VIEW));
        Pane appPane = loader.load();
        AppController appController = loader.getController();
        Navigator.setAppController(appController);

        if (hasCustomConfiguration()) {
            Navigator.nextState();
        } else {
            showNoCustomConfigurationAlert();
        }

        return new Scene(appPane);
    }

    private void showNoCustomConfigurationAlert() {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setHeaderText(null);
        String contentProperty;
        if (configuration.isStateFlowDefined())
            contentProperty = "ConfirmationAlert.ContentText";
        else
            contentProperty = "InitConfirmationAlert.ContentText";

        confirmationDialog.setContentText(ResourceBundle.getBundle("locale.locale")
                .getString(contentProperty));

        Optional<ButtonType> result = confirmationDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Navigator.goTo(Navigator.STATE_EDITOR_VIEW);
        } else { // Cancel
            Navigator.setCustomStates(configuration.loadLastStatFlow());
            Navigator.nextState();
        }
    }

    private boolean hasCustomConfiguration() {
        return Navigator.hasCustomStatesConfiguration();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        launch(args);
    }
}
