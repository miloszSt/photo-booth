package com.photobooth.controller;

import com.photobooth.controller.spec.TemplateAndPhotoInitializable;
import com.photobooth.navigator.Navigator;
import com.photobooth.templateEdytor.elements.PhotoElement;
import com.photobooth.templateEdytor.elements.TemplateElementInterface;
import com.photobooth.templateEdytor.serializable.SerializableTemplateInterface;
import com.photobooth.templateEdytor.serializable.TemplateData;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DisplayTemplateController implements Initializable, TemplateAndPhotoInitializable {

    private TemplateData templateData;
    private List<File> photos;

    @FXML
    StackPane mediaPane;

    @FXML
    Pane finalViewPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("DisplayTemplate");
    }

    @Override
    public void setTemplateAndPhotos(TemplateData template, List<File> photos) {
        this.templateData = template;
        this.photos = photos;
        createPaneWithPhotos();
    }

    public Pane createPaneWithPhotos(){
        List<File> photosToUse = new ArrayList<>(photos);
        if (finalViewPane.getChildren().size() > 0) finalViewPane.getChildren().clear();

        Rectangle background = new Rectangle(templateData.getWight(), templateData.getHeight(), Color.TRANSPARENT);

        finalViewPane.getChildren().add(background);
        List<SerializableTemplateInterface> templateInterfaceList = templateData.getTemplateInterfaceList();

        for(SerializableTemplateInterface element : templateInterfaceList){
            TemplateElementInterface templateElementInterface = element.toElement();

            if(templateElementInterface instanceof PhotoElement){
                ((PhotoElement) templateElementInterface).setPhoto(photosToUse.remove(0));
            }

            finalViewPane.getChildren().add((Node) templateElementInterface);
        }

        return finalViewPane;
    }

    @FXML
    public void handleMouseClick(Event event) {
        print();
        Navigator.nextState();
    }

    public void print(){
        WritableImage image = mediaPane.snapshot(new SnapshotParameters(), null);

        File file = new File("e:/fotki/gotowe.png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            // TODO: handle exception here
        }

    }
}
