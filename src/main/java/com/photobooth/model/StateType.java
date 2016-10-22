package com.photobooth.model;

/**
 * @author mst
 */
public class StateType {

    private String label;

    private String fxmlViewPath;

    private boolean shouldContainAnimation;

    public StateType(String label, String fxmlViewPath, boolean shouldContainAnimation) {
        this.label = label;
        this.fxmlViewPath = fxmlViewPath;
        this.shouldContainAnimation = shouldContainAnimation;
    }

    public String getLabel() {
        return label;
    }

    public String getFxmlViewPath() {
        return fxmlViewPath;
    }

    public boolean shouldContainAnimation() {
        return shouldContainAnimation;
    }

    @Override
    public String toString() {
        return label;
    }
}
