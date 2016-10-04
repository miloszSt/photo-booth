package com.photobooth.templateEdytor.panels;

import com.photobooth.templateEdytor.TemplateMainView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

public class TopPanel extends HBox{
    private final TemplateMainView templateMainView;

    public TopPanel(TemplateMainView templateMainView) {
        super(5);
        this.templateMainView = templateMainView;

        setMinWidth(900);
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
        getChildren().addAll(newTemplateButton, templates, copyTemplateButton, deleteTemplateButton,
                reverTemplateButton, printTemplateButton, importTemplateButton, exportTemplateButton,
                browseTemplateButton, undoTemplateButton, redoTemplateButton,button );
    }
}
