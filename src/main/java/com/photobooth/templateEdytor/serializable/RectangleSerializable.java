package com.photobooth.templateEdytor.serializable;


import com.photobooth.templateEdytor.elements.ImageElement;
import com.photobooth.templateEdytor.elements.RectangleElement;
import com.photobooth.util.ColorUtils;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.Serializable;

public class RectangleSerializable implements Serializable, SerializableTemplateInterface {

    Integer thickness;
    String strokeColor;
    String backgroundColor;
    Integer top;
    Integer left;
    Integer width;
    Integer height;

    String name;
    String color;


    public RectangleSerializable(RectangleElement imageElement){
        this.top = new Double(imageElement.getElementTop()).intValue();
        this.left = new Double(imageElement.getElementLeft()).intValue();
        this.width = new Double(imageElement.getElementWidth()).intValue();
        this.height = new Double(imageElement.getElementHeight()).intValue();

        this.name = imageElement.getName();
        this.color = imageElement.getElementColor().toString();
    }

    public RectangleSerializable(String backgroundColor, Integer height, Integer width, String name, Integer left, Integer top, String s, Integer thickness) {
        this.backgroundColor = backgroundColor;
        this.height = height;
        this.width = width;
        this.name = name;
        this.left = left;
        this.top = top;
        this.color = s;
        this.thickness = thickness;
    }

    public RectangleElement toElement(){

        RectangleElement rectangleElement = new RectangleElement(this.width, this.height,
                ColorUtils.parseStringToColor(backgroundColor));
        rectangleElement.setId(name);
        rectangleElement.setLayoutY(top);
        rectangleElement.setLayoutX(left);
        rectangleElement.setWidth(width);
        rectangleElement.setHeight(height);
        rectangleElement.setStroke(ColorUtils.parseStringToColor(color), thickness);


        return rectangleElement;
    }
}
