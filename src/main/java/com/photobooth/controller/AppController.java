package com.photobooth.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author mst
 */
public class AppController implements Initializable {

    @FXML
    StackPane contentHolder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setContent(Node node)
    {
        contentHolder.getChildren().setAll(node);
    }

    @FXML
    public void handleStateEditorAction(ActionEvent actionEvent) {
        // TODO implement: set StateEditorView
    }

    @FXML
    public void handleTemplateEditorAction(ActionEvent actionEvent) {
        // TODO implement: set TemplateEditorView
    }
}
