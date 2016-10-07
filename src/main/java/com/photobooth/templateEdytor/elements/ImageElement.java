package com.photobooth.templateEdytor.elements;

import com.photobooth.IdCreator;
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

import java.io.File;


public class ImageElement extends StackPane implements TemplateElementInterface{

    private final Integer elementId;
    private Rectangle rectangle;

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
                        //String absolutePath = file.getAbsolutePath();
                        String absolutePath = file.toURI().toString();

                        Image dbimage = new Image(absolutePath);
                        ImageView dbImageView = new ImageView();
                        dbImageView.setImage(dbimage);

                        rectangle.setFill(new ImagePattern(dbimage, 0, 0, 1, 1, true));
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

    @Override
    public void deselect() {
        getChildren().remove(selectionRectangle);
    }


    @Override
    public String getName() {
        return getId();
    }

    @Override
    public String getElementTop() {
        return null;
    }

    @Override
    public String getElementLeft() {
        return null;
    }

    @Override
    public String getElementWidth() {
        return null;
    }

    @Override
    public String getElementHeight() {
        return null;
    }

    @Override
    public String getElementRotation() {
        return null;
    }

    @Override
    public Integer getElementId() {
        return elementId;
    }


}
