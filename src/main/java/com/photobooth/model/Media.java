package com.photobooth.model;


import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;

public class Media implements Serializable {

    private String path;

    public Media() {}

    public Media(String path) {
        this.path = path;
    }

    @XmlAttribute(name = "path")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
