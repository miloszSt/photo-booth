package com.photobooth.util;

import com.photobooth.templateEdytor.serializable.SerializableTemplateInterface;
import javafx.embed.swing.SwingFXUtils;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
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
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Tomasz on 21.11.2016.
 */
public class PrintUtil {

    static public void print(List<SerializableTemplateInterface> serializableTemplateInterfaces) {
        Pane pane = new Pane();
        pane.getChildren().add(new Rectangle(487,734, Color.TRANSPARENT));
        for(SerializableTemplateInterface serial : serializableTemplateInterfaces){
            pane.getChildren().add((Node) serial.toElement());
        }
        Printer printer = Printer.getDefaultPrinter();

        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
        double scaleX = pageLayout.getPrintableWidth() / pane.getBoundsInParent().getWidth();
        double scaleY = pageLayout.getPrintableHeight() / pane.getBoundsInParent().getHeight();


        WritableImage snapshot = pane.snapshot(new SnapshotParameters(), null);
        System.out.println("Snapshot height "  + snapshot.getHeight());
        System.out.println("Snapshot width "  + snapshot.getWidth());

        BufferedImage bImage = SwingFXUtils.fromFXImage(snapshot, null);


        ImageView dbImageView = new ImageView();

        try {
            ImageIO.write(bImage, "PNG", new File("c:/test/dupa.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image value = new Image(new File("c:/test/dupa.png").toURI().toString());


        System.out.println("Image height "  + value.getHeight());
        System.out.println("Image width "  + value.getWidth());


        dbImageView.setImage(value);
//                dbImageView.setFitHeight(pageLayout.getPrintableHeight());
//                dbImageView.setFitWidth(pageLayout.getPrintableWidth());

        System.out.println("dbImageView height "  + dbImageView.getFitHeight());
        System.out.println("dbImageView width "  + dbImageView.getFitWidth());


        PrinterJob printJob = PrinterJob.getPrinterJob();

        try {
            java.awt.Image image = ImageIO.read(new File("c:/test/dupa.png"));
            printJob.setPrintable(new Printable() {
                public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                    if (pageIndex != 0) {
                        return NO_SUCH_PAGE;
                    }
                    graphics.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), null);
                    return PAGE_EXISTS;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            printJob.print();
        } catch (PrinterException e1) {
            e1.printStackTrace();
        }


//                PrinterJob job = PrinterJob.createPrinterJob();
//                if (job != null && job.showPrintDialog(templateMainView.getScene().getWindow())){
////                    boolean success = job.printPage(root);
//                    boolean success = job.printPage(dbImageView);
//                    if (success) {
//                        job.endJob();
//                        System.out.print("podrukowane");
//                    }
//                }
    }

}
