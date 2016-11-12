package com.photobooth.model;

/**
 * @author mst
 */
public class StateType {

    private String label;

    private String fxmlViewPath;

    private boolean shouldContainAnimation;

    private boolean shouldContainTemplate;

    public StateType(String label, String fxmlViewPath, boolean shouldContainAnimation, boolean shouldContainTemplate) {
        this.label = label;
        this.fxmlViewPath = fxmlViewPath;
        this.shouldContainAnimation = shouldContainAnimation;
        this.shouldContainTemplate = shouldContainTemplate;
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

    public boolean shouldContainTemplate() {
        return shouldContainTemplate;
    }

    @Override
    public String toString() {
        return label;
    }
}
