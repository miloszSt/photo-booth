package com.photobooth.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Represents main view of application. Displays invitation animation and handle click on it.
 */
public class MainView extends JFrame {

    private JLabel animatedGif;

    public MainView() {

        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // this will hide close button
        setUndecorated(true);

        initComponents();
        setSize(new Dimension(300, 300));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);

    }

    public void setAnimatedGif(JLabel animatedGif) {
        if(this.animatedGif != null) {
            remove(this.animatedGif);
        }
        this.animatedGif = animatedGif;
        add(animatedGif);
        revalidate();
    }



    /**
     * Inits all view components
     */
    private void initComponents() {
        // TODO initialize view components
    }
}
