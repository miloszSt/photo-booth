package com.photobooth.controller.spec;

import java.io.File;

/**
 * Initialize view with photo e.g. for preview photo
 */
public interface PhotoInitializable {
    void initPhoto(String photoFilePath);
}
