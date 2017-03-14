package com.photobooth.util;

import com.photobooth.model.StateDef;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.List;

public class Configuration implements Serializable {
    final static Logger logger = Logger.getLogger(Configuration.class);
    private static final String STATE_FLOW_FILE = "state-flow.ser";
    private String animationPath;
    private String templatePath;
    private String currentPhotosPath;
    private String archivePhotosPath;
    private String templateImagesPath;
    private String tempPath;
    private String stateFlowPath;
    private String beforePhotoPath;

    public Configuration(String animationPath, String templatePath, String currentPhotosPath, String archivePhotosPath, String templateImagesPath, String temp, String stateFlowPath,
                         String beforePhotoPath) {
        this.animationPath = animationPath;
        this.templatePath = templatePath;
        this.currentPhotosPath = currentPhotosPath;
        this.archivePhotosPath = archivePhotosPath;
        this.templateImagesPath = templateImagesPath;
        this.tempPath = temp;
        this.stateFlowPath = stateFlowPath;
        this.beforePhotoPath = beforePhotoPath;
        initDirectories();
    }

    private void initDirectories() {
        FileUtils.createDirIfDoesntExists(animationPath);
        FileUtils.createDirIfDoesntExists(templatePath);
        FileUtils.createDirIfDoesntExists(currentPhotosPath);
        FileUtils.createDirIfDoesntExists(archivePhotosPath);
        FileUtils.createDirIfDoesntExists(templateImagesPath);
        FileUtils.createDirIfDoesntExists(tempPath);
        FileUtils.createDirIfDoesntExists(stateFlowPath);
        FileUtils.createDirIfDoesntExists(beforePhotoPath);
    }

    public void saveStateFlow(List<StateDef> customStates) {
        FileOutputStream fout = null;
        ObjectOutputStream oos = null;
        try {
            fout = new FileOutputStream(getStateFlowFilePath());
            oos = new ObjectOutputStream(fout);
            oos.writeObject(customStates);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException e) {
                    logger.error(e);
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    logger.error(e);
                }
            }
        }
    }

    public List<StateDef> loadLastStatFlow(){
        FileInputStream fin = null;
        ObjectInputStream ois = null;

        try {
            fin = new FileInputStream(getStateFlowFilePath());
            ois = new ObjectInputStream(fin);
            return (List<StateDef>) ois.readObject();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    logger.error(e);
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    logger.error(e);
                }
            }
        }

        return null;
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

    public String getTemplateImagesPath() {
        return templateImagesPath;
    }

    public String getTempPath() {
        return tempPath;
    }

    public String getStateFlowPath() {
        return stateFlowPath;
    }

    public String getStateFlowFilePath() {
        return stateFlowPath + "/" + STATE_FLOW_FILE;
    }

    public boolean isStateFlowDefined() {
        return new File(getStateFlowFilePath()).exists();
    }

    public String getBeforePhotoPath() {
        return beforePhotoPath;
    }

    public void setBeforePhotoPath(String beforePhotoPath) {
        this.beforePhotoPath = beforePhotoPath;
    }
}
