package com.photobooth.util;

import java.io.Serializable;

public class Configuration implements Serializable{
    private String animationPath;
    private String templatePath;
    private String currentPhotosPath;
    private String archivePhotosPath;
    private String templateImagesPath;
    private String tempPath;
    private String stateFlowPath;
    public Configuration(String animationPath, String templatePath, String currentPhotosPath, String archivePhotosPath, String templateImagesPath, String temp, String stateFlowPath) {
        this.animationPath = animationPath;
        this.templatePath = templatePath;
        this.currentPhotosPath = currentPhotosPath;
        this.archivePhotosPath = archivePhotosPath;
        this.templateImagesPath = templateImagesPath;
        this.tempPath = temp;
        this.stateFlowPath = stateFlowPath;
        initDirectories();
    }

    public String getTemplateImagesPath() {
        return templateImagesPath;
    }

    public String getTempPath() {
        return tempPath;
    }

    public String getStateFlowPath() {
        return stateFlowPath;
    }

    private void initDirectories() {
        FileUtils.createDirIfDoesntExists(animationPath);
        FileUtils.createDirIfDoesntExists(templatePath);
        FileUtils.createDirIfDoesntExists(currentPhotosPath);
        FileUtils.createDirIfDoesntExists(archivePhotosPath);
        FileUtils.createDirIfDoesntExists(templateImagesPath);
        FileUtils.createDirIfDoesntExists(tempPath);
        FileUtils.createDirIfDoesntExists(stateFlowPath);
    }


    public String getAnimationPath() {
        return animationPath;
    }

    public void setAnimationPath(String animationPath) {
        this.animationPath = animationPath;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getCurrentPhotosPath() {
        return currentPhotosPath;
    }

    public void setCurrentPhotosPath(String currentPhotosPath) {
        this.currentPhotosPath = currentPhotosPath;
    }

    public String getArchivePhotosPath() {
        return archivePhotosPath;
    }

    public void setArchivePhotosPath(String archivePhotosPath) {
        this.archivePhotosPath = archivePhotosPath;
    }
}
