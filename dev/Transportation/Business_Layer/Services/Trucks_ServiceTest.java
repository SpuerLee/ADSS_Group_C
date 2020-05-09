//package Transportation.Business_Layer.Services;
//
//import Transportation.Business_Layer.Buisness_Exception;
//import Transportation.Business_Layer.License;
//import Transportation.Business_Layer.Truck;
//import org.junit.Assert;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.LinkedList;
//import java.util.List;
//
//import static org.hamcrest.core.StringContains.containsString;
//import static org.junit.jupiter.api.Assertions.*;
//
//class Trucks_ServiceTest {
//    Service service;
//    Trucks_Service trucks_service;
//    Truck trucks;
//    Truck trucks1;
//    Truck trucks2;
//
//    @BeforeEach
//    void setup(){
//        service=Service.getInstance();
//        trucks_service= Trucks_Service.getInstance();
//        List<License> licenses=new LinkedList<>();
//        licenses.add(new License("C"));
//        trucks=new Truck(12345678,licenses,"skoda",180,300);
//        service.getHashTrucks().put(trucks.getId(),trucks);
//        licenses.add(new License("C1"));
//        trucks1=new Truck(12345867,licenses,"picanto",200,450);
//        service.getHashTrucks().put(trucks1.getId(),trucks1);
//    }
//
//    @Test
//    void showtrucks() {
//        String result="";
//        try{
//            result=trucks_service.showtrucks();
//        }
//        catch (Buisness_Exception e){
//            Assert.assertThat(result,containsString("12345678"));
//        }
//
//    }
//
//    @Test
//    void addTruck() {
//        int size=service.getHashTrucks().size();
//        List<String> list=new LinkedList<>();
//        list.add("C1");
//
//        try {
//            trucks_service.addTruck("12356793",list,"skoda","350.5","465.8");
//        }
//        catch (Buisness_Exception e){
//            e.printStackTrace();
//        }
//        assertEquals(size+1,service.getHashTrucks().size());
//    }
//
//    @Test
//    void removeTruck() {
//        int size=service.getHashTrucks().size();
//
//        try {
//            trucks_service.removeTruck(1);
//        }
//        catch (Buisness_Exception e){
//            e.printStackTrace();
//        }
//        assertEquals(size-1,service.getHashTrucks().size());
//    }
//}