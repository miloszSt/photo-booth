package com.photobooth;


import edsdk.api.CanonCamera;

import edsdk.utils.CanonConstants;

import java.io.File;

public class Foto {

    public static void main(String[] args) {
        takePhoto("e:/","photo.txt");
        System.out.println("job's done");
    }

    public static File takePhoto(String directory, String fileName){
        CanonCamera slr = new CanonCamera();
        slr.openSession();
        CanonConstants.EdsAFMode autoFocusMode = slr.getAutoFocusMode();
        File[] shoot = slr.shoot(CanonConstants.EdsSaveTo.kEdsSaveTo_Host, 19, new File(directory + fileName));
        System.out.println("photo count " + shoot.length);
        slr.closeSession();

        return shoot[0];
    }
}
