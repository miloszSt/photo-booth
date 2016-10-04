package com.photobooth.templateEdytor.elements;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class PhotoElement extends Rectangle{

    private Integer counter;
    public PhotoElement(double width, double height, Paint fill, Integer counter) {
        super(width, height, fill);
        this.counter = counter;
    }
}
