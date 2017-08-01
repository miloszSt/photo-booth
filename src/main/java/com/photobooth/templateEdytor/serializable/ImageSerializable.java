package com.photobooth.templateEdytor.serializable;


import com.photobooth.templateEdytor.elements.ImageElement;
import com.photobooth.util.ColorUtils;
import javafx.scene.paint.Paint;
import javafx.scene.transform.Rotate;

import java.awt.*;
import java.io.Serializable;

public class ImageSerializable implements Serializable, SerializableTemplateInterface {

    String strokeColor;
    Integer thickness;
    Integer rotation;
    Integer top;
    Integer left;
    Integer width;
    Integer height;

    String name;
    String imageUrl;

    public ImageSerializable(ImageElement imageElement){
        this.top = new Double(imageElement.getElementTop()).intValue();
        this.left = new Double(imageElement.getElementLeft()).intValue();
        this.width = new Double(imageElement.getElementWidth()).intValue();
        this.height = new Double(imageElement.getElementHeight()).intValue();
        this.imageUrl = imageElement.getImageAbsolutePath();
        this.name = imageElement.getName();
    }

    public ImageSerializable(Integer top, Integer left, Integer width, Integer height, String name, String imageUrl, Integer thickness, String strokeColor, Integer rotation) {
        this.top = top;
        this.left = left;
        this.width = width;
        this.height = height;
        this.name = name;
        this.imageUrl = imageUrl;
        this.thickness = thickness;
        this.strokeColor = strokeColor;
        this.rotation = rotation;
    }

    public ImageElement toElement(){
        ImageElement imageElement = new ImageElement(this.width, this.height, Paint.valueOf("fff"));
        imageElement.setId(name);
        imageElement.setLayoutY(top);
        imageElement.setLayoutX(left);
        imageElement.setWidth(width);
        imageElement.setHeight(height);
        imageElement.setImageAbsolutePath(imageUrl);
        imageElement.setImage();
        imageElement.setStroke(ColorUtils.parseStringToColor(strokeColor), thickness);
        imageElement.setRotate(rotation);
        return imageElement;
    }
}
