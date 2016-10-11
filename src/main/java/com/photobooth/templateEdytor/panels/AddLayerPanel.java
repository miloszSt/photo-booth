package com.photobooth.templateEdytor.panels;

import com.photobooth.templateEdytor.elements.*;
import com.photobooth.templateEdytor.TemplateMainView;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class AddLayerPanel extends VBox{

    private final TemplateMainView templateMainView;
    private final Button addImageButton;
    private final Button addPhotoButton;
    private final Button addTextButton;
    private final Button addRectangleButton;
    private final Button addCircleButton;
    private Integer counter = 1;

    public AddLayerPanel(TemplateMainView templateMainView) {
        super();
        this.templateMainView = templateMainView;

        Label addLabel = new Label("ADD");

        addImageButton = new Button("addImageButton");
        initAddImageButtonListeners();

        addPhotoButton = new Button("addPhotoButton");
        initAddPhotoButtonListeners();

        addTextButton = new Button("addTextButton");
        initAddTextButtonListeners();

        addRectangleButton = new Button("addRectangleButton");
        initAddRectangleButtonListener();

        addCircleButton = new Button("addCircleButton");
        initAddCircleButtonListener();


        Button addQrCodeButton = new Button("addQrCodeButton");

        setSpacing(5);
        setBorder(templateMainView.getBlackSolidBorder());
        setPadding(new Insets(10,10,10,10));


        getChildren().addAll(addLabel, addImageButton, addPhotoButton, addTextButton,
                addRectangleButton, addCircleButton, addQrCodeButton);
    }

    private void initAddCircleButtonListener() {
        addCircleButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                CircleElement circle = new CircleElement(30, Color.YELLOWGREEN);
                circle.relocate(300,300);
                templateMainView.addNewLayer(circle);
            }
        });
    }

    private void initAddRectangleButtonListener() {
        addRectangleButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                RectangleElement rectangle = new RectangleElement(300,200, Color.GREEN);
                templateMainView.addNewLayer(rectangle);
            }
        });
    }

    private void initAddTextButtonListeners() {
        addTextButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                templateMainView.addNewLayer(new TextElement());
            }
        });
    }

    private void initAddPhotoButtonListeners() {
        addPhotoButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                PhotoElement photoElement = new PhotoElement(300,200, Color.RED, counter);
                counter++;
                templateMainView.addNewLayer(photoElement);
            }
        });
    }

    public void initAddImageButtonListeners(){
        addImageButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ImageElement imageElement = new ImageElement(300,200, Color.BLUE);
                templateMainView.addNewLayer(imageElement);
            }
        });

    }
}
