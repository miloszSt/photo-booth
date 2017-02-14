package com.photobooth.util;

import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Scale;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class PrintHelper {


    public static void print(String filePath, Integer copies) throws InterruptedException {
        Image image = null;
        try {
            image = new Image(Files.newInputStream(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageView imageView = new ImageView(image);
        Thread thread = new Thread(() -> printImage(imageView, copies));
        thread.start();
        thread.join();
    }


    private static void printImage(Node node, Integer copies) {

        Printer printer = Printer.getDefaultPrinter();
        printer.getPrinterAttributes().getSupportedPapers();
        Paper paper = new ArrayList<>(printer.getPrinterAttributes().getSupportedPapers()).get(3);
        PageLayout pageLayout = printer.createPageLayout(paper, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);
        System.out.println("PageLayout: " + pageLayout);


        // Printable area
        double pWidth = pageLayout.getPrintableWidth();
        double pHeight = pageLayout.getPrintableHeight();

        // Node's (Image) dimensions
        double nWidth = node.getBoundsInParent().getWidth();
        double nHeight = node.getBoundsInParent().getHeight();

        // How much space is left? Or is the image to big?
        double widthLeft = pWidth - nWidth;
        double heightLeft = pHeight - nHeight;

        // scale the image to fit the page in width, height or both
        double scaleY = 0;
        double scaleX = 0;

        scaleX = pWidth / nWidth;
        scaleY = pHeight / nHeight;

        node.getTransforms().add(new Scale(scaleX, scaleY));

        // after scale you can check the size fit in the printable area
        double newWidth = node.getBoundsInParent().getWidth();
        double newHeight = node.getBoundsInParent().getHeight();

        PrinterJob job = PrinterJob.createPrinterJob();
        job.getJobSettings().setCopies(copies);
        job.getJobSettings().setPageLayout(pageLayout);
        if (job != null) {
            boolean success = job.printPage(node);
            if (success) {
                job.endJob();
            }
        }
    }
}
