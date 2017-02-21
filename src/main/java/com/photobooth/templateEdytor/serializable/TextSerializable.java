package com.photobooth.templateEdytor.serializable;


import com.photobooth.templateEdytor.elements.ImageElement;
import com.photobooth.templateEdytor.elements.RectangleElement;
import com.photobooth.templateEdytor.elements.TextElement;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.Serializable;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class TextSerializable implements Serializable, SerializableTemplateInterface {

    Integer top;
    Integer left;
    Integer width;
    Integer height;

    Integer rotation;

    String name;
    String color;

    Integer textSize;
    String textColor;
    String textValue;

    Boolean isItalic;
    Boolean isBold;
    String fontName;



    public TextSerializable(TextElement textElement){
        this.top = new Double(textElement.getElementTop()).intValue();
        this.left = new Double(textElement.getElementLeft()).intValue();
        this.width = new Double(textElement.getElementWidth()).intValue();
        this.height = new Double(textElement.getElementHeight()).intValue();

        this.name = textElement.getName();
        this.color = textElement.getElementColor().toString();

        this.textSize = textElement.getTextSize();
        this.textColor = textElement.getTextColor();
        this.textValue = textElement.getTextValue();
    }


    public TextSerializable(Integer top, Integer left, Integer width, Integer height, String name, String color, Integer textSize, String textColor, String textValue, Boolean isItalic, Boolean isBold, String fontName) {
        this.top = top;
        this.left = left;
        this.width = width;
        this.height = height;
        this.name = name;
        this.color = color;
        this.textSize = textSize;
        this.textColor = textColor;
        this.textValue = textValue;
        this.isItalic = isItalic;
        this.isBold = isBold;
        this.fontName = fontName;
    }


    public TextElement toElement(){
        TextElement textElement = new TextElement();
        textElement.setId(name);
        textElement.setLayoutY(top);
        textElement.setLayoutX(left);
        textElement.setWidth(width);
        textElement.setHeight(height);


        textElement.setBackgroundColor(Color.TRANSPARENT);
        textElement.setFontStyle(textSize * 2,textColor, isBold, isItalic, fontName);
        textElement.setTextValue(textValue);

        return textElement;
    }

}
