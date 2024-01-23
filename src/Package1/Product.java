/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Package1;

/**
 *
 * @author Jie Shen
 */
public class Product {

    private String productId;
    private String productName;
    private double unitPrice;
    private String description;

    private int stock;

    private static int idCount = 0;

    public Product() {
        this("unknown", "unknown", 0, 0, "Unknown");
    }

    public Product(String username, String productName, double unitPrice, int stock, String description) {
        idCount++;
        productId = username + "P" + idCount;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.stock = stock;
        this.description = description;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setProductDescription(String description) {
        this.description = description;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getStock() {
        return stock;
    }

    public String getDescription() {
        return description;
    }

    //When product is assigned to a stall, productId need to be updated
    public void updateProductId(String stallId) {
        productId = productId + stallId.substring(stallId.length() - 3);
    }

    @Override
    public String toString() {
        return String.format("\n%26s%-19s%2s%s", "", "Product Id", ": ", this.productId)
                + String.format("\n%26s%-19s%2s%s", "", "Product Name", ": ", this.productName)
                + String.format("\n%26s%-19s%2s%s", "", "Product Description", ": ", this.description)
                + String.format("\n%26s%-19s%4s%s", "", "Product Unit Price", ": RM", String.format("%.2f", unitPrice))
                + String.format("\n%26s%-19s%2s%s\n", "", "Product Stock", ": ", this.stock);
    }
}
