package com.photobooth.templateEdytor.panels;

import com.photobooth.templateEdytor.TemplateMainView;
import com.photobooth.templateEdytor.elements.*;
import com.photobooth.templateEdytor.serializable.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class TopPanel extends HBox {
    private final TemplateMainView templateMainView;

    private Button saveTemplateButton;
    private Button newTemplateButton;
    private Button copyTemplateButton;
    private Button deleteTemplateButton;
    private Button printTemplateButton;

    private final ComboBox templates;

    public TopPanel(TemplateMainView templateMainView) {
        super(5);
        this.templateMainView = templateMainView;
        initButtons();

        templates = new ComboBox();
        refreshTemplateList();

        Button revertTemplateButton = new Button("reverTemplateButton");
        Button importTemplateButton = new Button("importTemplateButton");
        Button exportTemplateButton = new Button("exportTemplateButton");
        Button browseTemplateButton = new Button("browseTemplateButton");
        Button undoTemplateButton = new Button("undoTemplateButton");
        Button redoTemplateButton = new Button("redoTemplateButton");

        Button button = new Button("                                            ");
        button.setDisable(true);


        saveTemplateButton = new Button("Save");
        saveTemplateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                toFile(templateMainView.getTemplateName());
            }
        });

        getChildren().addAll(newTemplateButton, saveTemplateButton, templates, copyTemplateButton, deleteTemplateButton,
                revertTemplateButton, printTemplateButton, importTemplateButton, exportTemplateButton,
                browseTemplateButton, undoTemplateButton, redoTemplateButton, button);
    }

    private void initButtons(){
        initNewButton();
        initDeleteButton();
        initCopyButton();
        initPrintButton();
    }

    private void initCopyButton() {
        copyTemplateButton = new Button("copyTemplateButton");
        copyTemplateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                toFile(templateMainView.getTemplateName() + " - Copy");
                fromFile(templateMainView.getTemplateName() + " - Copy.ser");
            }
        });
    }

    private void initDeleteButton() {
        deleteTemplateButton = new Button("deleteTemplateButton");
        deleteTemplateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteTemplate(templateMainView.getTemplateName());
            }
        });
    }

    private void initNewButton(){
        newTemplateButton = new Button("New Template");
        newTemplateButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        templateMainView.reset();
                        templateMainView.setTemplateName("new");
                        templates.getSelectionModel().clearSelection();
                    }
                });

    }

    private void initPrintButton(){
        printTemplateButton = new Button("printTemplateButton");

        printTemplateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                print(getTemplateData());
            }
        });
    }
    private void deleteTemplate(String templateName) {
        try {
            Path path = Paths.get(templateMainView.getConfiguration().getTemplatePath() + templateName + ".ser");
            if (Files.exists(path)) {
                Files.delete(path);
            }
            refreshTemplateList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        templateMainView.reset();
    }

    private void refreshTemplateList() {
        List<String> templatesFiles = new ArrayList<>();
        try {
            Files.list(Paths.get(templateMainView.getConfiguration().getTemplatePath())).forEach(path -> templatesFiles.add(path.getFileName().toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObservableList<String> options = FXCollections.observableArrayList(templatesFiles);
        templates.setItems(options);
        templates.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (!(newValue == null || newValue.toString().isEmpty())) {
                    fromFile(newValue.toString());
                }
            }
        });

    }

    private void fromFile(String filename) {
        try {
            templateMainView.setTemplateName(filename.replace(".ser", ""));
            templateMainView.reset();

            FileInputStream fin = new FileInputStream(templateMainView.getConfiguration().getTemplatePath() + filename);
            ObjectInputStream ois = new ObjectInputStream(fin);

            TemplateData data = (TemplateData) ois.readObject();
            List<SerializableTemplateInterface> serializableTemplateInterfaceList = data.getTemplateInterfaceList();


            for (SerializableTemplateInterface serial : serializableTemplateInterfaceList) {
                this.templateMainView.addNewLayer((Node) serial.toElement());
            }

            ois.close();
            fin.close();
            templates.getSelectionModel().select(filename);

            templateMainView.getCenterPanel().setOrientation(data.getOrientation());
            templateMainView.getCenterPanel().setPageSize(data.getPaper());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void toFile(String templateName) {
        try {


            TemplateData templateData = getTemplateData();

            FileOutputStream fout = new FileOutputStream(templateMainView.getConfiguration().getTemplatePath() + templateName + ".ser");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(templateData);
            oos.flush();
            oos.close();
            fout.close();

            refreshTemplateList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private TemplateData getTemplateData() {
        TemplateData templateData = new TemplateData();
        ObservableList<Node> children = templateMainView.getCenterPanel().getElements();
        List<SerializableTemplateInterface> serializableTemplateInterfaceList = getSerializableTemplateInterfaces(children);
        templateData.setTemplateInterfaceList(serializableTemplateInterfaceList);
        templateData.setName(templateMainView.getTemplateName());
        templateData.setOrientation(templateMainView.getCenterPanel().getOrientation());
        templateData.setPaper(templateMainView.getCenterPanel().getPageFormat());
        templateData.setHeight(templateMainView.getCenterPanel().getPageHeight());
        templateData.setWight(templateMainView.getCenterPanel().getPageWidth());
        return templateData;
    }

    private List<SerializableTemplateInterface> getSerializableTemplateInterfaces(ObservableList<Node> children) {
        List<SerializableTemplateInterface> serializableTemplateInterfaceList = new ArrayList<>();

        for (int i = 1; i < children.size(); i++) {
            Node node = children.get(i);

            if (node instanceof CircleElement) {
                serializableTemplateInterfaceList.add(new CircleSerializable((CircleElement) node));
            } else if (node instanceof ImageElement) {
                serializableTemplateInterfaceList.add(new ImageSerializable((ImageElement) node));
            } else if (node instanceof PhotoElement) {
                serializableTemplateInterfaceList.add(new PhotoSerializable((PhotoElement) node));
            } else if (node instanceof RectangleElement) {
                serializableTemplateInterfaceList.add(new RectangleSerializable((RectangleElement) node));
            } else if (node instanceof TextElement) {
                serializableTemplateInterfaceList.add(new TextSerializable((TextElement) node));
            }


        }
        return serializableTemplateInterfaceList;
    }



}
