package com.photobooth.controller;

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
    VBox appHolder;

    @FXML
    StackPane contentHolder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setContent(Node node) {
        contentHolder.getChildren().setAll(node);
    }
}
