package com.photobooth.templateEdytor.serializable;


import com.photobooth.templateEdytor.elements.ImageElement;
import com.photobooth.templateEdytor.elements.RectangleElement;
import javafx.scene.paint.Paint;

import java.io.Serializable;

public class RectangleSerializable implements Serializable, SerializableTemplateInterface {

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

    public RectangleElement toElement(){
        RectangleElement rectangleElement = new RectangleElement(this.width, this.height, Paint.valueOf(color));
        rectangleElement.setId(name);
        rectangleElement.setLayoutY(top);
        rectangleElement.setLayoutX(left);
        rectangleElement.setWidth(width);
        rectangleElement.setHeight(height);

        return rectangleElement;
    }
}
