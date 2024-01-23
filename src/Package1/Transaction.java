/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Package1;

/**
 *
 * @author Jie Shen
 */
public class Transaction {

    private int id;
    private String productId;
    private int quantity;

    private static int idCount = 1;

    public Transaction() {
    }

    public Transaction(String productId, int quantity) {
        this.id = idCount++;
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getProductId() {
        return this.productId;
    }

    public int getQuantity() {
        return this.quantity;
    }

}
