package com.photobooth;

import com.photobooth.camera.CameraService;
import com.photobooth.state.StateInterface;
import com.photobooth.state.StateMachine;
import com.photobooth.state.TakePhotoState;
import com.photobooth.state.WaitingForInputAnimationState;
import com.photobooth.view.MainView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Entry point of application
 *
 * @author MS
 */
public class Main {

    private final static CameraService cameraService = new CameraService();

    public static void main(String[] args) throws IOException, InterruptedException {
        MainView mainView = new MainView();

        WaitingForInputAnimationState animation1 = new WaitingForInputAnimationState(mainView, "looped1");
        WaitingForInputAnimationState animation2 = new WaitingForInputAnimationState(mainView, "looped2");

        TakePhotoState takePhotoState = new TakePhotoState(new CameraService(), "testUser", "countDown", mainView);


        ArrayList<StateInterface> stateInterfaces = new ArrayList<>();
        stateInterfaces.add(animation1);
        stateInterfaces.add(takePhotoState);
        stateInterfaces.add(animation2);
        stateInterfaces.add(takePhotoState);
        stateInterfaces.add(animation1);
        stateInterfaces.add(takePhotoState);


        StateMachine stateMachine = new StateMachine(stateInterfaces);

//        AppController controller = new AppController();
//        controller.startApp();

        stateMachine.startState();
    }
}
