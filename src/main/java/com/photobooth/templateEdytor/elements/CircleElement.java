package com.photobooth.templateEdytor.elements;

import com.photobooth.IdCreator;
import com.photobooth.templateEdytor.serializable.CircleSerializable;
import com.photobooth.templateEdytor.serializable.SerializableTemplateInterface;
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

    public void setRadius(Integer radious){
        circle.setRadius(radious);
    }


    @Override
    public void deselect() {
        getChildren().remove(selectionRectangle);
    }

    @Override
    public String getName() {
        return getId();
    }

    @Override
    public double getElementTop() {
        return getBoundsInParent().getMinY();
    }

    @Override
    public double getElementLeft() {
        return getBoundsInParent().getMinX();
    }

    @Override
    public Integer getElementId() {
        return elementId;
    }

    @Override
    public double getElementWidth() {
        return circle.getRadius();
    }

    @Override
    public double getElementHeight() {
        return circle.getRadius();
    }

    @Override
    public double getElementRotation() {
        return 0d;
    }

    @Override
    public Paint getElementColor() {
        return circle.fillProperty().getValue();
    }

    @Override
    public SerializableTemplateInterface serialize() {
        return new CircleSerializable(this);
    }

    @Override
    public void setBackgroundColor(Paint color) {
        circle.setFill(color);
    }

    @Override
    public void setSecondColor(Paint color) {

    }


}
