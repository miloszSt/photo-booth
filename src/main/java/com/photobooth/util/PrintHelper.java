package com.photobooth.util;

import com.photobooth.controller.DisplayTemplateController;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Scale;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class PrintHelper {
    final static Logger logger = Logger.getLogger(PrintHelper.class);

    public static void print(String filePath, Integer copies, DisplayTemplateController.Orientation orientation) throws InterruptedException {
        Image image = null;
        try {
            image = new Image(Files.newInputStream(Paths.get(filePath)));
        } catch (IOException e) {
            logger.error(e);
        }
        ImageView imageView = new ImageView(image);
        Thread thread = new Thread(() -> printImage(imageView, copies, orientation));
//        Thread thread = new Thread(() -> dummyPrint(imageView, copies));
        thread.start();
    }
    private static void dummyPrint(Node node, Integer copies){

    }

    private static void printImage(Node node, Integer copies, DisplayTemplateController.Orientation orientation) {

        Printer printer = Printer.getDefaultPrinter();
        printer.getPrinterAttributes().getSupportedPapers();
        Paper paper = new ArrayList<>(printer.getPrinterAttributes().getSupportedPapers()).get(3);
        PageLayout pageLayout;
        if(orientation == DisplayTemplateController.Orientation.HORIZONTAL) {
            pageLayout = printer.createPageLayout(paper, PageOrientation.LANDSCAPE, Printer.MarginType.HARDWARE_MINIMUM);
        }else {
            pageLayout = printer.createPageLayout(paper, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);
        }
        logger.debug("PageLayout: " + pageLayout);


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



        logger.debug("printable Width " + pWidth);
        logger.debug("printable Height " + pHeight);
        logger.debug("new Width " + newWidth);
        logger.debug("new Height " + newHeight);
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
