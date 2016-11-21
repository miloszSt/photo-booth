package com.photobooth.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ConfigurationUtil {

    public static Configuration initConfiguration(){
        if(Files.exists(Paths.get("/configuration"))) {
            FileInputStream fin = null;
            ObjectInputStream ois = null;
            try {
                fin = new FileInputStream("/photoBooth/configuration.cfg");
                ois = new ObjectInputStream(fin);
                return (Configuration) ois.readObject();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fin.close();
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else {
            return new Configuration("/photoBooth/animation/", "/photoBooth/templates/", "/photoBooth/currentPhotos/", "/photoBooth/archivePhotos", "/photoBooth/templateImages/", "/photoBooth/temp/", "/photoBooth/stateFlows");
        }
        return null;
    }

}
