package Service;

public class SupplierProductDTO {

    private int productCatalogNumber;
    private int productID;

    public SupplierProductDTO(int productID, int productCatalogNumber) {
        this.productCatalogNumber = productCatalogNumber;
        this.productID = productID;
    }
}
