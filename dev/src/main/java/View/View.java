package View;

import Interfaces.Observer;

public class View implements Observer
{
    public View(){}

    @Override
    public void onEvent(String msg) {
        System.out.println(msg);
    }
}
