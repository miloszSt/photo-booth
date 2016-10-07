package com.photobooth.templateEdytor.elements;

import com.photobooth.IdCreator;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class CircleElement extends StackPane implements TemplateElementInterface{

    private final Integer elementId;

    private Circle circle;

    public CircleElement(int width, Paint fill) {
        super();
        setId("Circle Element");
        circle = new Circle(width, fill);
        getChildren().addAll(circle);
        elementId = IdCreator.getCounter();
    }

    private Rectangle selectionRectangle = new Rectangle(30,30,Color.YELLOWGREEN);
    @Override
    public void select() {
        deselect();
        setAlignment(selectionRectangle, Pos.TOP_LEFT);
        getChildren().addAll(selectionRectangle);
    }
    @Override
    public void deselect() {
        getChildren().remove(selectionRectangle);
    }
    @Override
    public Integer getElementId() {
        return elementId;
    }

    @Override
    public String getName() {
        return getId();
    }

    @Override
    public String getElementTop() {
        return null;
    }

    @Override
    public String getElementLeft() {
        return null;
    }

    @Override
    public String getElementWidth() {
        return null;
    }

    @Override
    public String getElementHeight() {
        return null;
    }

    @Override
    public String getElementRotation() {
        return null;
    }
}
