package com.photobooth.view;

import javax.swing.*;

/**
 * Represents main view of application. Displays invitation animation and handle click on it.
 */
public class MainView extends JFrame{

    public MainView() {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // this will hide close button
        setUndecorated(true);
        initComponents();
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    /** Inits all view components */
    private void initComponents() {
        // TODO initialize view components
    }
}
