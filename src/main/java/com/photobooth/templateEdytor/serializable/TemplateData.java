package com.photobooth.templateEdytor.serializable;

import javafx.print.PageOrientation;
import javafx.print.Paper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TemplateData implements Serializable{

    private String name;
    private PaperSizeEnum paper;

    private Integer height;
    private Integer wight;
    private PageOrientation orientation;
    private String backgroundColor;


    private List<SerializableTemplateInterface> templateInterfaceList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Paper getPaper() {

        return paper.toPaper();
    }


    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setPaper(Paper paper) {
        this.paper = PaperSizeEnum.getFromPaper(paper);
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWight() {
        return wight;
    }

    public void setWight(Integer wight) {
        this.wight = wight;
    }

    public PageOrientation getOrientation() {
        return orientation;
    }

    public void setOrientation(PageOrientation orientation) {
        this.orientation = orientation;
    }

    public List<SerializableTemplateInterface> getTemplateInterfaceList() {
        return templateInterfaceList;
    }

    public void setTemplateInterfaceList(List<SerializableTemplateInterface> templateInterfaceList) {
        this.templateInterfaceList = templateInterfaceList;
    }
}
