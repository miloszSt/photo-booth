package com.photobooth.util;

import com.photobooth.templateEdytor.serializable.SerializableTemplateInterface;
import com.photobooth.templateEdytor.serializable.TemplateData;
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
import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static java.awt.print.Printable.PAGE_EXISTS;

public class PrintUtil {
    static public void print(File file) {


    }
    static public void print(TemplateData templateData) {
        List<SerializableTemplateInterface> serializableTemplateInterfaces = templateData.getTemplateInterfaceList();

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




















    private int currentPage = -1;
    private Image cachedScaledImage = null;

    public double getScaleFactor(int iMasterSize, int iTargetSize) {
        double dScale = 1;
        if (iMasterSize > iTargetSize) {
            dScale = (double) iTargetSize / (double) iMasterSize;
        } else {
            dScale = (double) iTargetSize / (double) iMasterSize;
        }
        return dScale;
    }

    public double getScaleFactorToFit(BufferedImage img, Dimension size) {
        double dScale = 1;
        if (img != null) {
            int imageWidth = img.getWidth();
            int imageHeight = img.getHeight();
            dScale = getScaleFactorToFit(new Dimension(imageWidth, imageHeight), size);
        }
        return dScale;
    }

    public double getScaleFactorToFit(Dimension original, Dimension toFit) {
        double dScale = 1d;
        if (original != null && toFit != null) {
            double dScaleWidth = getScaleFactor(original.width, toFit.width);
            double dScaleHeight = getScaleFactor(original.height, toFit.height);

            dScale = Math.min(dScaleHeight, dScaleWidth);
        }
        return dScale;
    }

    public static void print(String filePath, PageOrientation orientation){
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        pras.add(new Copies(1));
        if(orientation == PageOrientation.LANDSCAPE){
            pras.add(OrientationRequested.LANDSCAPE);
        }else {
            pras.add(OrientationRequested.PORTRAIT);
        }
//        pras.add(new MediaPrintableArea(0,0,104,156, MediaPrintableArea.MM));
        pras.add(new MediaPrintableArea(0.0f,0.0f,104.987f,156.125f, MediaPrintableArea.MM));
        PrintService pss[] = PrintServiceLookup.lookupPrintServices(DocFlavor.INPUT_STREAM.PNG, pras);
        if (pss.length == 0)
            throw new RuntimeException("No printer services available.");
        PrintService ps = pss[0];
        System.out.println("Printing to " + ps);
        DocPrintJob job = ps.createPrintJob();
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(filePath);
            Doc doc = new SimpleDoc(fin, DocFlavor.INPUT_STREAM.PNG, null);
            job.print(doc, pras);
            fin.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (PrintException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static private PageFormat getMinimumMarginPageFormat(PrinterJob printJob) {
        PageFormat pf0 = printJob.defaultPage();
        PageFormat pf1 = (PageFormat) pf0.clone();
        java.awt.print.Paper p = pf0.getPaper();
        p.setImageableArea(0, 0,pf0.getWidth(), pf0.getHeight());
        pf1.setPaper(p);
        PageFormat pf2 = printJob.validatePage(pf1);
        return pf2;
    }

}
