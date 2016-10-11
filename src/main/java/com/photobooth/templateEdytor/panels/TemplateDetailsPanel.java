package com.photobooth.templateEdytor.panels;

import com.photobooth.templateEdytor.TemplateMainView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TemplateDetailsPanel extends VBox{

    private final TemplateMainView templateMainView;

    private final TextField nameInputControl;

    public TemplateDetailsPanel(final TemplateMainView templateMainView) {
        super();
        this.templateMainView = templateMainView;

        Label templateDetails = new Label("TEMPLATE DETAILS");
        Label name = new Label("Name");
        nameInputControl = new TextField(templateMainView.getTemplateName());
        nameInputControl.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                templateMainView.setTemplateName(newValue);
            }
        });

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

        setSpacing(5);
        setBorder(templateMainView.getBlackSolidBorder());
        setPadding(new Insets(10,10,10,10));




        getChildren().addAll(templateDetails, name, nameInputControl, paperSizeLabel,
                paperSizeComboBox, resolutionLabel, resolutionComboBox, widthHeightLabelsBox, widthHeightInputBox,
                orientationLabel, orientationComboBox, backgroundColorLabel, colorPicker);
    }
}
