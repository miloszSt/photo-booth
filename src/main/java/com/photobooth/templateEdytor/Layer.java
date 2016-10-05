package com.photobooth.templateEdytor;

import com.photobooth.templateEdytor.elements.TemplateElementInterface;

public class Layer {

    private String name;
    private Integer elementId;

    public Layer(String name, Integer elementId) {
        this.name = name;
        this.elementId = elementId;
    }

    public Layer(TemplateElementInterface templateElementInterface){
        this.name = templateElementInterface.getName();
        this.elementId = templateElementInterface.getElementId();
    }

    public String getName() {
        return name;
    }

    public Integer getElementId() {
        return elementId;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Layer layer = (Layer) o;

        if (name != null ? !name.equals(layer.name) : layer.name != null) return false;
        return elementId != null ? elementId.equals(layer.elementId) : layer.elementId == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (elementId != null ? elementId.hashCode() : 0);
        return result;
    }
}
