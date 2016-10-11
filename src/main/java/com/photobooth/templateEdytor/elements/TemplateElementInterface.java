package com.photobooth.templateEdytor.elements;

import javafx.scene.paint.Paint;

public interface TemplateElementInterface {
    void select();
    void deselect();

    Integer getElementId();

    String getName();

    double getElementTop();

    double getElementLeft();

    double getElementWidth();

    double getElementHeight();

    double getElementRotation();

    Paint getElementColor();
}
