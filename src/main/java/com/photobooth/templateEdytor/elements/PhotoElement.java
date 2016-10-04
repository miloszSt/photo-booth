package com.photobooth.templateEdytor.elements;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class PhotoElement extends StackPane implements TemplateElementInterface{

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
    }

    @Override
    public void select() {
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,null, new BorderWidths(5))));
    }

    @Override
    public void deselect() {
        setBorder(new Border(new BorderStroke(null,null,null, BorderWidths.EMPTY)));
    }

    @Override
    public String getName() {
        return null;
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

