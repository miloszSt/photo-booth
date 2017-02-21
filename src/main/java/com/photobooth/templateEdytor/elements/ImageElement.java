package com.photobooth.templateEdytor.elements;

import com.photobooth.util.IdCreator;
import com.photobooth.templateEdytor.serializable.ImageSerializable;
import com.photobooth.templateEdytor.serializable.SerializableTemplateInterface;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import javax.swing.*;
import java.io.File;


public class ImageElement extends StackPane implements TemplateElementInterface{

    private final Integer elementId;
    private Rectangle rectangle;
    private String imageAbsolutePath;
    public ImageElement(double width, double height, Paint fill) {
        super();

        elementId = IdCreator.getCounter();
        rectangle = new Rectangle(width, height, fill);
        getChildren().addAll(rectangle);
        setId("Image Element");
        setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if(db.hasFiles()){
                    event.acceptTransferModes(TransferMode.ANY);
                }

                event.consume();
            }
        });

        setOnDragDropped(new EventHandler <DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if(db.hasFiles()){

                    for(File file:db.getFiles()){
                        imageAbsolutePath = file.getAbsolutePath();

                        setImage();
                    }

                    event.setDropCompleted(true);
                }else{
                    event.setDropCompleted(false);
                }
                event.consume();
            }
        });
    }



    private Rectangle selectionRectangle = new Rectangle(30,30,Color.YELLOWGREEN);

    @Override
    public void select() {
        deselect();
        setAlignment(selectionRectangle, Pos.TOP_LEFT);
        getChildren().addAll(selectionRectangle);
    }

    public String getImageAbsolutePath() {
        return imageAbsolutePath;
    }

    public void setImageAbsolutePath(String imageAbsolutePath) {
        this.imageAbsolutePath = imageAbsolutePath;
    }

    public void setImage(){
        if(imageAbsolutePath != null) {

            Image dbimage = new Image(String.valueOf(new File(imageAbsolutePath).toURI()) );
            ImageView dbImageView = new ImageView();
            dbImageView.setImage(dbimage);

            rectangle.setFill(new ImagePattern(dbimage, 0, 0, 1, 1, true));
        }
    }

    public void setHeight(Integer height){
        rectangle.setHeight(height);
    }

    public void setWidth(Integer width){
        rectangle.setWidth(width);
    }


    @Override
    public void deselect() {
        getChildren().remove(selectionRectangle);
    }


    @Override
    public String getName() {
        return getId();
    }

    @Override
    public double getElementTop() {
        return getBoundsInParent().getMinY();
    }

    @Override
    public double getElementLeft() {
        return getBoundsInParent().getMinX();
    }

    @Override
    public double getElementWidth() {
        return getBoundsInParent().getWidth();
    }

    @Override
    public double getElementHeight() {
        return getBoundsInParent().getHeight();
    }

    @Override
    public double getElementRotation() {
        return 0d;
    }

    @Override
    public Paint getElementColor() {
        return rectangle.getFill();
    }

    @Override
    public SerializableTemplateInterface serialize() {
        return new ImageSerializable(this);
    }

    @Override
    public void setBackgroundColor(Paint color) {

    }

    @Override
    public void setSecondColor(Paint color) {

    }

    @Override
    public Integer getElementId() {
        return elementId;
    }


    public void setStroke(Color color, Integer thickness) {
        this.setBorder(new Border(new BorderStroke(color, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(thickness))));
    }
}
