package com.photobooth.controller.spec;

import com.photobooth.templateEdytor.serializable.TemplateData;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

public interface TemplateAndPhotoInitializable {

    void setTemplateAndPhotos(TemplateData template, List<File> photos);
}
