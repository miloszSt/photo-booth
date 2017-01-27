package com.photobooth;

import com.photobooth.util.PrintUtil;
import javafx.print.PageOrientation;

import javax.imageio.ImageIO;
import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Tomasz on 02.01.2017.
 */
public class PrintTest03 {

    private static BufferedImage image;

    public static void main(String[] args) {
        print("C:\\photoBooth\\temp\\2016-12-01T20.png", PageOrientation.PORTRAIT);
    }

    public static void print(String filePath, PageOrientation orientation){
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        pras.add(new Copies(1));
        pras.add(OrientationRequested.PORTRAIT);
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


    protected static double fromPPItoCM(double dpi) {
        return dpi / 72 / 0.393700787;
    }

    protected static double fromCMToPPI(double cm) {
        return toPPI(cm * 0.393700787);
    }

    protected static double toPPI(double inch) {
        return inch * 72d;
    }

}
