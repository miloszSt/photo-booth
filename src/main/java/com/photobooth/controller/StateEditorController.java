package com.photobooth.controller;

import com.photobooth.model.StateDef;
import com.photobooth.model.StateType;
import com.photobooth.navigator.Navigator;
import com.photobooth.util.Configuration;
import com.photobooth.util.ConfigurationUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.CheckComboBox;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author mst
 */
public class StateEditorController implements Initializable {

    private final ObservableList<StateType> stateTypes = FXCollections.observableArrayList(
            new StateType("Animacja zachety", Navigator.ENCOURAGMENT_VIEW, true, false),
            new StateType("Robienie fotki", Navigator.TAKE_PHOTO_VIEW, true, false),
            new StateType("Animacja jednorazowa", Navigator.PLAY_ONCE_VIEW, true, false),
            new StateType("Galeria", Navigator.GALLERY_VIEW, false, false),
            new StateType("Szablon", Navigator.TEMPLATE_EDITOR_VIEW, false, false),
            new StateType("Ekran opcji koncowych", Navigator.END_OPTIONS_VIEW, false, true) // ewentualnie można dodać animacje na dowidzenia
    );
    @FXML
    BorderPane stateEditorContainer;

    private VBox formContainer;

    //private List<HBox> formRows = new ArrayList<>();
    private List<GridPane> formRows = new ArrayList<>();

    private List<StateDef> customStates = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stateEditorContainer.setPadding(new Insets(15, 10, 15, 10));
        formContainer = new VBox();
        formContainer.setSpacing(15);
        formContainer.getChildren().add(createAddStateForm());
        // create default field for first state
        stateEditorContainer.setCenter(formContainer);
        //stateEditorContainer.setBottom(createSubmitButtonsContainer());
    }

    private GridPane createAddStateForm() {
        //HBox formRowContainer = new HBox();
        GridPane formRowContainer = new GridPane();
        //formRowContainer.setSpacing(12);
        formRowContainer.setAlignment(Pos.CENTER);

        // typ
        ComboBox<StateType> stateTypesComboBox = new ComboBox<>();
        stateTypesComboBox.setItems(stateTypes);
        stateTypesComboBox.setOnAction(actionEvent -> {
            StateType selected = stateTypesComboBox.getSelectionModel().getSelectedItem();
            if (selected.shouldContainTemplate()) {
                ComboBox<String> templateComboBox = new ComboBox<>();
                templateComboBox.setItems(FXCollections.observableArrayList(getListOfMedia(selected)));
                //formRowContainer.getChildren().add(templateComboBox);
                formRowContainer.add(templateComboBox, 1, 0);
            }
            else if (selected.shouldContainAnimation()) {
                CheckComboBox<String> animationsComboBox = new CheckComboBox<>();
                animationsComboBox.getItems().setAll(getListOfMedia(selected));
                //formRowContainer.getChildren().add(animationsComboBox);
                formRowContainer.add(animationsComboBox, 1, 0);
            }
            addBtnNewRow(formRowContainer);
        });
        //formRowContainer.getChildren().add(stateTypesComboBox);
        formRowContainer.add(stateTypesComboBox, 0, 0);


        // przycisk 'Dodaj'
        //formRowContainer.getChildren().add(addButton);
        // tutaj jakis gnoj
        formRows.add(formRowContainer);

        return formRowContainer;
    }

    private void addBtnNewRow(GridPane formRowContainer) {
        Button addButton = new Button();
        addButton.setText("Dodaj Stan");
        addButton.getStyleClass().setAll("add-button");
        addButton.setOnAction(actionEvent ->  {
            /*if (hasFormNotSelectedValues(stateTypesComboBox, animationsComboBox)) {
                showNotSelectedAlert();
            } else {*/
            formContainer.getChildren().add(createAddStateForm());
            addButton.setText("Usun");
            addButton.getStyleClass().setAll("del-button");
            addButton.setOnAction(removeActionEvent -> {
                formContainer.getChildren().remove(formRowContainer);
                formRows.remove(formRowContainer);
            });
            //}
        });
        formRowContainer.add(addButton, 2, 0);
    }

    /**
     * Load list of media names which will be displayed in combobox according to selected {@link StateType}.
     * If state is connected with view presented animation, list of available animations will be loaded. The same with
     * state connected with presenting template.
     *
     * @param selectedStateType selected state type
     * @return list of media names
     */
    private String[] getListOfMedia(StateType selectedStateType) {
        Configuration configuration = ConfigurationUtil.initConfiguration();
        String pathToMedia = "";
        String[] media = null;
        if (selectedStateType.shouldContainAnimation()) {
            pathToMedia = configuration.getAnimationPath();
            media = new File(pathToMedia).list();
        } else if (selectedStateType.shouldContainTemplate()) {
            pathToMedia = configuration.getTemplatePath();


            File[] files = new File(pathToMedia).listFiles(File::isDirectory);
            media = Arrays.stream(files).map(file -> file.getAbsolutePath()).toArray(size -> new String[size]);


        }


        return media == null ? new String[] {} : media;
    }

    /**
     * Check if there is some not selected values in combobox and checkcombobox.
     *
     * @param typeComboBox combobox with view (state) types
     * @param animationComboBox combobox with animations
     * @return true/false
     */
    private boolean hasFormNotSelectedValues(ComboBox<StateType> typeComboBox, CheckComboBox<String> animationComboBox) {
        return (typeComboBox.getValue() == null && typeComboBox.getValue() == null)
                || (animationComboBox.getCheckModel().getCheckedItems() == null
                && typeComboBox.getValue().shouldContainAnimation());
    }

    /**
     * Check if there is some not selected values in comboboxes.
     *
     * @param typeComboBox combobox with view (state) types
     * @param animationComboBox combobox with animations
     * @return true/false
     */
    private boolean hasFormNotSelectedValues(ComboBox<StateType> typeComboBox, ComboBox<String> animationComboBox) {
        return (typeComboBox.getValue() == null && typeComboBox.getValue() == null)
                || (animationComboBox.getValue() == null
                && typeComboBox.getValue().shouldContainAnimation());
    }

    private void showNotSelectedAlert() {
        Alert warnAlert = new Alert(Alert.AlertType.WARNING);
        warnAlert.setContentText(ResourceBundle.getBundle("locale.locale")
                .getString("WarnAlert.ContentText"));
        warnAlert.showAndWait();
    }

     public void handleCancel() {
         Navigator.nextState();
     }

    public void handleSave() {
        if(isFormValid()) {
            saveStatesDefinitions();
            Navigator.nextState();
        } else {
            showNotSelectedAlert();
        }
    }

    private Node createSubmitButtonsContainer() {
        // przycisk 'Anuluj'
        Button cancelButton = new Button();
        cancelButton.setText("Anuluj");
        cancelButton.getStyleClass().setAll("cancel-button");
        cancelButton.setOnAction(actionEvent -> Navigator.nextState());
        // przycisk 'Zapisz'
        Button saveButton = new Button();
        saveButton.setText("Zapisz");
        saveButton.getStyleClass().setAll("save-button");
        saveButton.setOnAction(actionEvent -> {
            if (isFormValid()) {
                saveStatesDefinitions();
                Navigator.nextState();
            } else {
                showNotSelectedAlert();
            }
        });

        HBox submitContainer = new HBox();
        submitContainer.setPadding(new Insets(15, 0, 15, 0));
        submitContainer.setSpacing(12);
        submitContainer.setAlignment(Pos.CENTER);
        submitContainer.getChildren().addAll(cancelButton, saveButton);
        return submitContainer;
    }

    private boolean isFormValid() {
        boolean isValid = true;
        //for (HBox formRow : formRows) {
        for (GridPane formRow : formRows) {
            ComboBox<StateType> stateTypeComboBox = (ComboBox) formRow.getChildren().get(0);
            StateType stateType = stateTypeComboBox.getValue();

            if (stateType.shouldContainAnimation()) {
                CheckComboBox<String> animationComboBox = (CheckComboBox) formRow.getChildren().get(1);
                isValid = isValid && !hasFormNotSelectedValues(stateTypeComboBox, animationComboBox);
            } else if (stateType.shouldContainTemplate()) {
                ComboBox<String> templateComboBox = (ComboBox) formRow.getChildren().get(1);
                isValid = isValid && !hasFormNotSelectedValues(stateTypeComboBox, templateComboBox);
            }
        }

        return isValid;
    }

    private void saveStatesDefinitions() {
        formRows.forEach(formRow -> {
            StateDef stateDefinition = new StateDef();
            Node stateTypeComboBox = formRow.getChildren().get(0);
            saveLabelAndFxmlPath(stateTypeComboBox, stateDefinition);
            saveAnimationPath(formRow.getChildren().get(1), stateTypeComboBox, stateDefinition);
            customStates.add(stateDefinition);
        });

        Navigator.setCustomStates(customStates);
    }

    private void saveLabelAndFxmlPath(Node node, StateDef stateDefinition) {
        ComboBox<StateType> comboBox = (ComboBox) node;
        StateType selected = comboBox.getValue();
        stateDefinition.setLabel(selected.getLabel());
        stateDefinition.setFxmlViewPath(selected.getFxmlViewPath());
    }

    private void saveAnimationPath(Node node, Node stateTypeComboBox, StateDef stateDefinition) {
        StateType stateType = ((ComboBox<StateType>)stateTypeComboBox).getValue();
        if (stateType.shouldContainAnimation()) {
            CheckComboBox<String> comboBox = (CheckComboBox) node;
            List<String> selected = comboBox.getCheckModel().getCheckedItems();
            if (selected != null) {
                List<String> animations = new ArrayList<>();
                String animationPath = ConfigurationUtil.initConfiguration().getAnimationPath();

                for (String animation : selected) {
                    animations.add(animationPath + animation);
                }
                stateDefinition.setAnimationPaths(animations);
            }
        } else if (stateType.shouldContainTemplate()) {
            ComboBox<String> comboBox = (ComboBox) node;
            String selected = comboBox.getValue();
            if (selected != null) {
                stateDefinition.setTemplateName(selected);
            }
        }
    }
}
