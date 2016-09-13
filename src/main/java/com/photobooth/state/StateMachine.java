package com.photobooth.state;


import java.util.Iterator;
import java.util.List;

public class StateMachine {
    private List<StateInterface> states;
    private Iterator<StateInterface> currentStateIterator;
    private StateInterface currentState;

    public StateMachine(List<StateInterface> states) {
        this.states = states;
        currentStateIterator = states.iterator();
        nextState();
    }

    public void nextState(){
        if(currentStateIterator.hasNext()) {
            currentState = currentStateIterator.next();
            currentState.setMachine(this);
            startState();
        }else {
            reset();
        }

    }

    public void startState(){
        currentState.init();
    }

    public void reset(){
        currentStateIterator = states.iterator();
        nextState();
    }
}
