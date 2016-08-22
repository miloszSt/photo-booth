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

    public MainView() {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // this will hide close button
        setUndecorated(true);
        JLabel animatedGif = new JLabel();

        animatedGif.setIcon(new ImageIcon("src/Main/resources/countdown.gif"));
        Image image = Toolkit.getDefaultToolkit().createImage("src/Main/resources/countdown.gif");
        animatedGif.setIcon(new ImageIcon(image));
        add(animatedGif);
        initComponents();
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    /**
     * Inits all view components
     */
    private void initComponents() {
        // TODO initialize view components
    }
}
