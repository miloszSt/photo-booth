package com.photobooth.templateEdytor.panels;

import com.photobooth.templateEdytor.TemplateMainView;
import com.photobooth.templateEdytor.elements.DragResizeMod;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CenterPanel extends Pane {

    private final TemplateMainView templateMainView;
    private final Rectangle backgroundPanel = new Rectangle(1200, 900, Color.WHITE);
    public CenterPanel(TemplateMainView templateMainView) {
        super();

        this.templateMainView = templateMainView;

        Rectangle rectangle2 = new Rectangle(50,50, Color.BLUE);

        enableDragging(rectangle2);
        getChildren().addAll(backgroundPanel,rectangle2);
    }


    public void addNewElement(Node layer) {
        getChildren().addAll(layer);
        enableDragging(layer);
        DragResizeMod.makeResizable(layer);

    }

    private void enableDragging(Node node) {
        final ObjectProperty<Point2D> mouseAnchor = new SimpleObjectProperty<>();
        node.setOnMousePressed( event -> mouseAnchor.set(new Point2D(event.getSceneX(), event.getSceneY())));
        node.setOnMouseDragged( event -> {
            double deltaX = event.getSceneX() - mouseAnchor.get().getX();
            double deltaY = event.getSceneY() - mouseAnchor.get().getY();

            node.relocate(node.getLayoutX()+deltaX, node.getLayoutY()+deltaY);
            mouseAnchor.set(new Point2D(event.getSceneX(), event.getSceneY()));;
        });
    }
}
