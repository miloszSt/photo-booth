package com.photobooth;

public class IdCreator {

    private static Integer counter = 1;

    public static Integer getCounter(){
        return counter++;
    }
}
