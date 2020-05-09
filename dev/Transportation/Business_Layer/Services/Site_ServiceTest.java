//package Transportation.Business_Layer.Services;
//
//import Transportation.Business_Layer.*;
//import org.junit.Assert;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.hamcrest.core.StringContains.containsString;
//import static org.junit.jupiter.api.Assertions.*;
//
//class Site_ServiceTest {
//
//    Service service;
//    Site_Service site_service;
//    Supplier site;
//    Store site1;
//    Store site2;
//    @BeforeEach
//    void setUp() {
//        service=Service.getInstance();
//        site_service=Site_Service.getInstance();
//        site1=new Store("store1","0546343156","reut",new Address("Beer-Sheva","Rager",12), new Area("A"));
//        service.getHashStoresMap().put(site1.getId(),site1);
//        site2=new Store("store2","0546343167","reut1",new Address("Haifa","Kadesh",18), new Area("B"));
//        service.getHashStoresMap().put(site2.getId(),site2);
//        site=new Supplier("Osem","0546323167","amit",new Address("Tel-Aviv","Yaacov-Cohen",3), new Area("C"));
//        service.getSuppliersMap().put(site.getId(),site);
//    }
//
//    @Test
//    void addsite() {
//        int size=service.getSuppliersMap().size();
//        site_service.addsite("supplier","neviot","Tel-Aviv","Shlomo-Hamelech","8","mor","0546713243","C");
//        assertEquals(size+1,service.getSuppliersMap().size());
//    }
//
//    @Test
//    void getSuppliersbyarea() {
//        String result=site_service.getSuppliersbyarea("C");
//        Assert.assertThat(result,containsString("Osem"));
//
//    }
//
//    @Test
//    void get_Stores_By_specific_area() {
//        String result=site_service.get_Stores_By_specific_area("B");
//        Assert.assertThat(result,containsString("store2"));
//    }
//}