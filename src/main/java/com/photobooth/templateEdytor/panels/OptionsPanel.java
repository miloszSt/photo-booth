package com.photobooth.templateEdytor.panels;

import com.photobooth.templateEdytor.TemplateMainView;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OptionsPanel extends VBox{

    private final TemplateMainView templateMainView;

    public OptionsPanel(TemplateMainView templateMainView) {
        super();
        this.templateMainView = templateMainView;

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

        setSpacing(5);
        setBorder(templateMainView.getBlackSolidBorder());

        getChildren().addAll(optionsLabel, fillColorHbox, strokeColorHbox, strokeThicknessLabel, strokeThickness, aspectRatioBox, opacityLabel, opacity);
    }
}
