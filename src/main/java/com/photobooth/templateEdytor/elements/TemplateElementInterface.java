package com.photobooth.templateEdytor.elements;

public interface TemplateElementInterface {
    void select();
    void deselect();

    String getName();

    String getElementTop();

    String getElementLeft();

    String getElementWidth();

    String getElementHeight();

    String getElementRotation();
}
