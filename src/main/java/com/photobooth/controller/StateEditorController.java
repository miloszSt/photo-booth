package com.photobooth.controller;

import com.photobooth.config.Config;
import com.photobooth.controller.spec.StateFlowConfigurationInitializable;
import com.photobooth.model.Media;
import com.photobooth.model.StateDef;
import com.photobooth.model.StateType;
import com.photobooth.navigator.Navigator;
import com.photobooth.util.Configuration;
import com.photobooth.util.ConfigurationUtil;
import com.photobooth.util.FormCreator;
import com.photobooth.util.StateFlowConfiguration;
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
import javafx.scene.layout.*;
import org.apache.log4j.Logger;
import org.controlsfx.control.CheckComboBox;

import javax.xml.bind.JAXB;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author mst
 */
public class StateEditorController implements Initializable, StateFlowConfigurationInitializable {

    private static final Logger logger = Logger.getLogger(StateEditorController.class);

    private final ObservableList<StateType> stateTypes = FXCollections.observableArrayList(
            new StateType("Animacja zachety", Navigator.ENCOURAGMENT_VIEW, true, false),
            new StateType("Robienie fotki", Navigator.TAKE_PHOTO_VIEW, true, false),
            new StateType("Animacja jednorazowa", Navigator.PLAY_ONCE_VIEW, true, false),
            new StateType("Galeria", Navigator.GALLERY_VIEW, false, false),
            new StateType("Szablon", Navigator.TEMPLATE_EDITOR_VIEW, false, false),
            new StateType("Ekran opcji koncowych", Navigator.END_OPTIONS_VIEW, false, true)
    );

    @FXML
    BorderPane stateEditorContainer;

    private VBox formContainer;

    private List<GridPane> formRows = new ArrayList<>();

    private List<StateDef> customStates = new ArrayList<>();

    private Configuration configuration;

    private StateFlowConfiguration stateFlowConfiguration;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configuration = ConfigurationUtil.initConfiguration();
        stateEditorContainer.setPadding(new Insets(15, 10, 15, 10));
        formContainer = new VBox();
        formContainer.setSpacing(15);
        //formContainer.getChildren().add(createAddStateForm());
        formContainer.getChildren().add(FormCreator.createNotFilledFormRow(formContainer));
        //formContainer.getChildren().addAll(FormCreator.createStateEditorFormFromConfig())

        // create default field for first state
        stateEditorContainer.setCenter(formContainer);
    }

    @Override
    public void setStateFlowConfiguration(StateFlowConfiguration stateFlowConfiguration) {
        this.stateFlowConfiguration = stateFlowConfiguration;
        //ObservableList<GridPane> createdFormRows = FormCreator.createStateEditorFormFromConfig(stateFlowConfiguration);
        //formRows.addAll(createdFormRows);
        //formContainer.getChildren().setAll(formRows);
        VBox createdForm = FormCreator.createStateEditorFormFromConfig(stateFlowConfiguration);
        createdForm.setSpacing(15);
        formContainer = createdForm;
        // create default field for first state
        stateEditorContainer.setCenter(formContainer);
    }

    private GridPane createAddStateForm() {
        GridPane formRowContainer = new GridPane();
        ColumnConstraints stateTypeColumnConstraints = new ColumnConstraints(230);
        ColumnConstraints animTemplColumnConstraints = new ColumnConstraints(320);
        ColumnConstraints addRemBtnColumnConstraints = new ColumnConstraints(100);
        formRowContainer.getColumnConstraints().addAll(stateTypeColumnConstraints, animTemplColumnConstraints, addRemBtnColumnConstraints);

        // typ
        ComboBox<StateType> stateTypesComboBox = new ComboBox<>();
        stateTypesComboBox.setItems(stateTypes);
        addBtnNewRow(formRowContainer);

        stateTypesComboBox.setOnAction(actionEvent -> {
            StateType selected = stateTypesComboBox.getSelectionModel().getSelectedItem();
            if (formRowContainer.getChildren().size() == 3 ) {
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
                templateComboBox.setItems(FXCollections.observableArrayList(getListOfMedia(selected)));
                formRowContainer.add(templateComboBox, 1, 0);
            }
            else if (selected.shouldContainAnimation()) {
                CheckComboBox<String> animationsComboBox = new CheckComboBox<>();
                animationsComboBox.getItems().setAll(getListOfMedia(selected));
                formRowContainer.add(animationsComboBox, 1, 0);
            }
        });

        formRowContainer.add(stateTypesComboBox, 0, 0);
        formRows.add(formRowContainer);

        return formRowContainer;
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

    private void addBtnNewRow(GridPane formRowContainer) {
        Button addButton = new Button();
        addButton.setText("Dodaj Stan");
        addButton.getStyleClass().setAll("add-button");
        addButton.setOnAction(actionEvent ->  {
            if (hasFormNotSelectedValues(formRowContainer)) {
                showNotSelectedAlert();
            } else {
                formContainer.getChildren().add(createAddStateForm());
                addButton.setText("Usun");
                addButton.getStyleClass().setAll("del-button");
                addButton.setOnAction(removeActionEvent -> {
                    formContainer.getChildren().remove(formRowContainer);
                    formRows.remove(formRowContainer);
                });
            }
        });
        formRowContainer.add(addButton, 2, 0);
    }

    private boolean hasFormNotSelectedValues(GridPane formRowContainer) {
        ComboBox<StateType> stateTypeComboBox = (ComboBox) getNodeFromGridPane(formRowContainer, 0, 0);
        StateType stateType = stateTypeComboBox.getValue();

        if (stateType.shouldContainAnimation()) {
            CheckComboBox<String> animationComboBox = (CheckComboBox) getNodeFromGridPane(formRowContainer, 1, 0);
            return hasFormNotSelectedValues(stateTypeComboBox, animationComboBox);
        } else if (stateType.shouldContainTemplate()) {
            ComboBox<String> templateComboBox = (ComboBox) getNodeFromGridPane(formRowContainer, 1, 0);
            return hasFormNotSelectedValues(stateTypeComboBox, templateComboBox);
        }

        return false;
    }

    /**
     * Check if there is some not selected values in combobox and checkcombobox.
     *
     * @param typeComboBox combobox with view (state) types
     * @param animationComboBox combobox with animations
     * @return true/false
     */
    private boolean hasFormNotSelectedValues(ComboBox<StateType> typeComboBox, CheckComboBox<String> animationComboBox) {
        return typeComboBox.getValue() == null
                || ( animationComboBox.getCheckModel().getCheckedItems().size() == 0
                && typeComboBox.getValue().shouldContainAnimation() );
    }

    /**
     * Check if there is some not selected values in comboboxes.
     *
     * @param typeComboBox combobox with view (state) types
     * @param animationComboBox combobox with animations
     * @return true/false
     */
    private boolean hasFormNotSelectedValues(ComboBox<StateType> typeComboBox, ComboBox<String> animationComboBox) {
        return typeComboBox.getValue() == null
                || ( animationComboBox.getValue() == null
                && typeComboBox.getValue().shouldContainTemplate() );
    }

    private void showNotSelectedAlert() {
        Alert warnAlert = new Alert(Alert.AlertType.WARNING);
        warnAlert.setContentText(ResourceBundle.getBundle("locale.locale")
                .getString("WarnAlert.ContentText"));
        warnAlert.showAndWait();
    }

    /**
     * Handling 'Cancel' button.
     */
     public void handleCancel() {
         StateFlowConfiguration stateFlowConfiguration = Config.getInstance()
                 .getStateFlowConfiguration();
         if (stateFlowConfiguration == null) {
             Alert confirmationDialog = new Alert(Alert.AlertType.ERROR);
             confirmationDialog.setHeaderText(ResourceBundle.getBundle("locale.locale")
                     .getString("NoStateFlowWarnAlert.HeaderText"));
             confirmationDialog.setContentText(ResourceBundle.getBundle("locale.locale")
                     .getString("NoStateFlowWarnAlert.ContentText"));
             confirmationDialog.show();
         } else {
             Navigator.setCustomStates(stateFlowConfiguration.getStates());
             Navigator.nextState();
         }
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
        cancelButton.setOnAction(actionEvent -> {
            Navigator.nextState();

        });
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
        for (Node formRowContainer : formContainer.getChildren()) {
            GridPane formRow = (GridPane) formRowContainer;
            ComboBox<StateType> stateTypeComboBox = (ComboBox) getNodeFromGridPane(formRow, 0, 0);
            StateType stateType = stateTypeComboBox.getValue();

            if (stateType.shouldContainAnimation()) {
                CheckComboBox<String> animationComboBox = (CheckComboBox) getNodeFromGridPane(formRow, 1, 0);
                isValid = isValid && !hasFormNotSelectedValues(stateTypeComboBox, animationComboBox);
            } else if (stateType.shouldContainTemplate()) {
                ComboBox<String> templateComboBox = (ComboBox) getNodeFromGridPane(formRow, 1, 0);
                isValid = isValid && !hasFormNotSelectedValues(stateTypeComboBox, templateComboBox);
            }
        }

        return isValid;
    }

    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row)
                return node;
        }

        return null;
    }

    private void saveStatesDefinitions(){
        formContainer.getChildren().forEach(formRow -> {
            StateDef stateDefinition = new StateDef();
            Node stateTypeComboBox = getNodeFromGridPane((GridPane) formRow, 0, 0);
            saveLabelAndFxmlPath(stateTypeComboBox, stateDefinition);
            saveAnimationPath(getNodeFromGridPane((GridPane) formRow, 1, 0), stateTypeComboBox, stateDefinition);
            customStates.add(stateDefinition);
        });
        // save stateflow to file
        StateFlowConfiguration stateFlowConfiguration = new StateFlowConfiguration();
        stateFlowConfiguration.setStates(customStates);
        Path configPath = Paths.get("/photoBooth/stateFlows/config.xml");
        File config = null;
        if (Files.exists(configPath)) {
            logger.info("Config file exists and will be overwritten");
            config = configPath.toFile();
        } else {
            try {
                config = Files.createFile(configPath).toFile();
            } catch (IOException e) {
                logger.error("Error creating state flow configuration for path: " + configPath, e);
            }
        }
        JAXB.marshal(stateFlowConfiguration, config);

        configuration.saveStateFlow(customStates);
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
                List<Media> animations = new ArrayList<>();
                String animationPath = ConfigurationUtil.initConfiguration().getAnimationPath();

                for (String animation : selected) {
                    Media media = new Media(animationPath + animation);
                    animations.add(media);
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
