package Workers.PresentationLayer;

import Workers.BusinessLayer.Utils.enums;
import Workers.InterfaceLayer.HR;

import java.text.ParseException;


public class Main {
    public static void main(String[] args){
        HR hr = new HR();
        try {
            hr.start();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
