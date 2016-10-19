package com.photobooth.templateEdytor.panels;

import com.photobooth.templateEdytor.TemplateMainView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TemplateDetailsPanel extends VBox{

    private final TemplateMainView templateMainView;

    private final TextField nameInputControl;
    private final ComboBox<Paper> paperSizeComboBox;
    private final ComboBox<PageOrientation> orientationComboBox;

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



//        Label resolutionLabel = new Label("Resolution");
//        ObservableList<String> resolutionOptions =
//                FXCollections.observableArrayList(
//                        "800x700",
//                        "100x100",
//                        "100x120"
//                );
//        ComboBox<String> resolutionComboBox = new ComboBox<>(resolutionOptions);

        HBox widthHeightLabelsBox = new HBox(new Label("Height"), new Label("Width"));
        widthHeightLabelsBox.setSpacing(30);
        TextField width = new TextField();
        TextField height = new TextField();

        HBox widthHeightInputBox = new HBox(height, width);

        Label paperSizeLabel = new Label("Paper size");
        ObservableList<Paper> paperSizeOptions =
                FXCollections.observableArrayList(Paper.A0,Paper.A1, Paper.A2, Paper.A3,
                        Paper.A4, Paper.A5, Paper.JIS_B4, Paper.JIS_B5, Paper.JIS_B6
                );

        paperSizeComboBox = new ComboBox<>(paperSizeOptions);

        paperSizeComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Paper>() {
            @Override
            public void changed(ObservableValue<? extends Paper> observable, Paper oldValue, Paper newValue) {
                templateMainView.getCenterPanel().setPageSize(newValue);
                width.setText(Integer.toString(templateMainView.getCenterPanel().getPageHeight()));
                height.setText(Integer.toString(templateMainView.getCenterPanel().getPageWidth()));
            }
        });


        paperSizeComboBox.getSelectionModel().select(Paper.A4);
        Label orientationLabel = new Label("Orientation");
        ObservableList<PageOrientation> orientationOptions =
                FXCollections.observableArrayList(
                        PageOrientation.PORTRAIT, PageOrientation.LANDSCAPE
                );

        orientationComboBox = new ComboBox<>(orientationOptions);
        orientationComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PageOrientation>() {
            @Override
            public void changed(ObservableValue<? extends PageOrientation> observable, PageOrientation oldValue, PageOrientation newValue) {
                templateMainView.getCenterPanel().setOrientation(newValue);
                width.setText(Integer.toString(templateMainView.getCenterPanel().getPageHeight()));
                height.setText(Integer.toString(templateMainView.getCenterPanel().getPageWidth()));
            }
        });

        orientationComboBox.getSelectionModel().select(PageOrientation.PORTRAIT);
        Label backgroundColorLabel = new Label("Backgroud color");
        ColorPicker colorPicker = new ColorPicker();

        setSpacing(5);
        setBorder(templateMainView.getBlackSolidBorder());
        setPadding(new Insets(10,10,10,10));




        getChildren().addAll(templateDetails, name, nameInputControl, paperSizeLabel,
                paperSizeComboBox, widthHeightLabelsBox, widthHeightInputBox,
                orientationLabel, orientationComboBox, backgroundColorLabel, colorPicker);
    }
}
