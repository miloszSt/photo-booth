package com.photobooth.templateEdytor.elements;

import com.photobooth.templateEdytor.serializable.SerializableTemplateInterface;
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

    SerializableTemplateInterface serialize();

    void setBackgroundColor(Paint color);

    void setSecondColor(Paint color);

}
