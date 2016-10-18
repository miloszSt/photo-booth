package com.photobooth.templateEdytor.panels;

import com.photobooth.templateEdytor.Layer;
import com.photobooth.templateEdytor.TemplateMainView;
import com.photobooth.templateEdytor.elements.TemplateElementInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LayersPanel extends VBox{

    private final TemplateMainView templateMainView;

    private final ListView<Layer> layersList;

    private final Button copyLayer;
    private final Button removeLayer;
    public LayersPanel(TemplateMainView templateMainView) {
        super();
        this.templateMainView = templateMainView;

        Label infoLabel = new Label("LAYERS");

        layersList = new ListView<Layer>();



        copyLayer = new Button("Copy layer");
        copyLayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                TemplateElementInterface currentSelection = (TemplateElementInterface) templateMainView.getCurrentSelection();
                Node templateElementInterface = (Node) currentSelection.serialize().toElement();


                templateMainView.addNewLayer(templateElementInterface);

            }
        });


        removeLayer = new Button("Remove layer");

        removeLayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Node currentSelection = templateMainView.getCurrentSelection();
                templateMainView.removeLayer(currentSelection);
                layersList.getItems().remove(layersList.getSelectionModel().getSelectedItem());
            }
        });
        setSpacing(5);
        setBorder(templateMainView.getBlackSolidBorder());

        getChildren().addAll(infoLabel, layersList, new HBox(copyLayer, removeLayer));
    }


    public ListView<Layer> getLayersList() {
        return layersList;
    }
}
