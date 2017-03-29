package com.photobooth.controller;

import com.photobooth.controller.spec.TemplateAndPhotoInitializable;
import com.photobooth.navigator.Navigator;
import com.photobooth.templateEdytor.elements.PhotoElement;
import com.photobooth.templateEdytor.elements.TemplateElementInterface;
import com.photobooth.templateEdytor.serializable.SerializableTemplateInterface;
import com.photobooth.templateEdytor.serializable.TemplateData;
import com.photobooth.util.ColorUtils;
import com.photobooth.util.ConfigurationUtil;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.*;

import com.photobooth.util.PrintHelper;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DisplayTemplateController implements Initializable, TemplateAndPhotoInitializable {
    final static Logger logger = Logger.getLogger(DisplayTemplateController.class);

    private TemplateData templateData;
    private List<File> photos;
    private Double scaleFactor;
    private Color signatureColor = Color.BLACK;
    Canvas canvas;
    @FXML
    BorderPane borderPane;

    @FXML
    StackPane stackPane;

    @FXML
    Pane finalViewPane = new Pane();

    @FXML
    ToggleButton greenButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("DisplayTemplate");
    }

    @Override
    public void setTemplateAndPhotos(TemplateData template, List<File> photos) {
        this.templateData = template;
        this.photos = photos;
        createPaneWithPhotos();
        addSignature();
    }

    @FXML
    public void setColorGreen() {
        this.signatureColor = Color.GREEN;
    }

    @FXML
    public void setColorRed() {
        this.signatureColor = Color.RED;
    }

    @FXML
    public void setColorBlue() {
        this.signatureColor = Color.BLUE;
    }

    @FXML
    public void setColorBlack() {
        this.signatureColor = Color.BLACK;
    }

    public Pane createPaneWithPhotos() {
        if (finalViewPane.getChildren().size() > 0) finalViewPane.getChildren().clear();
        scaleFactor = 1080.0 / templateData.getWight();
        Double scaleFactor2 = templateData.getHeight() / 900.0;


        final Scale scale = new Scale(scaleFactor, scaleFactor);
        if (!photos.isEmpty()) {
            List<File> photosToUse = new ArrayList<>(photos);
            Rectangle background = new Rectangle(templateData.getWight(), templateData.getHeight(), Color.GREEN);
//
            finalViewPane.getChildren().add(background);
            List<SerializableTemplateInterface> templateInterfaceList = templateData.getTemplateInterfaceList();

            for (SerializableTemplateInterface element : templateInterfaceList) {
                TemplateElementInterface templateElementInterface = element.toElement();

                if (templateElementInterface instanceof PhotoElement) {
                    Integer counter = ((PhotoElement) templateElementInterface).getCounter();
                    ((PhotoElement) templateElementInterface).setPhoto(photosToUse.get(counter - 1));
                    ((PhotoElement) templateElementInterface).getChildren().remove(1);
                }

                finalViewPane.getChildren().add((Node) templateElementInterface);

                finalViewPane.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));

            }

            System.out.println("w " + templateData.getWight() + "  H : " + templateData.getHeight());
            System.out.println("wF " + templateData.getWight() * scaleFactor + "  HF : " + templateData.getHeight() * scaleFactor);
//            System.out.println("wF2 " + templateData.getWight() * scaleFactor2 + "  HF2 : " + templateData.getHeight() * scaleFactor2 );


            borderPane.setCenter(stackPane);
//            stackPane.setBackground(new Background(new BackgroundFill(ColorUtils.parseStringToColor(templateData.getBackgroundColor()), null, null)));
            borderPane.setBackground(new Background(new BackgroundFill(ColorUtils.parseStringToColor(templateData.getBackgroundColor()), null, null)));
            borderPane.setMaxWidth(templateData.getWight());
            borderPane.setMaxHeight(templateData.getHeight());
//            stackPane.setAlignment(Pos.CENTER);


//            stackPane.setMaxHeight(10);
//            stackPane.setMaxWidth(1080);

//            ((GridPane)borderPane.getTop()).setBackground(new Background(new BackgroundFill(Color.BLACK,null, null)));
            stackPane.getChildren().add(finalViewPane);

            stackPane.getTransforms().add(scale);
        }
        return finalViewPane;
    }

    @FXML
    public void clearSignature() {
        Rectangle node = (Rectangle) finalViewPane.getChildren().get(0);
        canvas.getGraphicsContext2D().clearRect(0, 0, node.getWidth(), node.getHeight());
    }


    @FXML
    public void addSignature() {
        Rectangle node = (Rectangle) finalViewPane.getChildren().get(0);

        canvas = new Canvas(node.getWidth(), node.getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.TRANSPARENT);

        canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                gc.setFill(signatureColor);
                gc.fillOval(event.getX() - 10, event.getY() - 10, 20, 20);
            }
        });

        canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                gc.setFill(signatureColor);
                gc.fillOval(event.getX() - 10, event.getY() - 10, 20, 20);
            }
        });

        finalViewPane.getChildren().add(canvas);
    }

    public void print1() {
        print(1);
    }

    public void print2() {
        print(2);
    }

    @FXML
    private void reset(){
        Navigator.start();
    }

    private void print(Integer copies) {
        stackPane.setMaxHeight(10);
        stackPane.setMaxWidth(1080);

        finalViewPane.setBackground(null);
        SnapshotParameters params = new SnapshotParameters();
        params.setViewport(new Rectangle2D(0, 0, 1200, 1800));
        WritableImage image = finalViewPane.snapshot(params, null);
        DateTimeFormatter yyyymmdd_hHmm = DateTimeFormatter.ofPattern("MMdd_HHmm");
        LocalDateTime now = LocalDateTime.now();
        String fileName = now.format(yyyymmdd_hHmm);
        File file = new File("c:/" + ConfigurationUtil.initConfiguration().getTempPath() + fileName + ".png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);

            PrintHelper.print(file.getPath(), copies);
            Navigator.nextState();
        } catch (IOException e) {
            // TODO: handle exception here
        } catch (InterruptedException e) {
            logger.error(e);
        }
    }


}
