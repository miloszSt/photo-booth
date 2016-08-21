package com.photobooth.controller;

import com.photobooth.view.MainView;

/**
 * Main controller, which displays and manages main view of application.
 */
public class AppController {

    public AppController() {
        // TODO Configuration magic here
    }

    /** Shows application layaut */
    public void startApp() {
        MainView mainView = new MainView();
    }
}
