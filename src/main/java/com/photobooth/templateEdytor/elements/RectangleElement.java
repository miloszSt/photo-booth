package com.photobooth.templateEdytor.elements;


import com.photobooth.util.IdCreator;
import com.photobooth.templateEdytor.serializable.RectangleSerializable;
import com.photobooth.templateEdytor.serializable.SerializableTemplateInterface;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class RectangleElement extends StackPane implements TemplateElementInterface{

    private final Integer elementId;
    private Rectangle rectangle;
    public RectangleElement(double width, double height, Paint fill) {
        super();
        setId("Rectangle Element");
        rectangle = new Rectangle(width, height, fill);
        getChildren().addAll(rectangle);
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
    public double getElementWidth() {
        return getBoundsInParent().getWidth();
    }

    @Override
    public double getElementHeight() {
        return getBoundsInParent().getHeight();
    }

    @Override
    public double getElementRotation() {
        return 0d;
    }

    @Override
    public Paint getElementColor() {
        return rectangle.getFill();
    }

    @Override
    public SerializableTemplateInterface serialize() {
        return new RectangleSerializable(this);
    }

    @Override
    public void setBackgroundColor(Paint color) {
        rectangle.setFill(color);
    }

    @Override
    public void setSecondColor(Paint color) {

    }

    @Override
    public Integer getElementId() {
        return elementId;
    }


    public void setHeight(Integer height){
        rectangle.setHeight(height);
    }

    public void setWidth(Integer width){
        rectangle.setWidth(width);
    }


}
