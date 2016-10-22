package com.photobooth.view;

import com.photobooth.state.StateInterface;
import com.photobooth.state.StateMachine;
import com.photobooth.state.TakePhotoState;
import com.photobooth.state.WaitingForInputAnimationState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;
import java.util.List;

public class StateEditorView extends JFrame {

    private final StateMachine machine;
    private List<JPanel> states = new ArrayList<>();


    public StateEditorView(StateMachine machine) throws HeadlessException {
        this.machine = machine;
        createView();
    }

    private void createView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        setSize(new Dimension(450, 700));
        setVisible(true);
        setLayout(new GridLayout(20, 1));
    }

    private void initComponents() {
        JButton addRowButton = new JButton("Dodaj stan");
        addRowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStateRow();
            }
        });
        add(addRowButton);

        JButton saveAndStart = new JButton("Zapisz i zacznij prace");
        saveAndStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveStates();
            }
        });
        add(saveAndStart);
        revalidate();
    }

    private void saveStates() {
        Component[] components = ((JRootPane) getComponents()[0]).getComponents();
        ArrayList<StateInterface> stateInterfaces = new ArrayList<>();

        MainView mainView = new MainView();

        for (JPanel state : states) {

            String selectedState = ((JComboBox<String>) state.getComponent(0)).getSelectedItem().toString();
            String selectedAnimation = ((JComboBox<String>) state.getComponent(1)).getSelectedItem().toString();

            switch (selectedState) {
                case "Animacja zachęty":
                    WaitingForInputAnimationState waitingForInputAnimationState = new WaitingForInputAnimationState(mainView, selectedAnimation);
                    stateInterfaces.add(waitingForInputAnimationState);
                    break;
                case "Robienie fotki":
                    break;
                case "Szablon":
                    break;
                case "Ekran opcji końcowych":
                    break;
            }
        }

        machine.setStates(stateInterfaces);

        setVisible(false);

        machine.reset();
    }

    private void addStateRow() {
        JPanel stateRow = new JPanel();
        stateRow.setVisible(true);
        stateRow.setSize(300, 20);

        String[] stateType = {"Animacja zachęty", "Robienie fotki", "Szablon", "Ekran opcji końcowych"};
        JComboBox<String> stateTypeCombobox = new JComboBox<String>(stateType);
        stateRow.add(stateTypeCombobox);

        File f = new File("/");
        String[] animations = f.list();

        JComboBox<String> animationComboBox = new JComboBox<String>(animations);
        stateRow.add(animationComboBox);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                remove(stateRow);
            }
        });
        stateRow.add(deleteButton);

        add(stateRow);

        states.add(stateRow);
        revalidate();
    }


}
