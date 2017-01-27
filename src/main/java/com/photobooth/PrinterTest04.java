package com.photobooth;

import com.sun.javafx.print.PrintHelper;
import com.sun.javafx.print.Units;
import javafx.print.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Tomasz on 02.01.2017.
 */
public class PrinterTest04 {

    public static void main(String[] args) throws IOException {
        String path = "C:\\photoBooth\\temp\\2016-12-01T20.png";
        Image image = new Image(Files.newInputStream(Paths.get(path)));
        ImageView imageView = new ImageView(image);

        Printer printer = Printer.getDefaultPrinter();
        PrinterJob printerJob = PrinterJob.createPrinterJob(Printer.getDefaultPrinter());
        Paper paper = PrintHelper.createPaper("6x8", 6d, 8d, Units.INCH);
        PageLayout pageLayout = printer.createPageLayout(paper, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);


        imageView.setPreserveRatio(true);
        imageView.setFitHeight(pageLayout.getPrintableHeight());
        imageView.setFitWidth(pageLayout.getPrintableWidth());


        System.out.println("Left margin " + pageLayout.getLeftMargin());
        System.out.println("Right margin " + pageLayout.getRightMargin());
        System.out.println("Top margin " + pageLayout.getTopMargin());
        System.out.println("Bottom margin " + pageLayout.getBottomMargin());

        System.out.println("getFitHeight " + imageView.getFitHeight());
        System.out.println("getBottomMargin " + pageLayout.getBottomMargin());

        ;
        imageView.getFitWidth();
        printerJob.getJobSettings().setPageLayout(pageLayout);
        if(printerJob != null){
            boolean success = printerJob.printPage(imageView);
            if(success){
                printerJob.endJob();
            }
        }


    }
}
