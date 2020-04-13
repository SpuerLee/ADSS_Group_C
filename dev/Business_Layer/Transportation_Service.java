package Business_Layer;

import java.util.concurrent.ConcurrentHashMap;

public class Transportation_Service {

    private static class Singelton_Transport {
        private static Transportation_Service instance = new Transportation_Service();
    }
    private Transportation_Service() {
        // initialization code..
    }
    public static Transportation_Service getInstance() {
        return Singelton_Transport.instance;
    }

    private ConcurrentHashMap<Integer,Transportation> HashTransportation= new ConcurrentHashMap<>();

}
