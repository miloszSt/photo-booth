package com.photobooth.controller;

import com.photobooth.controller.spec.TemplateAndPhotoInitializable;
import com.photobooth.navigator.Navigator;
import com.photobooth.templateEdytor.elements.PhotoElement;
import com.photobooth.templateEdytor.elements.TemplateElementInterface;
import com.photobooth.templateEdytor.serializable.SerializableTemplateInterface;
import com.photobooth.templateEdytor.serializable.TemplateData;
import com.photobooth.util.ConfigurationUtil;
import com.photobooth.util.PrintHelper;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DisplayTemplateController implements Initializable, TemplateAndPhotoInitializable {

    private TemplateData templateData;
    private List<File> photos;

    @FXML
    BorderPane borderPane;

    @FXML
    StackPane stackPane;

    @FXML
    Pane finalViewPane = new Pane();

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

    public  Pane createPaneWithPhotos(){
        if (finalViewPane.getChildren().size() > 0) finalViewPane.getChildren().clear();

        if (!photos.isEmpty()) {
            List<File> photosToUse = new ArrayList<>(photos);
            Rectangle background = new Rectangle(templateData.getWight(), templateData.getHeight(), Color.TRANSPARENT);

            finalViewPane.getChildren().add(background);
            List<SerializableTemplateInterface> templateInterfaceList = templateData.getTemplateInterfaceList();

            for (SerializableTemplateInterface element : templateInterfaceList) {
                TemplateElementInterface templateElementInterface = element.toElement();

                if (templateElementInterface instanceof PhotoElement) {
                    Integer counter = ((PhotoElement) templateElementInterface).getCounter();
                    ((PhotoElement) templateElementInterface).setPhoto(photosToUse.get(counter - 1));
                }

                finalViewPane.getChildren().add((Node) templateElementInterface);
            }
            borderPane.setCenter(stackPane);
            stackPane.setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setMaxHeight(100);
            stackPane.setMaxWidth(100);
            stackPane.getChildren().add(finalViewPane);
        }

        return finalViewPane;
    }


    @FXML
    public void addSignature(){

        Rectangle rectangle = new Rectangle(finalViewPane.getWidth(), finalViewPane.getHeight(), Color.TRANSPARENT);

        rectangle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("x" + event.getSceneX());
                System.out.println("y" + event.getSceneY());
            }
        });

        finalViewPane.getChildren().add(rectangle);
    }

    @FXML
    public void handleMouseClick(Event event) {
        print();
        Navigator.nextState();
    }

    public void print(){
        WritableImage image = finalViewPane.snapshot(new SnapshotParameters(), null);
        File file = new File(ConfigurationUtil.initConfiguration().getTempPath() + LocalDateTime.now().toString().substring(0,13) + ".png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            PrintHelper.print(file.getPath());

        } catch (IOException e) {
            // TODO: handle exception here
        }

    }
}
