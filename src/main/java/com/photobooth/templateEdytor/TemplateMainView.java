package com.photobooth.templateEdytor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class TemplateMainView {

    private final Border blackSolidBorder = new Border(new BorderStroke(Paint.valueOf("000"),BorderStrokeStyle.SOLID, null, null));

    public TemplateMainView(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root,1920,1080);

        BorderPane border = new BorderPane();

        HBox hBox = getTopPanel();
        border.setTop(hBox);

        createLeftPanel(border);
        createRightPanel(border);
        root.getChildren().add(border);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createRightPanel(BorderPane border) {
        VBox rightPanel = new VBox();

        VBox infoPanel = getInfoPanel();
        VBox layersPanel = getLayersPanel();
        VBox optionsPanel = getOptionsPanel();

        infoPanel.setSpacing(5);
        infoPanel.setBorder(blackSolidBorder);

        layersPanel.setSpacing(5);
        layersPanel.setBorder(blackSolidBorder);

        optionsPanel.setSpacing(5);
        optionsPanel.setBorder(blackSolidBorder);

        rightPanel.getChildren().addAll(infoPanel, layersPanel, optionsPanel);

        rightPanel.setSpacing(10);

        border.setRight(rightPanel);
    }

    private VBox getOptionsPanel() {
        Label optionsLabel = new Label("OPTIONS");

        ColorPicker fillColorPicker = new ColorPicker();
        HBox fillColorHbox = new HBox(new Label("Fill color"), fillColorPicker);

        ColorPicker strokeColorPicker = new ColorPicker();
        HBox strokeColorHbox = new HBox(new Label("Stroke color"), strokeColorPicker);

        Label strokeThicknessLabel = new Label("Stroke thickness");
        ScrollBar strokeThickness = new ScrollBar();
        strokeThickness.setMin(0);
        strokeThickness.setMax(100);
        strokeThickness.setValue(50);

        CheckBox aspectRatio = new CheckBox();
        HBox aspectRatioBox = new HBox(aspectRatio, new Label("Keep aspect ratio"));

        Label opacityLabel = new Label("Opacity");
        ScrollBar opacity = new ScrollBar();
        opacity.setMin(0);
        opacity.setMax(100);
        opacity.setValue(50);

        return  new VBox(optionsLabel, fillColorHbox, strokeColorHbox, strokeThicknessLabel, strokeThickness, aspectRatioBox, opacityLabel, opacity);
    }

    private VBox getLayersPanel() {
        Label infoLabel = new Label("LAYERS");

        ListView<String> layersList = new ListView<String>();
        ObservableList<String> items =FXCollections.observableArrayList (
                "Single", "Double", "Suite", "Family App");
        layersList.setItems(items);

        Button copyLayer = new Button("Copy layer");
        Button removeLayer = new Button("Remove layer");

        return new VBox(infoLabel, layersList, new HBox(copyLayer, removeLayer));
    }

    private VBox getInfoPanel() {

        Label infoLabel = new Label("INFO");
        TextField nameTextField = new TextField();
        HBox nameHbox = new HBox(new Label("Name"), nameTextField);

        TextField topTextField = new TextField();
        HBox topTextBox = new HBox(new Label("Top"), topTextField);

        TextField leftTextField = new TextField();
        HBox leftTextBox = new HBox(new Label("Left"), leftTextField);

        TextField widthTextField = new TextField();
        HBox widthTextBox = new HBox(new Label("Width"), widthTextField);

        TextField heightTextField = new TextField();
        HBox heightTextBox = new HBox(new Label("Height"), heightTextField);

        TextField rotationTextField = new TextField();
        HBox rotationTextBox = new HBox(new Label("Rotation"), rotationTextField);

        TextField orderTextField = new TextField();
        HBox orderTextBox = new HBox(new Label("Order"), orderTextField);


        return new VBox(infoLabel, nameHbox, topTextBox, leftTextBox, widthTextBox,
                heightTextBox, rotationTextBox, orderTextBox);
    }

    private void createLeftPanel(BorderPane border) {
        VBox leftPanel = new VBox();
        VBox addPanel = getAddBox();

        leftPanel.setSpacing(5);

        addPanel.setSpacing(5);
        addPanel.setBorder(blackSolidBorder);
        addPanel.setPadding(new Insets(10,10,10,10));

        VBox templateDetailsPanel = getvTemplateDetails();

        templateDetailsPanel.setSpacing(5);
        templateDetailsPanel.setBorder(blackSolidBorder);
        templateDetailsPanel.setPadding(new Insets(10,10,10,10));


        leftPanel.setPadding(new Insets(10,0,0,10));
        leftPanel.getChildren().addAll(addPanel, templateDetailsPanel);

        border.setLeft(leftPanel);
    }

    private VBox getvTemplateDetails() {
        VBox templateDetailsPanel = new VBox();

        Label templateDetails = new Label("TEMPLATE DETAILS");
        Label name = new Label("Name");
        TextField nameInputControl= new TextField();

        Label paperSizeLabel = new Label("Paper size");
        ObservableList<String> paperSizeOptions =
                FXCollections.observableArrayList(
                        "4x6",
                        "99x99",
                        "6x9"
                );
        ComboBox<String> paperSizeComboBox = new ComboBox<>(paperSizeOptions);

        Label resolutionLabel = new Label("Resolution");
        ObservableList<String> resolutionOptions =
                FXCollections.observableArrayList(
                        "800x700",
                        "100x100",
                        "100x120"
                );
        ComboBox<String> resolutionComboBox = new ComboBox<>(resolutionOptions);

        HBox widthHeightLabelsBox = new HBox(new Label("Width"), new Label("Height"));
        HBox widthHeightInputBox = new HBox(new TextField(), new TextField());

        Label orientationLabel = new Label("Orientation");
        ObservableList<String> orientationOptions =
                FXCollections.observableArrayList(
                        "Horizontal",
                        "Vertical"
                );
        ComboBox<String> orientationComboBox = new ComboBox<>(orientationOptions);
        Label backgroundColorLabel = new Label("Backgroud color");
        ColorPicker colorPicker = new ColorPicker();

        templateDetailsPanel.getChildren().addAll(templateDetails, name, nameInputControl, paperSizeLabel,
                paperSizeComboBox, resolutionLabel, resolutionComboBox, widthHeightLabelsBox, widthHeightInputBox,
                orientationLabel, orientationComboBox, backgroundColorLabel, colorPicker);
        return templateDetailsPanel;
    }

    private VBox getAddBox() {
        VBox addPanel = new VBox();
        Label addLabel = new Label("ADD");
        Button addImageButton = new Button("addImageButton");
        Button addPhotoButton = new Button("addPhotoButton");
        Button addTextButton = new Button("addTextButton");
        Button addRectangleButton = new Button("addRectangleButton");
        Button addCircleButton = new Button("addCircleButton");
        Button addQrCodeButton = new Button("addQrCodeButton");

        addPanel.getChildren().addAll(addLabel, addImageButton, addPhotoButton, addTextButton,
                addRectangleButton, addCircleButton, addQrCodeButton);

        return addPanel;
    }

    private HBox getTopPanel() {
        HBox hBox = new HBox(5);
        hBox.setMinWidth(900);
        Button newTemplateButton = new Button("New Template");
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Taki",
                        "Sraki",
                        "Owaki"
                );
        ComboBox templates = new ComboBox(options);
        Button copyTemplateButton = new Button("copyTemplateButton");
        Button deleteTemplateButton = new Button("deleteTemplateButton");
        Button reverTemplateButton = new Button("reverTemplateButton");
        Button printTemplateButton = new Button("printTemplateButton");
        Button importTemplateButton = new Button("importTemplateButton");
        Button exportTemplateButton = new Button("exportTemplateButton");
        Button browseTemplateButton = new Button("browseTemplateButton");
        Button undoTemplateButton = new Button("undoTemplateButton");
        Button redoTemplateButton = new Button("redoTemplateButton");

        Button button = new Button("                                                                                                                                        ");
        button.setDisable(true);
        hBox.getChildren().addAll(newTemplateButton, templates, copyTemplateButton, deleteTemplateButton,
                reverTemplateButton, printTemplateButton, importTemplateButton, exportTemplateButton,
                browseTemplateButton, undoTemplateButton, redoTemplateButton,button );
        return hBox;
    }
}

