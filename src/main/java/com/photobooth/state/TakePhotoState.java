package com.photobooth.state;

import com.photobooth.camera.CameraService;
import com.photobooth.view.MainView;

import javax.swing.*;
import java.awt.*;

public class TakePhotoState implements StateInterface {
    private CameraService cameraService;
    private StateMachine machine;
    private String username;
    private String animationName;
    private MainView mainView;

    public TakePhotoState(CameraService cameraService, String username, String animationName, MainView mainView) {
        this.cameraService = cameraService;
        this.username = username;
        this.animationName = animationName;
        this.mainView = mainView;
    }

    @Override
    public void init() {
        replaceAnimation(animationName);
    }

    private void replaceAnimation(String animationName) {
        JLabel animatedGif = new JLabel();
        Image image = Toolkit.getDefaultToolkit().createImage(WaitingForInputAnimationState.GIF_FOLDER + animationName + ".gif");
        animatedGif.setIcon(new ImageIcon(image));

        mainView.setAnimatedGif(animatedGif);

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            for (int j = 0; j < Integer.MAX_VALUE; j++) {
            }
        }
        machine.nextState();
    }

    @Override
    public void onClose() {

    }

    @Override
    public void endState() {

    }

    @Override
    public void setMachine(StateMachine machine) {
        this.machine = machine;
    }
}
