package com.photobooth.controller;

import com.photobooth.model.StateDef;
import com.photobooth.model.StateType;
import com.photobooth.navigator.Navigator;
import com.photobooth.util.FileUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author mst
 */
public class StateEditorController implements Initializable {

    private static final String ANIMATIONS_PATH = "animations";

    private final ObservableList<StateType> stateTypes = FXCollections.observableArrayList(
            new StateType("Animacja zachety", Navigator.ENCOURAGMENT_VIEW, true),
            new StateType("Robienie fotki", Navigator.TAKE_PHOTO_VIEW, true),
            new StateType("Galeria", Navigator.GALLERY_VIEW, false),
            new StateType("Szablon", Navigator.TEMPLATE_EDITOR_VIEW, false),
            new StateType("Ekran opcji końcowych", Navigator.END_OPTIONS_VIEW, false) // ewentualnie można dodać animacje na dowidzenia
    );
    @FXML
    BorderPane stateEditorContainer;

    private VBox formContainer;

    private List<HBox> formRows = new ArrayList<>();

    private List<StateDef> customStates = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stateEditorContainer.setPadding(new Insets(10, 10, 10, 10));
        formContainer = new VBox();
        formContainer.getChildren().add(createAddStateForm());
        // create default field for first state
        stateEditorContainer.setCenter(formContainer);
        stateEditorContainer.setBottom(createSubmitButtonsContainer());
    }

    private HBox createAddStateForm() {
        HBox formRowContainer = new HBox();
        // animacja
        ComboBox<String> animationsComboBox = new ComboBox<>();
        String[] animations = FileUtils.load(ANIMATIONS_PATH).list();
        animationsComboBox.getItems().addAll(animations);

        // typ
        ComboBox<StateType> stateTypesComboBox = new ComboBox<>();
        stateTypesComboBox.setItems(stateTypes);
        stateTypesComboBox.setOnAction(actionEvent -> {
            StateType selected = stateTypesComboBox.getSelectionModel().getSelectedItem();
            if (!selected.shouldContainAnimation())
                animationsComboBox.setDisable(true);
            else
                animationsComboBox.setDisable(false);
        });
        formRowContainer.getChildren().add(stateTypesComboBox);
        formRowContainer.getChildren().add(animationsComboBox);

        // przycisk 'Dodaj'
        Button addButton = new Button();
        addButton.setText("Dodaj Stan");
        addButton.setOnAction(actionEvent ->  {
            if ((animationsComboBox.getValue() == null
                    && stateTypesComboBox.getValue() == null) || (animationsComboBox.getValue() == null
                    && stateTypesComboBox.getValue().shouldContainAnimation())) {
                showAnimationNotSelectedAlert();
            } else {
                formContainer.getChildren().add(createAddStateForm());
                addButton.setText("Usun");
                addButton.setOnAction(removeActionEvent -> {
                    formContainer.getChildren().remove(formRowContainer);
                    formRows.remove(formRowContainer);
                });
            }
        });
        formRowContainer.getChildren().add(addButton);
        formRows.add(formRowContainer);

        return formRowContainer;
    }

    private void showAnimationNotSelectedAlert() {
        Alert warnAlert = new Alert(Alert.AlertType.WARNING);
        warnAlert.setContentText(ResourceBundle.getBundle("locale.locale")
                .getString("WarnAlert.ContentText"));
        warnAlert.showAndWait();
    }

    private Node createSubmitButtonsContainer() {
        // przycisk 'Anuluj'
        Button cancelButton = new Button();
        cancelButton.setText("Anuluj");
        cancelButton.setOnAction(actionEvent -> Navigator.nextState());
        // przycisk 'Zapisz'
        Button saveButton = new Button();
        saveButton.setText("Zapisz");
        saveButton.setOnAction(actionEvent -> {
            if (validateForm()) {
                saveStatesDefinitions();
                Navigator.nextState();
            } else {
                // alert
            }
        });

        HBox submitContainer = new HBox();
        submitContainer.getChildren().addAll(cancelButton, saveButton);
        return submitContainer;
    }

    private boolean validateForm() {
        // TODO mst implement
        return true;
    }

    private void saveStatesDefinitions() {
        formRows.forEach(formRow -> {
            StateDef stateDefinition = new StateDef();
            saveLabelAndFxmlPath(formRow.getChildren().get(0), stateDefinition);
            saveAnimationPath(formRow.getChildren().get(1), stateDefinition);
            customStates.add(stateDefinition);
        });

        Navigator.setCustomStates(customStates);
        Navigator.nextState();
    }

    private void saveLabelAndFxmlPath(Node node, StateDef stateDefinition) {
        ComboBox<StateType> comboBox = (ComboBox) node;
        StateType selected = comboBox.getValue();
        stateDefinition.setLabel(selected.getLabel());
        stateDefinition.setFxmlViewPath(selected.getFxmlViewPath());
    }

    private void saveAnimationPath(Node node, StateDef stateDefinition) {
        ComboBox<String> comboBox = (ComboBox) node;
        String selected = comboBox.getValue();
        stateDefinition.setAnimationPath(ANIMATIONS_PATH + "/" + selected);
    }
}
