package com.photobooth.templateEdytor.elements;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class TextElement extends StackPane {

    private Rectangle rectangle;
    private Text text;

    public TextElement() {
        rectangle = new Rectangle(100, 100, Color.ALICEBLUE);
        text = new Text("Text");

        getChildren().addAll(rectangle, text);
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
}
