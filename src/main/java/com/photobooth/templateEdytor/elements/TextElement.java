package com.photobooth.templateEdytor.elements;

import com.photobooth.IdCreator;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
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

    public Integer getTextSize() {
        return  new Double(text.getFont().getSize()).intValue();
    }

    public String getTextColor() {
        return  text.getFill().toString();
    }


    public String getTextValue() {
        return text.getText();
    }

    public void setHeight(Integer height){
        rectangle.setHeight(height);
    }

    public void setWidth(Integer width){
        rectangle.setWidth(width);
    }

    public void setBackgroundColor(Color color) {
        rectangle.setFill(color);
    }

    public void setFontStyle(Integer textSize, String textColor) {
        text.setFont(new Font(textSize));
        text.setFill(Color.valueOf(textColor));
    }

    public void setTextValue(String textValue) {
        text.setText(textValue);
    }
}
