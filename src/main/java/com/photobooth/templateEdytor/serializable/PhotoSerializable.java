package com.photobooth.templateEdytor.serializable;

import com.photobooth.templateEdytor.elements.ImageElement;
import com.photobooth.templateEdytor.elements.PhotoElement;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.Serializable;

public class PhotoSerializable implements Serializable, SerializableTemplateInterface {

    Integer top;
    Integer left;
    Integer width;
    Integer height;
    Integer counter;

    String name;


    public PhotoSerializable(PhotoElement photoElement){
        this.top = new Double(photoElement.getElementTop()).intValue();
        this.left = new Double(photoElement.getElementLeft()).intValue();
        this.width = new Double(photoElement.getElementWidth()).intValue();
        this.height = new Double(photoElement.getElementHeight()).intValue();
        this.counter = photoElement.getCounter();
        this.name = photoElement.getName();
//        this.color = photoElement.getElementColor().toString();
    }

    public PhotoSerializable(Integer top, Integer left, Integer width, Integer height, Integer counter, String name) {
        this.top = top;
        this.left = left;
        this.width = width;
        this.height = height;
        this.counter = counter;
        this.name = name;
//        this.color = color;
    }

    public PhotoElement toElement(){
        PhotoElement photoElement = new PhotoElement(this.width, this.height, Color.BLUE, counter);
        photoElement.setId(name);
        photoElement.setLayoutY(top);
        photoElement.setLayoutX(left);
        photoElement.setWidth(width);
        photoElement.setHeight(height);

        return photoElement;
    }

}
