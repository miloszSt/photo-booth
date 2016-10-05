package com.photobooth.templateEdytor.elements;

import com.photobooth.IdCreator;
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

