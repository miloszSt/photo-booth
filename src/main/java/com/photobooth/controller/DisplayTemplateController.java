package com.photobooth.controller;

import com.google.common.collect.FluentIterable;
import com.photobooth.controller.spec.TemplateAndPhotoInitializable;
import com.photobooth.navigator.Navigator;
import com.photobooth.templateEdytor.elements.ImageElement;
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
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class DisplayTemplateController implements Initializable, TemplateAndPhotoInitializable {
    final static Logger logger = Logger.getLogger(DisplayTemplateController.class);

    private TemplateData templateData;
    private List<File> photos;
    private Double scaleFactor;
    private Color signatureColor = Color.BLACK;
    private Orientation orientation;

    public enum Orientation {
        VERTICAL, HORIZONTAL;
    }

    private Canvas canvas;

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
        logger.debug("DisplayTemplate");
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

    private Orientation getOrientation(TemplateData templateData) {
        if (templateData.getWidht() > templateData.getHeight()) {
            return Orientation.HORIZONTAL;
        }
        return Orientation.VERTICAL;
    }

    public Pane createPaneWithPhotos() {
        if (finalViewPane.getChildren().size() > 0) finalViewPane.getChildren().clear();

        this.orientation = getOrientation(templateData);

        scaleFactor = 1080.0 / templateData.getWidht();
        Double scaleFactor2 = 1080.0 / templateData.getWidht();


        final Scale scale;
        if (orientation == Orientation.VERTICAL) {
            scale = new Scale(scaleFactor, scaleFactor);
        } else {
            scale = new Scale(scaleFactor2, scaleFactor2);
        }
        if (!photos.isEmpty()) {
            List<File> photosToUse = new ArrayList<>(photos);
            Rectangle background = new Rectangle(templateData.getWidht(), templateData.getHeight(), Color.GREEN);
            finalViewPane.getChildren().add(background);
            List<SerializableTemplateInterface> templateInterfaceList = templateData.getTemplateInterfaceList();

            int highestPhotoCounter = 0;
            for (SerializableTemplateInterface element : templateInterfaceList) {
                TemplateElementInterface templateElementInterface = element.toElement();
                if (templateElementInterface instanceof PhotoElement) {
                    if (highestPhotoCounter < ((PhotoElement) templateElementInterface).getCounter()) {
                        highestPhotoCounter = ((PhotoElement) templateElementInterface).getCounter();
                    }
                }
            }
            while (highestPhotoCounter < photosToUse.size()) {
                photosToUse.remove(0);
            }

            for (SerializableTemplateInterface element : templateInterfaceList) {
                TemplateElementInterface templateElementInterface = element.toElement();

                if (templateElementInterface instanceof PhotoElement) {
                    Integer counter = ((PhotoElement) templateElementInterface).getCounter();
                    ((PhotoElement) templateElementInterface).setPhoto(photosToUse.get(counter - 1));
                    ((PhotoElement) templateElementInterface).getChildren().remove(1);
                }

                if (templateElementInterface instanceof ImageElement) {
                    double height = templateElementInterface.getElementHeight();

                    double elementTop = templateElementInterface.getElementTop();
                    if ((height + elementTop) > templateData.getHeight()) {
                        ((ImageElement) templateElementInterface).setHeight((int) (templateData.getHeight() - elementTop));
                    }
                }

                finalViewPane.getChildren().add((Node) templateElementInterface);
                finalViewPane.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));

            }
            borderPane.setCenter(stackPane);
            borderPane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
            borderPane.setMaxWidth(templateData.getWidht());
            borderPane.setMaxHeight(templateData.getHeight());

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
    private void reset() {
        getSnapshot();
        Navigator.start();
    }

    private void print(Integer copies) {
        File file = getSnapshot();
        try {
            PrintHelper.print(file.getPath(), copies, orientation);
        } catch (InterruptedException e) {
            logger.error(e);
        }
    }

    private File getSnapshot() {
        stackPane.setMaxHeight(10);
        stackPane.setMaxWidth(1080);

        finalViewPane.setBackground(null);
        SnapshotParameters params = new SnapshotParameters();

        if (orientation == Orientation.HORIZONTAL) {
            params.setViewport(new Rectangle2D(0, 0, 1800, 1200));
        }
        if (orientation == Orientation.VERTICAL) {
            params.setViewport(new Rectangle2D(0, 0, 1200, 1800));
        }
        WritableImage image = finalViewPane.snapshot(params, null);

        DateTimeFormatter yyyymmdd_hHmm = DateTimeFormatter.ofPattern("MMdd_HHmm");
        LocalDateTime now = LocalDateTime.now();
        String fileName = now.format(yyyymmdd_hHmm);
        File file = new File(ConfigurationUtil.initConfiguration().getTempPath() + fileName + ".png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            Navigator.nextState();
        } catch (IOException e) {
            logger.error(e);
        }
        return file;
    }


}

