package com.photobooth.templateEdytor.panels;

import com.photobooth.templateEdytor.elements.ImageElement;
import com.photobooth.templateEdytor.TemplateMainView;
import com.photobooth.templateEdytor.elements.PhotoElement;
import com.photobooth.templateEdytor.elements.TextElement;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddLayerPanel extends VBox{

    private final TemplateMainView templateMainView;
    private final Button addImageButton;
    private final Button addPhotoButton;
    private final Button addTextButton;
    private  Integer counter = 1;

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
        Button addRectangleButton = new Button("addRectangleButton");
        Button addCircleButton = new Button("addCircleButton");
        Button addQrCodeButton = new Button("addQrCodeButton");

        setSpacing(5);
        setBorder(templateMainView.getBlackSolidBorder());
        setPadding(new Insets(10,10,10,10));


        getChildren().addAll(addLabel, addImageButton, addPhotoButton, addTextButton,
                addRectangleButton, addCircleButton, addQrCodeButton);
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

                Text text = new Text(counter.toString());
                counter++;
                text.setFont(Font.font ("Verdana", 50));

                StackPane stackPane = new StackPane(photoElement, text);
                templateMainView.addNewLayer(stackPane);
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
