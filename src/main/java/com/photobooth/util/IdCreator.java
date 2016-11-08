package com.photobooth.util;

public class IdCreator {

    private static Integer counter = 1;

    public static Integer getCounter(){
        return counter++;
    }
}
