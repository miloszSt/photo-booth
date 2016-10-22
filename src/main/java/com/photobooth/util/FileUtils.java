package com.photobooth.util;

import java.io.File;

/**
 * @author mst
 */
public class FileUtils {

    private FileUtils() {}

    public static final String RESOURCES_FOLDER = "src/main/resources/";

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

}
