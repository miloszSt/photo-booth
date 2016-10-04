package com.photobooth.templateEdytor.panels;

import com.photobooth.templateEdytor.TemplateMainView;
import com.photobooth.templateEdytor.elements.TemplateElementInterface;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

public class InfoSelectedPanel extends VBox {

    private final TemplateMainView templateMainView;

    private final TextField nameTextField;
    private final TextField topTextField;
    private final TextField leftTextField;
    private final TextField widthTextField;
    private final TextField heightTextField;
    private final TextField rotationTextField;
    private final TextField orderTextField;

    public InfoSelectedPanel(TemplateMainView templateMainView) {
        super();
        this.templateMainView = templateMainView;

        Label infoLabel = new Label("INFO");

        nameTextField = new TextField();
        topTextField = new TextField();
        leftTextField = new TextField();
        widthTextField = new TextField();
        heightTextField = new TextField();
        rotationTextField = new TextField();
        orderTextField = new TextField();


        GridPane gridPane = new GridPane();


        Label name = new Label("Name");
        Label top = new Label("Top");
        Label left = new Label("Left");
        Label width = new Label("Width");
        Label height = new Label("Height");
        Label rotation = new Label("Rotation");
        Label order = new Label("Order");

        GridPane.setRowIndex(nameTextField, 1);
        GridPane.setRowIndex(topTextField, 2);
        GridPane.setRowIndex(leftTextField, 3);
        GridPane.setRowIndex(widthTextField, 4);
        GridPane.setRowIndex(heightTextField, 5);
        GridPane.setRowIndex(rotationTextField, 6);
        GridPane.setRowIndex(orderTextField, 7);

        GridPane.setRowIndex(name, 1);
        GridPane.setRowIndex(top, 2);
        GridPane.setRowIndex(left, 3);
        GridPane.setRowIndex(width, 4);
        GridPane.setRowIndex(height, 5);
        GridPane.setRowIndex(rotation, 6);
        GridPane.setRowIndex(order, 7);


        GridPane.setColumnIndex(name, 1);
        GridPane.setColumnIndex(top, 1);
        GridPane.setColumnIndex(left, 1);
        GridPane.setColumnIndex(width, 1);
        GridPane.setColumnIndex(height, 1);
        GridPane.setColumnIndex(rotation, 1);
        GridPane.setColumnIndex(order, 1);

        GridPane.setColumnIndex(nameTextField, 2);
        GridPane.setColumnIndex(topTextField, 2);
        GridPane.setColumnIndex(leftTextField, 2);
        GridPane.setColumnIndex(widthTextField, 2);
        GridPane.setColumnIndex(heightTextField, 2);
        GridPane.setColumnIndex(rotationTextField, 2);
        GridPane.setColumnIndex(orderTextField, 2);


        gridPane.getChildren().addAll(name, top, left, width, height, rotation, order, nameTextField, topTextField,
                leftTextField, widthTextField, heightTextField, rotationTextField, orderTextField);
        gridPane.setGridLinesVisible(true);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        setSpacing(5);
        setBorder(templateMainView.getBlackSolidBorder());


        getChildren().addAll(gridPane);
    }

    public void setTemplateElementInterface(TemplateElementInterface templateElementInterface) {
        nameTextField.setText(templateElementInterface.getName());
        topTextField.setText(templateElementInterface.getElementTop());
        leftTextField.setText(templateElementInterface.getElementLeft());
        widthTextField.setText(templateElementInterface.getElementWidth());
        heightTextField.setText(templateElementInterface.getElementHeight());
        rotationTextField.setText(templateElementInterface.getElementRotation());
        orderTextField.setText(templateElementInterface.getName());
    }
}
