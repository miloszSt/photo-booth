package com.photobooth.templateEdytor;

import com.photobooth.templateEdytor.elements.ImageElement;
import com.photobooth.templateEdytor.panels.*;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class TemplateMainView {

    private final Border blackSolidBorder = new Border(new BorderStroke(Paint.valueOf("000"), BorderStrokeStyle.SOLID, null, null));

    private final CenterPanel centerPanel;
    private final AddLayerPanel addLayerPanel;
    private final TemplateDetailsPanel templateDetailsPanel;
    private final InfoSelectedPanel infoSelectedPanel;
    private final LayersPanel layersPanel;
    private final OptionsPanel optionsPanel;
    private final TopPanel topPanel;

    private Object template = new Object();
    private List<Node> layers = new ArrayList<>();
    private Node currentSelection;

    public TemplateMainView(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 1920, 1080, Color.GRAY);

        BorderPane border = new BorderPane();

        addLayerPanel = new AddLayerPanel(this);
        templateDetailsPanel = new TemplateDetailsPanel(this);
        layersPanel = new LayersPanel(this);
        optionsPanel = new OptionsPanel(this);
        this.infoSelectedPanel = new InfoSelectedPanel(this);
        centerPanel = new CenterPanel(this);
        topPanel = new TopPanel(this);

        border.setTop(topPanel);

        createLeftPanel(border);
        createRightPanel(border);
        border.setCenter(centerPanel);

        root.getChildren().add(border);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createRightPanel(BorderPane border) {
        VBox rightPanel = new VBox();
        rightPanel.getChildren().addAll(infoSelectedPanel, layersPanel, optionsPanel);
        rightPanel.setSpacing(10);
        border.setRight(rightPanel);
    }

    private void createLeftPanel(BorderPane border) {
        VBox leftPanel = new VBox();

        leftPanel.setSpacing(5);
        leftPanel.setPadding(new Insets(10, 0, 0, 10));

        leftPanel.getChildren().addAll(addLayerPanel, templateDetailsPanel);

        border.setLeft(leftPanel);
    }


    public Border getBlackSolidBorder() {
        return blackSolidBorder;
    }

    public void addNewLayer(Node layer) {
        layers.add(layer);
        centerPanel.addNewElement(layer);
    }
}

