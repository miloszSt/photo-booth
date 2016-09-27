package com.photobooth;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.File;

public class DragDrop extends Application{
    @Override
    public void start(Stage primaryStage) {

        StackPane root = new StackPane();
        Pane pane = new Pane();

        Circle circle = new Circle(100,100,100);
        enableDragging(circle);

        pane.getChildren().addAll(circle);
        root.getChildren().addAll(pane);

        circle.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
            /* data is dragged over the target */
                System.out.println("onDragOver");

                Dragboard db = event.getDragboard();
                if(db.hasFiles()){
                    event.acceptTransferModes(TransferMode.ANY);
                }

                event.consume();
            }
        });

        circle.setOnDragDropped(new EventHandler <DragEvent>() {
            @Override
            public void handle(DragEvent event) {
            /* data dropped */
                System.out.println("onDragDropped");

                Dragboard db = event.getDragboard();

                if(db.hasFiles()){

                    for(File file:db.getFiles()){
                        //String absolutePath = file.getAbsolutePath();
                        String absolutePath = file.toURI().toString();

                        Image dbimage = new Image(absolutePath);
                        ImageView dbImageView = new ImageView();
                        dbImageView.setImage(dbimage);

                        circle.setFill(new ImagePattern(dbimage, 0, 0, 1, 1, true));
                    }

                    event.setDropCompleted(true);
                }else{
                    event.setDropCompleted(false);
                }
                event.consume();
            }
        });

        Scene scene = new Scene(root, 300, 300);

        primaryStage.setTitle("Drag and Drop Image from Pc into Circle");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void enableDragging(Node node) {
        final ObjectProperty<Point2D> mouseAnchor = new SimpleObjectProperty<>();
        node.setOnMousePressed( event -> mouseAnchor.set(new Point2D(event.getSceneX(), event.getSceneY())));
        node.setOnMouseDragged( event -> {
            double deltaX = event.getSceneX() - mouseAnchor.get().getX();
            double deltaY = event.getSceneY() - mouseAnchor.get().getY();
            node.relocate(node.getLayoutX()+deltaX, node.getLayoutY()+deltaY);
            mouseAnchor.set(new Point2D(event.getSceneX(), event.getSceneY()));;
        });
    }
    public static void main(String[] args) {
        launch(args);
    }



}


