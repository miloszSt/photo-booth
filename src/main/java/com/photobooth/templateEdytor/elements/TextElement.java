package com.photobooth.templateEdytor.elements;

import com.photobooth.util.ColorUtils;
import com.photobooth.util.IdCreator;
import com.photobooth.templateEdytor.serializable.SerializableTemplateInterface;
import com.photobooth.templateEdytor.serializable.TextSerializable;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.List;

public class TextElement extends StackPane implements TemplateElementInterface{

    private final Integer elementId;
    private Rectangle rectangle;
    private Text text;

    public TextElement() {
        setId("Text Element");
        rectangle = new Rectangle(100, 100, Color.TRANSPARENT);
        text = new Text("Text");
        getChildren().addAll(rectangle,text);
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
        return null;
    }

    @Override
    public SerializableTemplateInterface serialize() {
        return new TextSerializable(this);
    }

    @Override
    public void setBackgroundColor(Paint color) {
        rectangle.setFill(color);
    }

    @Override
    public void setSecondColor(Paint color) {
        text.setFill(color);
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
//        rectangle.setFill(Color.TRANSPARENT);
//        rectangle.setFill(color);
    }

    public void setFontStyle(Integer textSize, String textColor, Boolean isBold, Boolean isItalic, String fontName) {

        Font font = Font.font(fontName, textSize);
        if(isBold){
            font = Font.font(fontName, FontWeight.BOLD, textSize);
        }

        if(isItalic){
            font = Font.font(fontName, FontPosture.ITALIC, textSize);
        }

        if (isBold && isItalic){
            font = Font.font(fontName, FontWeight.BOLD, FontPosture.ITALIC, textSize);
        }
        text.setFont(font);
        text.setFill(ColorUtils.parseStringToColor(textColor));
    }

    public void setTextSize(int textSize){
        text.setFont(new Font(textSize));
    }

    public void setTextValue(String textValue) {
        text.setText(textValue);
    }
}
