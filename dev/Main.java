import java.text.ParseException;
import java.util.Date;

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
