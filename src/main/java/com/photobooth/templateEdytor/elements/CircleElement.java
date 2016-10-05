package com.photobooth.templateEdytor.elements;

import com.photobooth.IdCreator;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

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

    @Override
    public void select() {
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DASHED,null, new BorderWidths(1))));;
    }

    @Override
    public void deselect() {
        setBorder(new Border(new BorderStroke(null,null,null, BorderWidths.EMPTY)));
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
