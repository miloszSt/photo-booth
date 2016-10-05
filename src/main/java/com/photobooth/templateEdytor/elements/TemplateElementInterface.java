package com.photobooth.templateEdytor.elements;

public interface TemplateElementInterface {
    void select();
    void deselect();

    Integer getElementId();

    String getName();

    String getElementTop();

    String getElementLeft();

    String getElementWidth();

    String getElementHeight();

    String getElementRotation();
}
