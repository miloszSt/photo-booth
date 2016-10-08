package com.photobooth;

public class IdCreator {

    public static Integer counter = 1;


    public static Integer getCounter(){
        return counter++;
    }
}
