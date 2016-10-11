package com.photobooth;

public class PhotoNumberCreator {

    public static Integer counter = 1;


    public static Integer getCounter(){
        return counter++;
    }
}
