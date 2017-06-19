package com.photobooth.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mst
 */
public class StateDef implements Serializable{

    private String label;

    private String fxmlViewPath;

    private List<Media> animationPaths;

    private String templateName;

    public StateDef() {}

    public StateDef(String label, String fxmlViewPath, List<Media> animationPaths) {
        this.label = label;
        this.fxmlViewPath = fxmlViewPath;
        this.animationPaths = animationPaths;
    }

    public StateDef(String label, String fxmlViewPath, List<Media> animationPaths, String templateName) {
        this.label = label;
        this.fxmlViewPath = fxmlViewPath;
        this.animationPaths = animationPaths;
        this.templateName = templateName;
    }

    @XmlAttribute(name = "label")
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @XmlAttribute(name = "fxmlViewPath")
    public String getFxmlViewPath() {
        return fxmlViewPath;
    }

    public void setFxmlViewPath(String fxmlViewPath) {
        this.fxmlViewPath = fxmlViewPath;
    }

    @XmlElement(name = "media")
    public List<Media> getAnimationPaths() {
        return animationPaths;
    }

    public List<String> getMediaPaths() {
        return this.animationPaths.stream()
                .map(Media::getPath)
                .collect(Collectors.toList());
    }

    public void setAnimationPaths(List<Media> animationPaths) {
        this.animationPaths = animationPaths;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
}
