package com.photobooth.templateEdytor.serializable;

import com.photobooth.templateEdytor.elements.CircleElement;
import javafx.scene.paint.Paint;

import java.io.Serializable;

public class CircleSerializable implements Serializable, SerializableTemplateInterface {
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

    public CircleElement toElement(){
        CircleElement circleElement = new CircleElement(this.radius, Paint.valueOf(color));
        circleElement.setId(name);
        circleElement.setLayoutY(top);
        circleElement.setLayoutX(left);
        circleElement.setRadius(radius);
        return circleElement;
    }
}
