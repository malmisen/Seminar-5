package serverSide.integration;

import exceptions.InvalidCustomerIDException;
import sharedDataObjects.Item;

import java.util.ArrayList;

/**
 * Class DiscountCatalog, has a list of valid customer identification numbers
 * and is able to return a totalDiscount on a purchase
 */
public class DiscountCatalog {
    private Item[] inStoreItems;
    private int[] vipCustomers = {1, 2, 3, 4};
    private double totalDiscount;

    /**
     * Create a new instance
     */
    public DiscountCatalog(Item[] inStoreItems){
        this.inStoreItems = inStoreItems;
    }

    /**
     * Method which calculates a totalDiscount
     * @param items The items to be purchased
     */
    static double setTotalDiscount(ArrayList<Item> items){
        double tempDiscount = 0;
        for (int i = 0; i < items.size(); i++) {
            tempDiscount += (items.get(i).getItemDiscount() * items.get(i).getItemPrice());
        }
        return tempDiscount;
    }

    /**
     * Method which checks the if the customer is eligible for discount
     * @param customerID The identification number of the customer
     * @param items The list of items being purchased
     * @throws InvalidCustomerIDException, thrown when the customer id is not eligible for discount.
     * @return totalDiscount, the total discount on the purchase
     */
    public double getTotalDiscount(int customerID, ArrayList<Item> items) throws InvalidCustomerIDException {

        totalDiscount = setTotalDiscount(items);

        for(int i = 0; i < vipCustomers.length; i++){
            if(customerID == vipCustomers[i]){
                return totalDiscount;
            }
        }

        System.out.println("Log: (checked exception) the customer ID is not valid.");
        throw new InvalidCustomerIDException("The customer with id: " + customerID + " Is not eligible for discount");
    }

    public double getTotalDiscountField(){
        return totalDiscount;
    }

}
