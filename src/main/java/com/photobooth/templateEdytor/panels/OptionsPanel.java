package com.photobooth.templateEdytor.panels;

import com.photobooth.templateEdytor.TemplateMainView;
import com.photobooth.templateEdytor.elements.TemplateElementInterface;
import com.photobooth.templateEdytor.elements.TextElement;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;


public class OptionsPanel extends VBox {

    private final TemplateMainView templateMainView;
    private final ColorPicker fillColorPicker;
    private final ColorPicker strokeColorPicker;
    private final ScrollBar strokeThickness;
    private final TextArea textField;

    public OptionsPanel(TemplateMainView templateMainView) {

        super();
        this.templateMainView = templateMainView;

        Label optionsLabel = new Label("OPTIONS");

        fillColorPicker = new ColorPicker();

        fillColorPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TemplateElementInterface currentSelection = (TemplateElementInterface) templateMainView.getCurrentSelection();
                currentSelection.setBackgroundColor(fillColorPicker.getValue());
            }
        });
        HBox fillColorHbox;
        fillColorHbox = new HBox(new Label("Fill color"), fillColorPicker);


        strokeColorPicker = new ColorPicker();

        strokeColorPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TemplateElementInterface currentSelection = (TemplateElementInterface) templateMainView.getCurrentSelection();
                currentSelection.setSecondColor(strokeColorPicker.getValue());
            }
        });

        HBox strokeColorHbox = new HBox(new Label("Stroke color"), strokeColorPicker);

        Label strokeThicknessLabel = new Label("Text size");

        strokeThickness = new ScrollBar();
        strokeThickness.setMin(0);
        strokeThickness.setMax(100);
        strokeThickness.setValue(20);

        strokeThickness.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Node currentSelection = templateMainView.getCurrentSelection();
                if(currentSelection instanceof TextElement){
                    ((TextElement) currentSelection).setTextSize( newValue.intValue());
                }
            }
        });

//        CheckBox aspectRatio = new CheckBox();
//        HBox aspectRatioBox = new HBox(aspectRatio, new Label("Keep aspect ratio"));

//        Label opacityLabel = new Label("Opacity");
//        ScrollBar opacity = new ScrollBar();
//        opacity.setMin(0);
//        opacity.setMax(100);
//        opacity.setValue(50);

        setSpacing(5);
        setBorder(templateMainView.getBlackSolidBorder());


        Label textContentLabel = new Label("Text : ");
        textField = new TextArea();
        textField.setMaxSize(250,100);

        textField.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Node currentSelection = templateMainView.getCurrentSelection();
                if(currentSelection instanceof TextElement){

                   ((TextElement) currentSelection).setText(new Text(newValue));
                    ((TextElement) currentSelection).setFontStyle((int) strokeThickness.getValue(), strokeColorPicker.getValue().toString());
                }
            }
        });


        getChildren().addAll(optionsLabel, fillColorHbox, strokeColorHbox, strokeThicknessLabel, strokeThickness,textContentLabel, textField);
    }

    public void setBackgroundColor(Color color){
        fillColorPicker.setValue(color);
    }

    public void setSecondColor(Color color){
        strokeColorPicker.setValue(color);
    }

    public void setTextSize(Integer size){
        strokeThickness.setValue(size.doubleValue());
    }
    public void setText(String text){
        textField.setText(text);
    }
}
