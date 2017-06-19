package com.photobooth;

import com.photobooth.config.Config;
import com.photobooth.config.ConfigReader;
import com.photobooth.controller.AppController;
import com.photobooth.navigator.Navigator;
import com.photobooth.util.StateFlowConfiguration;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Entry point of application
 *
 * @author mst
 */
public class App extends Application {

    private final static Logger logger = Logger.getLogger(App.class);

    private static final String FULL_SCREEN_HINT = "";

    @Override
    public void start(Stage primaryStage) throws Exception {
        logger.info("Uruchomiono aplikację");

        if (ConfigReader.hasStateFlowConfigurationDefined()) {
            logger.info("Czytam konfiguracje przepływu stanów");
            Task<StateFlowConfiguration> getStateFlowConfigTask = new Task<StateFlowConfiguration>() {
                @Override
                protected StateFlowConfiguration call() throws Exception {
                    return ConfigReader.readStateFlowCOnfiguration();
                }
            };
            getStateFlowConfigTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    StateFlowConfiguration stateFlowConfiguration = getStateFlowConfigTask.getValue();
                    Config.getInstance().setStateFlowConfiguration(stateFlowConfiguration);
                    setUp(primaryStage);
                }
            });
            Thread thread = new Thread(getStateFlowConfigTask);
            thread.setDaemon(true);
            thread.start();
        } else {
            setUp(primaryStage);
        }
    }

    private void setUp(Stage primaryStage) {
        // standardowy przepływ programu
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint(FULL_SCREEN_HINT);
        Navigator.setAppContainer(primaryStage);

        try {
            primaryStage.setScene(loadAppView());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        primaryStage.show();
    }

    private Scene loadAppView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Navigator.APP_VIEW));
        Pane appPane = loader.load();
        AppController appController = loader.getController();
        Navigator.setAppController(appController);

        if (Config.getInstance().getStateFlowConfiguration() != null) {
            showStateFlowConfigurationExists();
        } else {
            Navigator.goToStateEditor(false);
        }

        return new Scene(appPane);
    }

    private void showStateFlowConfigurationExists() {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setHeaderText(ResourceBundle.getBundle("locale.locale")
                .getString("ConfirmationLoadDefinedStatFlowConfigurationAlert.HeaderText"));
        confirmationDialog.setContentText(ResourceBundle.getBundle("locale.locale")
                .getString("ConfirmationLoadDefinedStatFlowConfigurationAlert.ContentText"));

        Optional<ButtonType> result = confirmationDialog.showAndWait();
        boolean shouldUseDefinedStateFlowConfig = result.isPresent() && result.get() == ButtonType.OK;
        Navigator.goToStateEditor(shouldUseDefinedStateFlowConfig);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        launch(args);
    }
}
