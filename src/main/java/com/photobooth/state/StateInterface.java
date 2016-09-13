package com.photobooth.state;

public interface StateInterface {

    void init();
    void onClose();
    void endState();
    void setMachine(StateMachine machine);

}
