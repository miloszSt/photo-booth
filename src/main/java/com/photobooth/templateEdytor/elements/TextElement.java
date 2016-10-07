package com.photobooth.templateEdytor.elements;

import com.photobooth.IdCreator;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class TextElement extends StackPane implements TemplateElementInterface{

    private final Integer elementId;
    private Rectangle rectangle;
    private Text text;

    public TextElement() {
        setId("Text Element");
        rectangle = new Rectangle(100, 100, Color.BROWN);
        text = new Text("Text");
        getChildren().addAll(rectangle, text);
        elementId = IdCreator.getCounter();
    }

    public void setText(Text text) {
        this.text = text;
        getChildren().set(1, text);
    }

    public Rectangle getRectangle() {
        return (Rectangle) getChildren().get(0);
    }

    public Text getText() {
        return (Text) getChildren().get(1);
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
