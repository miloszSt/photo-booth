package com.photobooth.templateEdytor;

import com.photobooth.templateEdytor.elements.ImageElement;
import com.photobooth.templateEdytor.elements.TemplateElementInterface;
import com.photobooth.templateEdytor.panels.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TemplateMainView implements Serializable{

    private final Border blackSolidBorder = new Border(new BorderStroke(Paint.valueOf("000"), BorderStrokeStyle.SOLID, null, null));

    private CenterPanel centerPanel;
    private AddLayerPanel addLayerPanel;
    private TemplateDetailsPanel templateDetailsPanel;
    private InfoSelectedPanel infoSelectedPanel;
    private LayersPanel layersPanel;
    private OptionsPanel optionsPanel;
    private TopPanel topPanel;
    private Stage primaryStage;
    private Scene scene;

    private String templateName;
    private List<Node> layers;
    private List<Layer> layersAString;
    private Node currentSelection;

    private final EventHandler<MouseEvent> deselectAllOnClick = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            deselect();
        }
    };

    public TemplateMainView(Stage primaryStage) {
        topPanel = new TopPanel(this);
        this.templateName = "Nowy template";
        init(primaryStage);
    }

    private void init(Stage primaryStage) {
        this.primaryStage = primaryStage;
        Group root = new Group();

        scene = new Scene(root, 1920, 1080, Color.GRAY);

        BorderPane border = new BorderPane();

        addLayerPanel = new AddLayerPanel(this);
        templateDetailsPanel = new TemplateDetailsPanel(this);
        layersPanel = new LayersPanel(this);
        optionsPanel = new OptionsPanel(this);
        this.infoSelectedPanel = new InfoSelectedPanel(this);
        centerPanel = new CenterPanel(this);


        centerPanel.getBackgroundPanel().setOnMouseClicked(deselectAllOnClick);

        border.setTop(topPanel);

        createLeftPanel(border);
        createRightPanel(border);
        border.setCenter(centerPanel);

        root.getChildren().add(border);

        primaryStage.setScene(scene);
        primaryStage.show();
        layers = new ArrayList<>();
        layersAString = new ArrayList<>();
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
        layersAString.add(new Layer((TemplateElementInterface) layer));
        layersPanel.getLayersList().setItems(FXCollections.observableArrayList(layersAString));
        centerPanel.addNewElement(layer);
        setOnClickListenerForSelection(layer);
    }

    private void setOnClickListenerForSelection(Node node) {
        node.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                deselect();
                layersPanel.getLayersList().getSelectionModel().select(new Layer((TemplateElementInterface) node));
                ((TemplateElementInterface) node).select();
                infoSelectedPanel.setTemplateElementInterface((StackPane) node);
            }
        });

        layersPanel.getLayersList().getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Layer>() {
            @Override
            public void changed(ObservableValue<? extends Layer> observable, Layer oldValue, Layer newValue) {
                deselect();
                for(Node node1 : layers){
                    TemplateElementInterface node11 = (TemplateElementInterface) node1;
                    if(newValue != null) {
                        if (node11.getElementId().equals(newValue.getElementId())) {
                            node11.select();
                            infoSelectedPanel.setTemplateElementInterface((StackPane) node1);
                        }
                    }
                }
            }
        });

    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;

    }

    public CenterPanel getCenterPanel() {
        return centerPanel;
    }

    private void deselect() {
        for (Node node : layers) {
            ((TemplateElementInterface) node).deselect();
        }
    }

    public void reset(){
        init(primaryStage);
    }

    public Scene getScene() {
        return scene;
    }
}

