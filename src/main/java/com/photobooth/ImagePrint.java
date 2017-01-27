package com.photobooth;

import com.sun.javafx.print.PrintHelper;
import com.sun.javafx.print.Units;
import javafx.application.Application;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ImagePrint extends Application {

    @Override
    public void start(Stage stage) {
        String path = "F:\\Zdjecia\\SantiagoWybrane\\Fotka (67).jpg";
            path = "F:\\Zdjecia\\SantiagoTomek\\100NIKON\\DSCN0879.JPG";

        Image image = null;
        try {
            image = new Image(Files.newInputStream(Paths.get(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageView imageView = new ImageView(image);
        new Thread(() -> printImage(imageView)).start();
    }

    public void printImage(Node node) {

        Printer printer = Printer.getDefaultPrinter();
        printer.getPrinterAttributes().getSupportedPapers();
        Paper paper = new ArrayList<>(printer.getPrinterAttributes().getSupportedPapers()).get(3);
//        paper = PrintHelper.createPaper("Paper: PR (4x6) x 2", 104.9, 156.1, Units.MM);
//        paper =      printer.getPrinterAttributes().getDefaultPaper();
        PageLayout pageLayout = printer.createPageLayout(paper, PageOrientation.LANDSCAPE, Printer.MarginType.HARDWARE_MINIMUM);
//        PageLayout pageLayout = printer.getDefaultPageLayout();
        System.out.println("PageLayout: " + pageLayout);

        // Printable area
        double pWidth = pageLayout.getPrintableWidth();
        double pHeight = pageLayout.getPrintableHeight();
        System.out.println("Printable area is " + pWidth + " width and "
                + pHeight + " height.");

        // Node's (Image) dimensions
        double nWidth = node.getBoundsInParent().getWidth();
        double nHeight = node.getBoundsInParent().getHeight();
        System.out.println("Node's dimensions are " + nWidth + " width and "
                + nHeight + " height");

        // How much space is left? Or is the image to big?
        double widthLeft = pWidth - nWidth;
        double heightLeft = pHeight - nHeight;
        System.out.println("Width left: " + widthLeft
                + " height left: " + heightLeft);

        // scale the image to fit the page in width, height or both
        double scaleY = 0;
        double scaleX = 0;

        scaleX = pWidth / nWidth;
        scaleY = pHeight / nHeight;

//
//        if (widthLeft < heightLeft) {
//            scaleY = pWidth / nWidth;
//        } else {
//            scaleX = pHeight / nHeight;
//        }

        // preserve ratio (both values are the same)
        node.getTransforms().add(new Scale(scaleX, scaleY));

        // after scale you can check the size fit in the printable area
        double newWidth = node.getBoundsInParent().getWidth();
        double newHeight = node.getBoundsInParent().getHeight();
        System.out.println("New Node's dimensions: " + newWidth
                + " width " + newHeight + " height");

        PrinterJob job = PrinterJob.createPrinterJob();
        job.getJobSettings().setPageLayout(pageLayout);
        if (job != null) {
            boolean success = job.printPage(node);
            if (success) {
                job.endJob();
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
