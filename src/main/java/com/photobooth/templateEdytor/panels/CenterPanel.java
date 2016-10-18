package com.photobooth.templateEdytor.panels;

import com.photobooth.templateEdytor.TemplateMainView;
import com.photobooth.templateEdytor.DragResizeMod;
import javafx.collections.ObservableList;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CenterPanel extends Pane {

    private final TemplateMainView templateMainView;
    private Pane mainPane;

    private Rectangle bigBackground = new Rectangle(1200, 900, Color.BLACK);
    private Rectangle backgroundPanel = new Rectangle(1200, 900, Color.WHITE);

    private PageOrientation orientation = PageOrientation.PORTRAIT;
    private Paper pageFormat = Paper.A4;


    public CenterPanel(TemplateMainView templateMainView) {
        super();

        mainPane = new Pane();

        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);

        double printableHeight = pageLayout.getPrintableHeight();
        double printableWidth = pageLayout.getPrintableWidth();

        System.out.println("rozmiar H  " + printableHeight);
        System.out.println("rozmiar W " + printableWidth);

        backgroundPanel = new Rectangle(printableWidth, printableHeight, Color.WHITE);
        this.templateMainView = templateMainView;

        mainPane.getChildren().addAll(backgroundPanel);
        redrawBackground();
        getChildren().addAll(bigBackground, mainPane);
    }

    public Rectangle getBackgroundPanel() {
        return backgroundPanel;
    }

    public void addNewElement(Node layer) {
        mainPane.getChildren().addAll(layer);
        DragResizeMod.makeResizable(layer, (int) backgroundPanel.getHeight(), (int) backgroundPanel.getWidth());
    }

    public void moveHigher(Node currentNode) {
        ObservableList<Node> children = mainPane.getChildren();
        int i = children.indexOf(currentNode);
        if (i > 1) {
            children.remove(i);
            children.add(--i, currentNode);
        }
    }

    public void moveLower(Node currentNode) {
        ObservableList<Node> children = mainPane.getChildren();
        int i = children.indexOf(currentNode);
        if (i < children.size() - 1) {
            children.remove(i);
            children.add(++i, currentNode);
        }

    }

    public ObservableList<Node> getElements() {
        return mainPane.getChildren();
    }

    public void setOrientation(PageOrientation orientation) {
        this.orientation = orientation;

        redrawBackground();
    }

    public void setPageSize(Paper size) {
        this.pageFormat = size;
        redrawBackground();
    }

    public void redrawBackground() {

        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(pageFormat, orientation, Printer.MarginType.DEFAULT);

        double printableHeight = pageLayout.getPrintableHeight();
        double printableWidth = pageLayout.getPrintableWidth();

        System.out.println("rozmiar H  " + printableHeight);
        System.out.println("rozmiar W " + printableWidth);

        backgroundPanel.setWidth(printableWidth);
        backgroundPanel.setHeight(printableHeight);

        DragResizeMod.setHEIGHT((int) printableHeight);
        DragResizeMod.setWIDTH((int) printableWidth);
    }

    public int getPageHeight(){
        return (int) backgroundPanel.getHeight();
    }

    public int getPageWidth(){
        return (int) backgroundPanel.getWidth();
    }

    public void removeLayer(Node node){
        mainPane.getChildren().remove(node);
    }
}
