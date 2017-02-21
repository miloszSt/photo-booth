package com.photobooth.templateEdytor.serializable;

import com.photobooth.templateEdytor.elements.CircleElement;
import com.photobooth.util.ColorUtils;
import javafx.scene.paint.Paint;

import java.io.Serializable;

public class CircleSerializable implements Serializable, SerializableTemplateInterface {

    Integer thickness;
    Integer width;
    Integer height;
    String backgroundColor;
    Integer top;
    Integer left;
    Integer radius;
    Integer rotation;

    String name;
    String color;

    public CircleSerializable(CircleElement circleElement){
        this.top = new Double(circleElement.getElementTop()).intValue();
        this.left = new Double(circleElement.getElementLeft()).intValue();;
        this.radius = new Double(circleElement.getElementWidth()).intValue();;
        this.name = circleElement.getName();
        this.color = circleElement.getElementColor().toString();
    }

    public CircleSerializable(String backgroundColor, Integer height, Integer width, String name, Integer left, Integer top, String color, Integer thickness) {
        this.backgroundColor = backgroundColor;
        this.height = height;
        this.width = width;
        this.name = name;
        this.left = left;
        this.top = top;
        this.color = color;
        this.thickness = thickness;
        this.radius = height;
    }

    public CircleElement toElement(){
        CircleElement circleElement = new CircleElement(this.radius, ColorUtils.parseStringToColor(backgroundColor));
        circleElement.setId(name);
        circleElement.setLayoutY(top);
        circleElement.setLayoutX(left);
        circleElement.setRadius(radius / 2);
        circleElement.setStroke(ColorUtils.parseStringToColor(color), thickness);
        return circleElement;
    }
}
