package Interfaces;

public interface myObservable {

    void register(Observer o);

    void notifyObserver(String msg);
}
