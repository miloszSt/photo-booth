package com.photobooth.controller;

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
        System.out.println("Init AppController");
        //appHolder.setStyle("-fx-border-color: red;");
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
