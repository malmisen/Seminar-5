package sharedDataObjects;

import java.util.ArrayList;

public class SaleInfoDTO {
    private double totalPrice;
    private double changeAmount;
    private ArrayList<Item> itemsBought;

    public SaleInfoDTO(double totalPrice, double changeAmount, ArrayList<Item> itemsBought){
        this.totalPrice = totalPrice;
        this.changeAmount = changeAmount;
        this.itemsBought = itemsBought;
    }
}
