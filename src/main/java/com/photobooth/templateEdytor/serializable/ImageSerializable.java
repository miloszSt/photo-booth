package com.photobooth.templateEdytor.serializable;


import com.photobooth.templateEdytor.elements.ImageElement;
import javafx.scene.paint.Paint;

import java.io.Serializable;

public class ImageSerializable implements Serializable, SerializableTemplateInterface {

    Integer top;
    Integer left;
    Integer width;
    Integer height;

    String name;
    String color;
    String imageUrl;

    public ImageSerializable(ImageElement imageElement){
        this.top = new Double(imageElement.getElementTop()).intValue();
        this.left = new Double(imageElement.getElementLeft()).intValue();
        this.width = new Double(imageElement.getElementWidth()).intValue();
        this.height = new Double(imageElement.getElementHeight()).intValue();
        this.imageUrl = imageElement.getImageAbsolutePath();
        this.name = imageElement.getName();
        this.color = imageElement.getElementColor().toString();
    }

    public ImageElement toElement(){
        ImageElement imageElement = new ImageElement(this.width, this.height, Paint.valueOf(color));
        imageElement.setId(name);
        imageElement.setLayoutY(top);
        imageElement.setLayoutX(left);
        imageElement.setWidth(width);
        imageElement.setHeight(height);
        imageElement.setImageAbsolutePath(imageUrl);
        imageElement.setImage();

        return imageElement;
    }
}
