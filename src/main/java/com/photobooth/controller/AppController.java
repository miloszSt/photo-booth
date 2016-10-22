package com.photobooth.controller;

import com.photobooth.navigator.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author mst
 */
public class AppController implements Initializable {

    @FXML
    StackPane contentHolder;

    @FXML
    VBox appHolder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setContent(Node node)
    {
        contentHolder.getChildren().setAll(node);
    }

    @FXML
    public void handleStateEditorAction(ActionEvent actionEvent) {
        Navigator.goTo(Navigator.STATE_EDITOR_VIEW);
    }

    @FXML
    public void handleTemplateEditorAction(ActionEvent actionEvent) {
        Navigator.goTo(Navigator.TEMPLATE_EDITOR_VIEW);
    }
}
