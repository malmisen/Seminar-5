package serverSide.integration;

import exceptions.CouldNotConnectToDatabaseException;
import exceptions.FetchItemException;
import sharedDataObjects.Item;

/**
 * A class which represents a list which holds all the items that can be purchased.
 */
public class ItemRegistry {
    private Item[] items;

    /**
     * Creates a new instance
     * @param items The list of items which are in store
     */
    public ItemRegistry(Item[] items){
        this.items = items;
    }

    /**
     * A method which fetches the desired item from the list of available items.
     * @param itemName The name of the item to be fetched.
     * @param quantity The quantity of the item to be fetched.
     * @throws FetchItemException Thrown when the desired quantity of an item is too large or is less than/equals 0.
     *
     * @return item. The desired item.
     */
    public Item fetchItem(String itemName, int quantity) throws FetchItemException, CouldNotConnectToDatabaseException {

        Boolean found = false;
        Boolean isQuantityFetchable = false;
        if(quantity <= 0){
            System.out.println("Log: (checked exception) Customer attempted to request 0 of an item");
            throw new FetchItemException("So let me get this straight, you want to purchase " + quantity + " " + itemName + "s ?");
        }

        Item item = null;
        for (int i = 0; i < items.length; i++) {

            if(itemName.equals(items[i].getItemName()))
            {
                found = true;
                if(items[i].getItemQuantity() >= quantity){
                    isQuantityFetchable = true;
                    item = items[i];
                    items[i].setQuantity(items[i].getItemQuantity() - quantity);
                    break;
                }
            }
        }

        if(!found){
            System.out.println("Log: (unchecked exception) Could not connect to database when attempting to fetch item: " + itemName);
            throw new CouldNotConnectToDatabaseException("Woops, network error occurred. Please try again.");
        }
        if(!isQuantityFetchable){
            System.out.println("Log: (checked exception) The customer wanted a too large quantity of an item");
            throw new FetchItemException("The quantity: " + quantity + " is too large");
        }

        return item;
    }

}
