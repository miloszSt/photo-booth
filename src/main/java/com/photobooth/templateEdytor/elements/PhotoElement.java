package com.photobooth.templateEdytor.elements;

import com.photobooth.IdCreator;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class PhotoElement extends StackPane implements TemplateElementInterface{

    private final Integer elementId;
    private Integer counter;
    private Rectangle rectangle;
    private Text text;

    public PhotoElement(double width, double height, Paint fill, Integer counter) {
        super();
        setId("Photo Element");
        this.counter = counter;
        this.rectangle = new Rectangle(width, height, fill);;
        this.text = new Text(counter.toString());
        text.setFont(Font.font ("Verdana", 50));
        getChildren().addAll(rectangle, text);

        elementId = IdCreator.getCounter();
    }
    private Rectangle selectionRectangle = new Rectangle(30,30,Color.YELLOWGREEN);
    @Override
    public void select() {
        deselect();
        setAlignment(selectionRectangle, Pos.TOP_LEFT);
        getChildren().addAll(selectionRectangle);
    }


    public void setHeight(Integer height){
        rectangle.setHeight(height);
    }

    public void setWidth(Integer width){
        rectangle.setWidth(width);
    }


    public Integer getCounter() {
        return counter;
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
    public Integer getElementId() {
        return elementId;
    }

}

