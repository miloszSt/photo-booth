package com.photobooth.model;

import java.util.List;

/**
 * @author mst
 */
public class StateDef {

    private String label;

    private String fxmlViewPath;

    private List<String> animationPaths;

    private String templateName;

    public StateDef() {}

    public StateDef(String label, String fxmlViewPath, List<String> animationPaths) {
        this.label = label;
        this.fxmlViewPath = fxmlViewPath;
        this.animationPaths = animationPaths;
    }

    public StateDef(String label, String fxmlViewPath, List<String> animationPaths, String templateName) {
        this.label = label;
        this.fxmlViewPath = fxmlViewPath;
        this.animationPaths = animationPaths;
        this.templateName = templateName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getFxmlViewPath() {
        return fxmlViewPath;
    }

    public void setFxmlViewPath(String fxmlViewPath) {
        this.fxmlViewPath = fxmlViewPath;
    }

    public List<String> getAnimationPaths() {
        return animationPaths;
    }

    public void setAnimationPaths(List<String> animationPaths) {
        this.animationPaths = animationPaths;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
}
