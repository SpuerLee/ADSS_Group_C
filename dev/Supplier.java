import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.Map;

public class Supplier {

    private int supId;
    private int incNum;
    private String accountNumber;
    private String paymentInfo;

    //Supplier Details
    private String name;

    private List<ContactInfo> contacts;
    private ContractWithSupplier contract;

    // TODO: Consider making this constructor private as you need to verify the data e.g
    //  the incNum is bigger than 0. Then if you make it private create a static constructor.
    public Supplier(String name, int incNum, String accountNumber, String paymentInfo){

    }

    public boolean addContactInfo(String name, String phone, String email){
        throw new NotImplementedException();
    }

    /**
     *
     * @param contractDetails
     * @param days
     * @param products
     * @return Map from catId to productID
     */
    public Map<Integer, Integer> addContractInfo(String contractDetails, List<Days> days, List<AddProductInfo> products){
        throw new NotImplementedException();
    }

    /**
     *
     * @param product
     * @return ProductId in the system
     */
    public int addProduct(AddProductInfo product){
        throw new NotImplementedException();
    }

    public List<ProductDiscount> getAmountDiscountReport(int supId){
        throw new NotImplementedException();
    }

    // TODO: not in class diagram
    public int getSupId(){
        return supId;
    }

    // TODO: not in class diagram
    public String getPaymentInfo(){
        return paymentInfo;
    }

    // TODO: not in class diagram
    public String getSupplierName(){
        return name;
    }
}
