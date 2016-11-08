package com.photobooth.util;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author mst
 */
public class FileUtils {

    private FileUtils() {}

    public static final String RESOURCES_FOLDER = "src/main/resources/";

    public static final List<String> PHOTO_EXTENSIONS = new ArrayList<String>(){
        {
            add("jpg");
            add("jpeg");
            add("png");
            add("tif");
            add("tiff");
            // dodać jak malo
        }
    };

    /**
     * Loads file or folder from 'resources' according to given path.
     *
     * @param path to file or folder
     * @return
     */
    public static File load(String path) {
        ClassLoader classLoader = FileUtils.class.getClassLoader();
        return new File(classLoader.getResource(path).getFile());
    }

    /**
     * Gets photos from given path to folder were photos are stored.
     *
     * @param path path to photo's folder
     * @return list of photo's files
     */
    public static List<File> getPhotos(String path) {
        return Arrays.asList(new File(path).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return PHOTO_EXTENSIONS.contains(getExtension(file.getPath()));
            }
        }));
    }

    public static String getExtension(String pathname) {
        return pathname.substring(pathname.lastIndexOf('.') + 1);
    }

}
