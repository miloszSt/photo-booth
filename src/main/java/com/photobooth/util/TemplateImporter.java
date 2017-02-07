package com.photobooth.util;

import com.photobooth.templateEdytor.elements.ImageElement;
import com.photobooth.templateEdytor.serializable.ImageSerializable;
import com.photobooth.templateEdytor.serializable.PhotoSerializable;
import com.photobooth.templateEdytor.serializable.SerializableTemplateInterface;
import com.photobooth.templateEdytor.serializable.TemplateData;
import javafx.print.PageOrientation;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class TemplateImporter {



    public static TemplateData importTemplateFromDSLRBooth(String templateDirectoryPath) throws ParserConfigurationException, IOException, SAXException {
        TemplateData data = new TemplateData();



        File fXmlFile = new File(templateDirectoryPath+"\\template.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);


        Node template = doc.getElementsByTagName("Template").item(0);

        String backgroundColor = template.getAttributes().getNamedItem("BackgroundColor").getNodeValue();
        Integer resolution = Integer.valueOf(template.getAttributes().getNamedItem("Resolution").getNodeValue());
        Integer height = Integer.valueOf(template.getAttributes().getNamedItem("Height").getNodeValue());
        Integer width = Integer.valueOf(template.getAttributes().getNamedItem("Width").getNodeValue());
        String name = template.getAttributes().getNamedItem("Name").getNodeValue();




        Node elements = doc.getElementsByTagName("Elements").item(0);


        NodeList childNodes = elements.getChildNodes();

        for(int i = 0 ; i < childNodes.getLength(); i++){
            Node node = childNodes.item(i);

            String nodeName = node.getNodeName();


            switch (nodeName){
                case "Image": data.getTemplateInterfaceList().add(0,parseImageNode(node, templateDirectoryPath));
                    break;
                case "Photo": data.getTemplateInterfaceList().add(0,parsePhotoNode(node));
                    break;
//                case "Photo": data.getTemplateInterfaceList().add(parseImageNode(node));
//                    break;
//                case "Image": data.getTemplateInterfaceList().add(parseImageNode(node));
//                    break;
//                case "Image": data.getTemplateInterfaceList().add(parseImageNode(node));
//                    break;
//                case "Image": data.getTemplateInterfaceList().add(parseImageNode(node));
//                    break;
//                case "Image": data.getTemplateInterfaceList().add(parseImageNode(node));
//                    break;


            }
        }


        data.setHeight(height);
        data.setWight(width);
        data.setName(name);

        data.setOrientation(PageOrientation.PORTRAIT);

        return data;
    }

    private static SerializableTemplateInterface parsePhotoNode(Node node) {
        Integer height = Integer.valueOf(node.getAttributes().getNamedItem("Height").getNodeValue());
        Integer width = Integer.valueOf(node.getAttributes().getNamedItem("Width").getNodeValue());
        String name = node.getAttributes().getNamedItem("Name").getNodeValue();
        Integer left = Integer.valueOf(node.getAttributes().getNamedItem("Left").getNodeValue());
        Integer top = Integer.valueOf(node.getAttributes().getNamedItem("Top").getNodeValue());

        Integer photoNumber = Integer.valueOf(node.getAttributes().getNamedItem("PhotoNumber").getNodeValue());

        Integer rotation = Integer.valueOf(node.getAttributes().getNamedItem("Rotation").getNodeValue());

        PhotoSerializable photoSerializable = new PhotoSerializable(top, left, width, height, photoNumber, name);

        return photoSerializable;
    }

    private static SerializableTemplateInterface parseImageNode(Node node, String templateDirectoryPath) {
        Integer height = Integer.valueOf(node.getAttributes().getNamedItem("Height").getNodeValue());
        Integer width = Integer.valueOf(node.getAttributes().getNamedItem("Width").getNodeValue());
        String name = node.getAttributes().getNamedItem("Name").getNodeValue();
        Integer left = Integer.valueOf(node.getAttributes().getNamedItem("Left").getNodeValue());
        Integer top = Integer.valueOf(node.getAttributes().getNamedItem("Top").getNodeValue());


        String imagePath = templateDirectoryPath + "\\" + node.getAttributes().getNamedItem("ImagePath").getNodeValue();



        String opacity = node.getAttributes().getNamedItem("Opacity").getNodeValue();
        String zIndex = node.getAttributes().getNamedItem("ZIndex").getNodeValue();
        String strokeColor = node.getAttributes().getNamedItem("StrokeColor").getNodeValue();
        String thickness = node.getAttributes().getNamedItem("Thickness").getNodeValue();
        String keepAspect = node.getAttributes().getNamedItem("KeepAspect").getNodeValue();


        ImageSerializable imageSerializable = new ImageSerializable(top, left, width, height, name, imagePath);


        return imageSerializable;
    }


}

