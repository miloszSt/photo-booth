package com.photobooth.templateEdytor.serializable;

import javafx.print.Paper;

public enum PaperSizeEnum {

    A0,A1,A2,A3,A4,A5,B4,B5,B6;

    PaperSizeEnum(){

    }

    public static PaperSizeEnum getFromPaper(Paper paper){
        switch (paper.getName()){
            case "A0": return A0;
            case "A1": return A1;
            case "A2": return A2;
            case "A3": return A3;
            case "A4": return A4;
            case "A5": return A5;
            case "B4": return B4;
            case "B5": return B5;
            case "B6": return B6;
        }
        return null;
    }

    public Paper toPaper(){
        switch (this){
            case A0:
                return Paper.A0;
            case A1:
                return Paper.A1;
            case A2:
                return Paper.A2;
            case A3:
                return Paper.A3;
            case A4:
                return Paper.A4;
            case A5:
                return Paper.A5;
            case B4:
                return Paper.JIS_B4;
            case B5:
                return Paper.JIS_B5;
            case B6:
                return Paper.JIS_B6;
        }

        return null;
    }


}


/*


Paper.A0,Paper.A1, Paper.A2, Paper.A3,
                        Paper.A4, Paper.A5, Paper.JIS_B4, Paper.JIS_B5, Paper.JIS_B6
 */