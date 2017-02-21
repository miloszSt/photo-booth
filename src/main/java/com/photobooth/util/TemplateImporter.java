package com.photobooth.util;

import com.photobooth.templateEdytor.elements.ImageElement;
import com.photobooth.templateEdytor.serializable.*;
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
        data.setBackgroundColor(template.getAttributes().getNamedItem("BackgroundColor").getNodeValue());



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
                case "Text": data.getTemplateInterfaceList().add(0,parseTextNode(node));
                    break;
                case "Rectangle": data.getTemplateInterfaceList().add(0,parseRectangleNode(node));
                    break;
                case "Ellipse": data.getTemplateInterfaceList().add(0,parseCircleNode(node));
                    break;
//                case "Image": data.getTemplateInterfaceList().add(parseImageNode(node));
//                    break;
//                case "Image": data.getTemplateInterfaceList().add(parseImageNode(node));
//                    break;


            }
        }



        data.getTemplateInterfaceList().add(0,new RectangleSerializable(backgroundColor,height,width,"background",0,0,"#FF000000",0));
        data.setHeight(height);
        data.setWight(width);
        data.setName(name);


        data.setOrientation(PageOrientation.PORTRAIT);

        return data;
    }

    private static SerializableTemplateInterface parseCircleNode(Node node) {
        String backgroundColor = node.getAttributes().getNamedItem("BackgroundColor").getNodeValue();
        Integer height = Integer.valueOf(node.getAttributes().getNamedItem("Height").getNodeValue());
        Integer width = Integer.valueOf(node.getAttributes().getNamedItem("Width").getNodeValue());
        String name = node.getAttributes().getNamedItem("Name").getNodeValue();
        Integer left = Integer.valueOf(node.getAttributes().getNamedItem("Left").getNodeValue());
        Integer top = Integer.valueOf(node.getAttributes().getNamedItem("Top").getNodeValue());

//        Integer rotation = Integer.valueOf(node.getAttributes().getNamedItem("Rotation").getNodeValue());
        String color = node.getAttributes().getNamedItem("Color").getNodeValue();

        Integer thickness = Integer.valueOf(node.getAttributes().getNamedItem("Thickness").getNodeValue());

        return new CircleSerializable(backgroundColor, height, width, name, left, top, color, thickness);

    }

    private static SerializableTemplateInterface parseRectangleNode(Node node) {
        String backgroundColor = node.getAttributes().getNamedItem("BackgroundColor").getNodeValue();
        Integer height = Integer.valueOf(node.getAttributes().getNamedItem("Height").getNodeValue());
        Integer width = Integer.valueOf(node.getAttributes().getNamedItem("Width").getNodeValue());
        String name = node.getAttributes().getNamedItem("Name").getNodeValue();
        Integer left = Integer.valueOf(node.getAttributes().getNamedItem("Left").getNodeValue());
        Integer top = Integer.valueOf(node.getAttributes().getNamedItem("Top").getNodeValue());

//        Integer rotation = Integer.valueOf(node.getAttributes().getNamedItem("Rotation").getNodeValue());
        String color = node.getAttributes().getNamedItem("Color").getNodeValue();

        Integer thickness = Integer.valueOf(node.getAttributes().getNamedItem("Thickness").getNodeValue());



        return new RectangleSerializable(backgroundColor, height, width, name, left, top, color, thickness);
    }

    private static SerializableTemplateInterface parseTextNode(Node node) {
        Integer height = Integer.valueOf(node.getAttributes().getNamedItem("Height").getNodeValue());
        Integer width = Integer.valueOf(node.getAttributes().getNamedItem("Width").getNodeValue());
        String name = node.getAttributes().getNamedItem("Name").getNodeValue();
        Integer left = Integer.valueOf(node.getAttributes().getNamedItem("Left").getNodeValue());
        Integer top = Integer.valueOf(node.getAttributes().getNamedItem("Top").getNodeValue());

//        Integer rotation = Integer.valueOf(node.getAttributes().getNamedItem("Rotation").getNodeValue());
        String color = node.getAttributes().getNamedItem("FontColor").getNodeValue();

        Integer textSize = Integer.valueOf(node.getAttributes().getNamedItem("FontSize").getNodeValue());
        String textColor = node.getAttributes().getNamedItem("FontColor").getNodeValue();
        String textValue = node.getAttributes().getNamedItem("Text").getNodeValue();
        Boolean isItalic = Boolean.valueOf(node.getAttributes().getNamedItem("IsItalic").getNodeValue());
        Boolean isBold = Boolean.valueOf(node.getAttributes().getNamedItem("IsBold").getNodeValue());
        String fontName = node.getAttributes().getNamedItem("FontName").getNodeValue();
        TextSerializable textSerializable = new TextSerializable(top, left, width, height,
                name, color, textSize, textColor, textValue, isItalic, isBold, fontName);

        return textSerializable;

    }

    private static SerializableTemplateInterface parsePhotoNode(Node node) {
        Integer height = Integer.valueOf(node.getAttributes().getNamedItem("Height").getNodeValue());
        Integer width = Integer.valueOf(node.getAttributes().getNamedItem("Width").getNodeValue());
        String name = node.getAttributes().getNamedItem("Name").getNodeValue();
        Integer left = Integer.valueOf(node.getAttributes().getNamedItem("Left").getNodeValue());
        Integer top = Integer.valueOf(node.getAttributes().getNamedItem("Top").getNodeValue());

        Integer photoNumber = Integer.valueOf(node.getAttributes().getNamedItem("PhotoNumber").getNodeValue());

        Integer rotation = Integer.valueOf(node.getAttributes().getNamedItem("Rotation").getNodeValue());

        PhotoSerializable photoSerializable = new PhotoSerializable(top, left, width, height, photoNumber, name, rotation);

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
        Integer thickness = Integer.valueOf(node.getAttributes().getNamedItem("Thickness").getNodeValue());
        String keepAspect = node.getAttributes().getNamedItem("KeepAspect").getNodeValue();


        ImageSerializable imageSerializable = new ImageSerializable(top, left, width, height, name, imagePath, thickness, strokeColor);


        return imageSerializable;
    }


}

