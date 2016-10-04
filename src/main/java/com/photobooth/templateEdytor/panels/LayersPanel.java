package com.photobooth.templateEdytor.panels;

import com.photobooth.templateEdytor.TemplateMainView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LayersPanel extends VBox{

    private final TemplateMainView templateMainView;

    private final ListView<String> layersList;
    public LayersPanel(TemplateMainView templateMainView) {
        super();
        this.templateMainView = templateMainView;

        Label infoLabel = new Label("LAYERS");

        layersList = new ListView<String>();
        Button copyLayer = new Button("Copy layer");
        Button removeLayer = new Button("Remove layer");

        setSpacing(5);
        setBorder(templateMainView.getBlackSolidBorder());

        getChildren().addAll(infoLabel, layersList, new HBox(copyLayer, removeLayer));
    }


    public ListView<String> getLayersList() {
        return layersList;
    }
}
