/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Package1;

import java.util.ArrayList;

/**
 *
 * @author Jie Shen
 */
public class Stall {

    private String stallId;
    private String stallName;
    private StallType stallType;

    private final ArrayList<Slot> SLOTS = new ArrayList<>();
    private final ArrayList<Product> PRODUCTS = new ArrayList<>();

    private static int idCount = 0;

    public Stall() {
        this("Unknown", "Unknown", StallType.FOOD);
    }

    public Stall(String lastName, String stallName, StallType stallType) {
        stallId = lastName + "ST" + ++idCount;
        this.stallName = stallName;
        this.stallType = stallType;
    }

    public void setStallName(String stallName) {
        this.stallName = stallName;
    }

    public void setStallType(StallType stallType) {
        this.stallType = stallType;
    }

    public String getStallId() {
        return stallId;
    }

    public String getStallName() {
        return stallName;
    }

    public ArrayList<Slot> getSlots() {
        return this.SLOTS;
    }

    public ArrayList<Product> getProducts() {
        return this.PRODUCTS;
    }

    public StallType getStallType() {
        return stallType;
    }

    public void rentSlot(Slot slot) {
        SLOTS.add(slot);
    }

    public void addProduct(Product product) {
        PRODUCTS.add(product);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("\n%7s%-17s%2s", "", "Stall ID", ": ")).append(stallId)
                .append(String.format("\n%7s%-17s%2s", "", "Stall Name", ": ")).append(stallName)
                .append(String.format("\n%7s%-17s%2s", "", "Stall Type", ": ")).append(stallType)
                .append(String.format("\n%7s%-17s%2s", "", "Slots", ": "));
        for (Slot slot : SLOTS) {
            sb.append(slot.toString());
        }
        sb.append(String.format("\n%7s%-17s%2s","","Assigned Products",": "));
        for (Product product : PRODUCTS) {
            sb.append(product.toString());
        }
        return sb.toString();
    }

    public Product[] displayMenu() {
        Product[] products = new Product[this.PRODUCTS.size()];
        this.PRODUCTS.toArray(products);

        System.out.println();
        System.out.println("Welcome to " + this.stallName);
        System.out.println();
        System.out.println(String.format("%-5s%-20s%-20s%-7s", "", "Product Name", "Product Description", "Price"));
        for (int i = 0; i < products.length; i++) {
            Product product = products[i];
            System.out.println(String.format("%-5s%-20s%-20s%-7s",
                    String.format("[%d]", (i + 1)),
                    product.getProductName(),
                    product.getDescription(),
                    String.format("RM%.2f", product.getUnitPrice())));
        }
        System.out.println();
        System.out.println("Press [0] to BACK");

        return products;
    }
}
