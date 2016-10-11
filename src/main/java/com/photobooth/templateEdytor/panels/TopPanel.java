package com.photobooth.templateEdytor.panels;

import com.photobooth.templateEdytor.TemplateMainView;
import com.photobooth.templateEdytor.elements.*;
import com.photobooth.templateEdytor.serializable.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TopPanel extends HBox {
    private static final String SRC_RESOURCES_TEMPLATES = "src/main/resources/templates/";
    private final TemplateMainView templateMainView;

    private final Button saveTemplateButton;
    private final Button newTemplateButton;
    private final Button copyTemplateButton;
    private final Button deleteTemplateButton;
    private final Button printTemplateButton;

    private final ComboBox templates;

    public TopPanel(TemplateMainView templateMainView) {
        super(5);
        this.templateMainView = templateMainView;

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

        templates = new ComboBox();
        refreshTemplateList();

        copyTemplateButton = new Button("copyTemplateButton");
        copyTemplateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                toFile(templateMainView.getTemplateName() + " - Copy");
                fromFile(templateMainView.getTemplateName() + " - Copy.ser");
            }
        });
        deleteTemplateButton = new Button("deleteTemplateButton");

        deleteTemplateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteTemplate(templateMainView.getTemplateName());
            }
        });


        Button revertTemplateButton = new Button("reverTemplateButton");

        printTemplateButton = new Button("printTemplateButton");

        printTemplateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                List<SerializableTemplateInterface> serializableTemplateInterfaces = getSerializableTemplateInterfaces(templateMainView.getCenterPanel().getChildren());
                Pane pane = new Pane();
                pane.getChildren().add(new Rectangle(900,900, Color.TRANSPARENT));
                for(SerializableTemplateInterface serial : serializableTemplateInterfaces){
                    pane.getChildren().add((Node) serial.toElement());
                }

                Parent root = templateMainView.getScene().getRoot();
                Parent centerPanel  = templateMainView.getCenterPanel();
                Printer printer = Printer.getDefaultPrinter();
                PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
                double scaleX = pageLayout.getPrintableWidth() / pane.getBoundsInParent().getWidth();
                double scaleY = pageLayout.getPrintableHeight() / pane.getBoundsInParent().getHeight();
                pane.getTransforms().add(new Scale(scaleX, scaleY));



                PrinterJob job = PrinterJob.createPrinterJob();
                if (job != null && job.showPrintDialog(templateMainView.getScene().getWindow())){
//                    boolean success = job.printPage(root);
                    boolean success = job.printPage(pane);
                    if (success) {
                        job.endJob();
                        System.out.print("podrukowane");
                    }
                }
            }
        });
        Button importTemplateButton = new Button("importTemplateButton");
        Button exportTemplateButton = new Button("exportTemplateButton");
        Button browseTemplateButton = new Button("browseTemplateButton");
        Button undoTemplateButton = new Button("undoTemplateButton");
        Button redoTemplateButton = new Button("redoTemplateButton");

        Button button = new Button("                                                                                                                ");
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

    private void deleteTemplate(String templateName) {
        try {
            Path path = Paths.get(SRC_RESOURCES_TEMPLATES + templateName + ".ser");
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
            Files.list(Paths.get("src/main/resources/templates")).forEach(path -> templatesFiles.add(path.getFileName().toString()));
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
            FileInputStream fin = new FileInputStream(SRC_RESOURCES_TEMPLATES + filename);
            ObjectInputStream ois = new ObjectInputStream(fin);

            List<SerializableTemplateInterface> serializableTemplateInterfaceList = (List<SerializableTemplateInterface>) ois.readObject();


            for (SerializableTemplateInterface serial : serializableTemplateInterfaceList) {
                this.templateMainView.addNewLayer((Node) serial.toElement());
            }

            ois.close();
            fin.close();
            templates.getSelectionModel().select(filename);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void toFile(String templateName) {
        try {

            ObservableList<Node> children = templateMainView.getCenterPanel().getChildren();

            List<SerializableTemplateInterface> serializableTemplateInterfaceList = getSerializableTemplateInterfaces(children);

            FileOutputStream fout = new FileOutputStream(SRC_RESOURCES_TEMPLATES + templateName + ".ser");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(serializableTemplateInterfaceList);
            oos.flush();
            oos.close();
            fout.close();

            refreshTemplateList();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
