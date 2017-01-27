package com.photobooth.util;

import com.photobooth.templateEdytor.serializable.TemplateData;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class TemplateImporter {



    public static TemplateData importTemplateFromDSLRBooth(String path) throws ParserConfigurationException, IOException, SAXException {
        TemplateData data = new TemplateData();

        File fXmlFile = new File(path);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);

        return data;
    }


}
