package Service;

public class SupplierProductDTO {

    private String productCatalogNumber;
    private int productID;

    public SupplierProductDTO(int productID, String productCatalogNumber) {
        this.productCatalogNumber = productCatalogNumber;
        this.productID = productID;
    }

    public String toString(){
        return String.format("Product id : %d, Catalog number : %d", productID, productCatalogNumber);
    }
}