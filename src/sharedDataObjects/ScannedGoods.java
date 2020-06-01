package sharedDataObjects;

import exceptions.InsufficientFundsException;

import java.util.ArrayList;
/**
 * A Class which represents a list of registered items. Items which are ready to be purchased,
 */

public class ScannedGoods {
    private ArrayList<Item> items;
    private double runningTotal = 0;

    /**
     * Creates a new instance
     */
    public ScannedGoods(){
        items = new ArrayList<>();
    }

    /**
     * Add item to the list of registered items
     *
     * @param item The item to be added
     * @param quantity The quantity of the item to be added
     */
    public void addItem(Item item, int quantity) {
        int counter = 0;
        while(counter < quantity){
            items.add(item);
            counter++;
        }

        runningTotal += item.getItemPrice() * quantity;
    }

    /**
     * Method which calculates the change the customer is owed.
     * @param payment The amount paid by the customer.
     * @throws InsufficientFundsException Thrown when the amount paid by the customer is less than the totalPrice.
     *
     * @return change, The difference between the amount paid and the totalPrice.
     */
    public double calculateChange(double payment) throws InsufficientFundsException {
        if(payment < runningTotal){
            System.out.println("Log: (checked exception) The customer attempted to pay with an insufficient amount of money");
            throw new InsufficientFundsException("I regret to inform that " + payment + " is not enough money...");
        }

        return payment - runningTotal;
    }

    /**
     * @return items The list of items to be purchased
     */
    public ArrayList<Item> getList() {
        return items;
    }
    /**
     * @param price The price which the runningTotal should be set to.
     */
    public void setTotalPrice(double price){
        runningTotal = price;
    }
    /**
     * @return runningTotal The current total price of the registered items
     */
    public double getRunningTotal(){
        return runningTotal;
    }

}