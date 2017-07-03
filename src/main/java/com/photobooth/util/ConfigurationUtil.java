package com.photobooth.util;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ConfigurationUtil {
    final static Logger logger = Logger.getLogger(ConfigurationUtil.class);
    public static Configuration initConfiguration(){
        if(Files.exists(Paths.get("/configuration"))) {
            FileInputStream fin = null;
            ObjectInputStream ois = null;
            try {
                fin = new FileInputStream("/photoBooth/configuration.cfg");
                ois = new ObjectInputStream(fin);
                return (Configuration) ois.readObject();
            } catch (FileNotFoundException e) {
                logger.error(e);
            } catch (ClassNotFoundException e) {
                logger.error(e);
            } catch (IOException e) {
                logger.error(e);
            } finally {
                try {
                    fin.close();
                    ois.close();
                } catch (IOException e) {
                    logger.error(e);
                }
            }
        }else {
            return new Configuration("/photoBooth/animation/",
                    "c:/photoBooth/templates/",
                    "c:/photoBooth/currentPhotos/",
                    "c:/photoBooth/archivePhotos",
                    "c:/photoBooth/templateImages/",
                    "c:/photoBooth/temp/",
                    "c:/photoBooth/stateFlows",
                    "c:/photoBooth/beforePhoto");
        }
        return null;
    }

}
