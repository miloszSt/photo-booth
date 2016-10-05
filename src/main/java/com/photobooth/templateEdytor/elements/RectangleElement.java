package com.photobooth.templateEdytor.elements;


import com.photobooth.IdCreator;
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

    @Override
    public void select() {
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DASHED,null, new BorderWidths(1))));
    }

    @Override
    public void deselect() {
        setBorder(new Border(new BorderStroke(null,null,null, BorderWidths.EMPTY)));
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

    @Override
    public Integer getElementId() {
        return elementId;
    }
}
