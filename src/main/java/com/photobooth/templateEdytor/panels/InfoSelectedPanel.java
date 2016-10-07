package com.photobooth.templateEdytor.panels;

import com.photobooth.templateEdytor.TemplateMainView;
import com.photobooth.templateEdytor.elements.TemplateElementInterface;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class InfoSelectedPanel extends VBox {

    private final TemplateMainView templateMainView;

    private Node currentNode;

    private final TextField nameTextField;
    private final TextField topTextField;
    private final TextField leftTextField;
    private final TextField widthTextField;
    private final TextField heightTextField;
    private final TextField rotationTextField;
    private final TextField orderTextField;

    private final Button upButton;
    private final Button downButton;

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
        upButton = new Button("Up");
        downButton = new Button("Down");

        setButtonListeners();
        setTextAreaListeners();
        GridPane gridPane = new GridPane();


        Label name = new Label("Name");
        Label top = new Label("Top");
        Label left = new Label("Left");
        Label width = new Label("Width");
        Label height = new Label("Height");
        Label rotation = new Label("Rotation");
        Label order = new Label("Order");


        HBox vBox = new HBox(upButton, downButton);

        GridPane.setRowIndex(nameTextField, 1);
        GridPane.setRowIndex(topTextField, 2);
        GridPane.setRowIndex(leftTextField, 3);
        GridPane.setRowIndex(widthTextField, 4);
        GridPane.setRowIndex(heightTextField, 5);
        GridPane.setRowIndex(rotationTextField, 6);
        GridPane.setRowIndex(vBox, 7);

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


        GridPane.setColumnIndex(vBox, 2);


        gridPane.getChildren().addAll(name, top, left, width, height, rotation, order, nameTextField, topTextField,
                leftTextField, widthTextField, heightTextField, rotationTextField, vBox);
        gridPane.setGridLinesVisible(true);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        setSpacing(5);
        setBorder(templateMainView.getBlackSolidBorder());


        getChildren().addAll(gridPane);
    }

    public void setTemplateElementInterface(final StackPane node) {
        TemplateElementInterface templateElementInterface = (TemplateElementInterface) node;

        this.currentNode = node;

        nameTextField.textProperty().setValue(templateElementInterface.getName());

        topTextField.textProperty().setValue(doubleToIntWithoutDot(node.getBoundsInParent().getMinY()));
        leftTextField.textProperty().setValue(doubleToIntWithoutDot(node.getBoundsInParent().getMinX()));

        Node rectangleOrCircle = ((StackPane) currentNode).getChildren().get(0);
        if (rectangleOrCircle instanceof Rectangle) {
            widthTextField.textProperty().setValue(doubleToIntWithoutDot(node.getBoundsInParent().getWidth()));
            heightTextField.textProperty().setValue(doubleToIntWithoutDot(node.getBoundsInParent().getHeight()));
        } else if (rectangleOrCircle instanceof Circle) {
            widthTextField.textProperty().setValue(doubleToIntWithoutDot(((Circle) rectangleOrCircle).getRadius()));
        }

        rotationTextField.textProperty().setValue(templateElementInterface.getElementRotation());
        orderTextField.textProperty().setValue(templateElementInterface.getName());
    }


    public String doubleToIntWithoutDot(double number) {
        return Integer.valueOf(Double.valueOf(number).intValue()).toString();
    }

    public void setButtonListeners() {
        downButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<Node> children = templateMainView.getCenterPanel().getChildren();
                int i = children.indexOf(currentNode);
                if (i > 1) {
                    children.remove(i);
                    children.add(--i, currentNode);
                }
            }
        });

        upButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<Node> children = templateMainView.getCenterPanel().getChildren();
                int i = children.indexOf(currentNode);
                if (i < children.size() - 1) {
                    children.remove(i);
                    children.add(++i, currentNode);
                }
            }
        });
    }

    public void setTextAreaListeners() {
        topTextField.addEventFilter(KeyEvent.KEY_TYPED, numericValidation(5));
        leftTextField.addEventFilter(KeyEvent.KEY_TYPED, numericValidation(5));
        widthTextField.addEventFilter(KeyEvent.KEY_TYPED, numericValidation(5));
        heightTextField.addEventFilter(KeyEvent.KEY_TYPED, numericValidation(5));

        topTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.isEmpty()) {
                    currentNode.setLayoutY(Integer.valueOf(newValue));
                }
            }
        });


        leftTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.isEmpty()) {
                    currentNode.setLayoutX(Integer.valueOf(newValue));
                }
            }
        });

        widthTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.isEmpty()) {
                    Node rectangleOrCircle = ((StackPane) currentNode).getChildren().get(0);
                    if (rectangleOrCircle instanceof Rectangle) {
                        ((Rectangle) rectangleOrCircle).setWidth(Integer.valueOf(newValue));
                    } else if (rectangleOrCircle instanceof Circle) {
                        ((Circle) rectangleOrCircle).setRadius(Integer.valueOf(newValue));
                    }
                }

            }
        });

        heightTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.isEmpty()) {
                    Node rectangleOrCircle = ((StackPane) currentNode).getChildren().get(0);
                    if (rectangleOrCircle instanceof Rectangle) {
                        Rectangle rectangle = (Rectangle) rectangleOrCircle;
                        rectangle.setHeight(Integer.valueOf(newValue));
                    }
                }
            }
        });


    }

    public EventHandler<KeyEvent> numericValidation(final Integer maxLengh) {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                TextField txtTextField = (TextField) e.getSource();
                if (txtTextField.getText().length() >= maxLengh) {
                    e.consume();
                }
                if (e.getCharacter().matches("[0-9.]")) {
                    if (txtTextField.getText().contains(".") && e.getCharacter().matches("[.]")) {
                        e.consume();
                    } else if (txtTextField.getText().length() == 0 && e.getCharacter().matches("[.]")) {
                        e.consume();
                    }
                } else {
                    e.consume();
                }
            }
        };
    }

}

