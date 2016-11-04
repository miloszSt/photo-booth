package com.photobooth.model;

/**
 * @author mst
 */
public class StateDef {

    private String label;

    private String fxmlViewPath;

    private String animationPath;

    private String templateName;

    public StateDef() {}

    public StateDef(String label, String fxmlViewPath, String animationPath) {
        this.label = label;
        this.fxmlViewPath = fxmlViewPath;
        this.animationPath = animationPath;
    }

    public StateDef(String label, String fxmlViewPath, String animationPath, String templateName) {
        this.label = label;
        this.fxmlViewPath = fxmlViewPath;
        this.animationPath = animationPath;
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

    public String getAnimationPath() {
        return animationPath;
    }

    public void setAnimationPath(String animationPath) {
        this.animationPath = animationPath;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
}
