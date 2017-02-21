package com.photobooth.util;


import javafx.scene.paint.Color;

/**
 * Created by Tomasz on 21.02.2017.
 */
public class ColorUtils {


    static public Color parseStringToColor(String hashColorString){
        String colorString = hashColorString.substring(1);
        String alfa = colorString.substring(0, 2);
        String red = colorString.substring(2, 4);
        String green = colorString.substring(4, 6);
        String blue = colorString.substring(6);

        Long alfaLong = Long.parseLong(alfa, 16);
        Long redLong = Long.parseLong(red, 16);
        Long greenLong = Long.parseLong(green, 16);
        Long blueLong = Long.parseLong(blue, 16);

        float redFloat = (float) (redLong / 255.0);
        float greenFloat = (float) (greenLong / 255.0);
        float blueFloat = (float) (blueLong / 255.0);
        float alfaFloat = (float) (alfaLong / 255.0);
        return new Color(redFloat, greenFloat, blueFloat, alfaFloat);
    }
}
