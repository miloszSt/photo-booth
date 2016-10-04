package com.photobooth.templateEdytor.panels;

import com.photobooth.templateEdytor.TemplateMainView;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class InfoSelectedPanel extends VBox {

    private final TemplateMainView templateMainView;

    public InfoSelectedPanel(TemplateMainView templateMainView) {
        super();
        this.templateMainView = templateMainView;

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

        setSpacing(5);
        setBorder(templateMainView.getBlackSolidBorder());


        getChildren().addAll(infoLabel, nameHbox, topTextBox, leftTextBox, widthTextBox,
                heightTextBox, rotationTextBox, orderTextBox);
    }
}
