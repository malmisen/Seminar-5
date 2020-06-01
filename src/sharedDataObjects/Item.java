package sharedDataObjects;

import java.io.Serializable;
/**
 * Class Item, holds the data which belongs to an item
 */
public class Item implements Serializable {
    private int itemID;
    private String itemName;
    private double itemPrice;
    private double itemDiscount;
    private double itemVAT;
    private int itemQuantity;

    /**
     * Creates new instance
     */
    public Item(int itemID, String itemName, double itemPrice, double itemDiscount, double itemVAT, int itemQuantity) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemDiscount = itemDiscount;
        this.itemVAT = itemVAT;
        this.itemQuantity = itemQuantity;
    }

    public int getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public double getItemDiscount() {
        return itemDiscount;
    }

    public double getItemVAT() {
        return itemVAT;
    }

    public int getItemQuantity(){
        return itemQuantity;
    }

    public void setQuantity(int i) {
        itemQuantity = i;
    }
}
