package com.photobooth.state;

import com.photobooth.view.MainView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WaitingForInputAnimationState implements StateInterface {

    public static final String GIF_FOLDER = "src/App/resources/";
    private MainView mainView;
    private String animationName;
    private StateMachine machine;

    public WaitingForInputAnimationState(MainView mainView, String animationName) {
        this.mainView = mainView;
        this.animationName = animationName;
    }

    public void setMachine(StateMachine machine) {
        this.machine = machine;
    }

    public void init() {
        replaceAnimation(this.animationName);
    }

    private void replaceAnimation(String animationName) {
        JLabel animatedGif = new JLabel();
        Image image = Toolkit.getDefaultToolkit().createImage(GIF_FOLDER + animationName);
        animatedGif.setIcon(new ImageIcon(image));

        animatedGif.addMouseListener(onClickAction);
        mainView.setAnimatedGif(animatedGif);
    }

    MouseAdapter onClickAction = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            endState();
        }
    };

    public void endState() {
        machine.nextState();
    }


    @Override
    public void onClose() {

    }
}
