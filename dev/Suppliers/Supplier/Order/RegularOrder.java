package Suppliers.Supplier.Order;

import Suppliers.Supplier.ProductInOrder;

import java.util.List;

public class RegularOrder extends Order {

    protected RegularOrder(int orderId, List<ProductInOrder> products, int shopNumber){
        super(orderId, products, shopNumber);
    }

    public static RegularOrder CreateRegularOrder(int orderId, List<ProductInOrder> productsInOrder, int shopNumber){
        if(productsInOrder.isEmpty()){
            return null;
        }

        return new RegularOrder(orderId, productsInOrder, shopNumber);
    }
    public void setOrderId(int orderId){
        this.orderId = orderId;
    }

}