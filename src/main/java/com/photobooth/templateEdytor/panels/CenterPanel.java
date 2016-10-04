package com.photobooth.templateEdytor.panels;

import com.photobooth.templateEdytor.TemplateMainView;
import com.photobooth.templateEdytor.elements.DragResizeMod;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class CenterPanel extends Pane {

    private final TemplateMainView templateMainView;
    private final Rectangle backgroundPanel = new Rectangle(1200, 900, Color.WHITE);
    public CenterPanel(TemplateMainView templateMainView) {
        super();
        this.templateMainView = templateMainView;
        getChildren().addAll(backgroundPanel);
    }

    public Rectangle getBackgroundPanel() {
        return backgroundPanel;
    }

    public void addNewElement(Node layer) {
        getChildren().addAll(layer);
        DragResizeMod.makeResizable(layer);
    }
}
