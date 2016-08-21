package com.photobooth;

import com.photobooth.controller.AppController;

/**
 * Entry point of application
 *
 * @author MS
 */
public class Main {

    public static void main(String[] args)
    {
       AppController controller = new AppController();
       controller.startApp();
    }
}
