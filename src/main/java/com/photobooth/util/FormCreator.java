package com.photobooth.util;


import com.photobooth.model.StateDef;
import com.photobooth.model.StateType;
import com.photobooth.navigator.Navigator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.CheckComboBox;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class FormCreator {

    private static final ObservableList<StateType> stateTypes = FXCollections.observableArrayList(
            new StateType("Animacja zachęty", Navigator.ENCOURAGMENT_VIEW, true, false),
            new StateType("Robienie fotki", Navigator.TAKE_PHOTO_VIEW, true, false),
            new StateType("Animacja jednorazowa", Navigator.PLAY_ONCE_VIEW, true, false),
            new StateType("Galeria", Navigator.GALLERY_VIEW, false, false),
            new StateType("Szablon", Navigator.TEMPLATE_EDITOR_VIEW, false, false),
            new StateType("Ekran opcji koncowych", Navigator.END_OPTIONS_VIEW, false, true)
    );


    public static VBox createStateEditorFormFromConfig(StateFlowConfiguration configuration) {
        ObservableList<GridPane> formRows = FXCollections.observableArrayList();
        VBox formContainer = new VBox();
        configuration.getStates()
                .forEach(stateDef -> formRows.add(createFormRow(stateDef, formRows, formContainer)));

        //return formRows;
        return formContainer;
    }

    private static GridPane createFormRow(StateDef stateDef, ObservableList<GridPane> formRows, VBox formContainer) {
        Configuration configuration = ConfigurationUtil.initConfiguration();

        GridPane formRowContainer = new GridPane();
        ColumnConstraints stateTypeColumnConstraints = new ColumnConstraints(200);
        ColumnConstraints animTemplColumnConstraints = new ColumnConstraints(330);
        ColumnConstraints addBtnColumnConstraints = new ColumnConstraints(75);
        ColumnConstraints delBtnColumnConstraints = new ColumnConstraints(75);
        formRowContainer.getColumnConstraints().addAll(stateTypeColumnConstraints, animTemplColumnConstraints,
                addBtnColumnConstraints, delBtnColumnConstraints);

        // cmbStateType
        ComboBox<StateType> stateTypesComboBox = new ComboBox<>();
        stateTypesComboBox.setItems(stateTypes);
        stateTypesComboBox.getSelectionModel().select(getStateTypeIndex(stateDef.getLabel()));
        formRowContainer.add(stateTypesComboBox, 0, 0);

        stateTypesComboBox.setOnAction(actionEvent -> {
            StateType selected = stateTypesComboBox.getSelectionModel().getSelectedItem();
            if (formRowContainer.getChildren().size() == 4 ) {
                System.out.println("Jest cos do usuniecia");
                int counter = 0;
                for (Node node : formRowContainer.getChildren()) {
                    if (GridPane.getColumnIndex(node) == 1 && GridPane.getRowIndex(node) == 0) {
                        formRowContainer.getChildren().remove(counter);
                        break;
                    }
                    else {
                        counter++;
                    }
                }
            }

            if (selected.shouldContainTemplate()) {
                ComboBox<String> templateComboBox = new ComboBox<>();
                templateComboBox.setItems(FXCollections.observableArrayList(getListOfMedia(configuration.getTemplatePath())));
                formRowContainer.add(templateComboBox, 1, 0);
            }
            else if (selected.shouldContainAnimation()) {
                CheckComboBox<String> animationsComboBox = new CheckComboBox<>();
                animationsComboBox.getItems().setAll(getListOfMedia(configuration.getAnimationPath()));
                formRowContainer.add(animationsComboBox, 1, 0);
            }
        });

        // cmbMedia
        if (stateDef.getAnimationPaths() != null) {
            CheckComboBox<String> animationsComboBox = new CheckComboBox<>();
            String[] animations = getListOfMedia(configuration.getAnimationPath());
            animationsComboBox.getItems().setAll(animations);
            animationsComboBox.getCheckModel().checkIndices(getShouldBeCheckedIndexes(animations, stateDef.getMediaPaths()));
            animationsComboBox.setMaxWidth(310);
            formRowContainer.add(animationsComboBox, 1, 0);
        }

        if (stateDef.getTemplateName() != null) {
            ComboBox<String> templateComboBox = new ComboBox<>();
            String[] templates = getListOfMedia(configuration.getTemplatePath());
            templateComboBox.setItems(FXCollections.observableArrayList(templates));
            Arrays.stream(getShouldBeCheckedIndexes(templates, Arrays.asList(stateDef.getTemplateName())))
                    .forEach(index -> templateComboBox.getSelectionModel().select(index));
            formRowContainer.add(templateComboBox, 1, 0);
        }

        // buttons
        Button addButton = new Button(ResourceBundle.getBundle("locale.locale")
                .getString("BtnAdd.Text"));
        addButton.getStyleClass().setAll("add-button");
        addButton.setOnAction(actionEvent -> {
            int index = formContainer.getChildren().indexOf(formRowContainer);
            GridPane newFormRow = createNotFilledFormRow(formContainer);
            formContainer.getChildren().add(index + 1, newFormRow);
        });
        formRowContainer.add(addButton, 2, 0);

        Button delButton = new Button(ResourceBundle.getBundle("locale.locale")
                .getString("BtnDel.Text"));
        delButton.getStyleClass().setAll("del-button");
        delButton.setOnAction(actionEvent -> {
            int index = formContainer.getChildren().indexOf(formRowContainer);
            formContainer.getChildren().remove(index);
        });
        formRowContainer.add(delButton, 3, 0);

        formContainer.getChildren().add(formRowContainer);
        return formRowContainer;
    }

    private static int[] getShouldBeCheckedIndexes(String[] animations, List<String> mediaPaths) {
        List<Integer> checkedIndexes = new ArrayList<>();
        for (String mediaPath : mediaPaths) {
            int index = 0;
            for (String animation : animations) {
                if (mediaPath.contains(animation))
                    checkedIndexes.add(index);
                index++;
            }
        }
        return checkedIndexes.stream().mapToInt(Integer::intValue).toArray();
    }

    private static int getStateTypeIndex(String label) {
        int index = 0;
        for (StateType stateType : stateTypes) {
            if (stateType.getLabel().equals(label))
                return index;
            index++;
        }

        return -1;
    }

    private static String[] getListOfMedia(String pathToMedia) {
        String[] media = new File(pathToMedia).list();

        return media == null ? new String[] {} : media;
    }

    public static GridPane createNotFilledFormRow(VBox formContainer) {
        Configuration configuration = ConfigurationUtil.initConfiguration();

        GridPane formRowContainer = new GridPane();
        ColumnConstraints stateTypeColumnConstraints = new ColumnConstraints(200);
        ColumnConstraints animTemplColumnConstraints = new ColumnConstraints(330);
        ColumnConstraints addBtnColumnConstraints = new ColumnConstraints(75);
        ColumnConstraints delBtnColumnConstraints = new ColumnConstraints(75);
        formRowContainer.getColumnConstraints().addAll(stateTypeColumnConstraints, animTemplColumnConstraints,
                addBtnColumnConstraints, delBtnColumnConstraints);

        // cmbStateType
        ComboBox<StateType> stateTypesComboBox = new ComboBox<>();
        stateTypesComboBox.setItems(stateTypes);
        formRowContainer.add(stateTypesComboBox, 0, 0);

        stateTypesComboBox.setOnAction(actionEvent -> {
            StateType selected = stateTypesComboBox.getSelectionModel().getSelectedItem();

            // cmbMedia
            if (selected.shouldContainAnimation()) {
                CheckComboBox<String> animationsComboBox = new CheckComboBox<>();
                String[] animations = getListOfMedia(configuration.getAnimationPath());
                animationsComboBox.getItems().setAll(animations);
                animationsComboBox.setMaxWidth(310);
                formRowContainer.add(animationsComboBox, 1, 0);
            }

            if (selected.shouldContainTemplate()) {
                ComboBox<String> templateComboBox = new ComboBox<>();
                String[] templates = getListOfMedia(configuration.getTemplatePath());
                templateComboBox.setItems(FXCollections.observableArrayList(templates));
                formRowContainer.add(templateComboBox, 1, 0);
            }
        });

        // buttons
        Button addButton = new Button(ResourceBundle.getBundle("locale.locale")
                .getString("BtnAdd.Text"));
        addButton.getStyleClass().setAll("add-button");
        addButton.setOnAction(actionEvent -> {
            int index = formContainer.getChildren().indexOf(formRowContainer);
            GridPane newFormRow = createNotFilledFormRow(formContainer);
            formContainer.getChildren().add(index + 1, newFormRow);
        });
        formRowContainer.add(addButton, 2, 0);

        Button delButton = new Button(ResourceBundle.getBundle("locale.locale")
                .getString("BtnDel.Text"));
        delButton.getStyleClass().setAll("del-button");
        delButton.setOnAction(actionEvent -> {
            int index = formContainer.getChildren().indexOf(formRowContainer);
            formContainer.getChildren().remove(index);
        });
        formRowContainer.add(delButton, 3, 0);

        return formRowContainer;
    }
}
