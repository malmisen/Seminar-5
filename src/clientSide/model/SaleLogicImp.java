package clientSide.model;

import exceptions.CouldNotConnectToDatabaseException;
import exceptions.FetchItemException;
import exceptions.InsufficientFundsException;
import exceptions.InvalidCustomerIDException;
import serverSide.externalSystems.SaleLog;
import serverSide.integration.DiscountCatalog;
import serverSide.integration.ItemRegistry;
import sharedDataObjects.Item;
import sharedDataObjects.SaleInfoDTO;
import sharedDataObjects.ScannedGoods;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
* A class which implements the interface which contains the business logic
* fire's propertyChangeEvents which the viewModel is observing.
*
*/
public class SaleLogicImp implements SaleLogicModel {

    private ItemRegistry registry;
    private DiscountCatalog catalog;
    private ScannedGoods cart = new ScannedGoods();
    private SaleLog log;

    private PropertyChangeSupport support = new PropertyChangeSupport(this);
    /**
    * Creates an instance
    * @param registry The registry which holds all the items available to purchase
    * @param catalog The catalog calculates discount and holds information regarding discount eligible customers
    */
    public SaleLogicImp(ItemRegistry registry, DiscountCatalog catalog){
        this.registry = registry;
        this.catalog = catalog;
    }
    /**
    * Method used to add listener
    * @param eventName The name of the event
    * @param listener The listener
    */
    @Override
    public void addPropertyChangeListener(String eventName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(eventName, listener);
    }
    /**
    * Methods which attempts to add a desired item to the customer's shopping list
    * @param itemName The name of the item to be added
     * @param quantity The quantity of the desired item
    */
    @Override
    public void addItemToCart(String itemName, int quantity){
        try{
            Item item = registry.fetchItem(itemName, quantity);
            cart.addItem(item, quantity);
            support.firePropertyChange("ItemFound", null, item);
        }catch(FetchItemException | CouldNotConnectToDatabaseException exception){
            support.firePropertyChange("ItemNotFound", null, exception.getMessage());
        }
    }
    /**
     * Method which attempts to retrieve a discount depending on the customerID
    * @param customerID The identification number of the customer
    */
    @Override
    public void requestingDiscount(String customerID) {
            try{
                double discount = catalog.getTotalDiscount(Integer.parseInt(customerID), cart.getList());
                cart.setTotalPrice(cart.getRunningTotal() - discount);
                support.firePropertyChange("CustomerHasDiscount", null, discount);
            }catch(InvalidCustomerIDException e){
                support.firePropertyChange("InvalidCustomerID", null, e.getMessage());
            }
    }

    /**
    * Method which attempts to retrieve the change the customer is owed
    * if the change is successfully acquired the method gets an instance of SaleLog and updates the list of sale information.
    * @param amountPaid The amount paid by the customer
    */
    @Override
    public void endSale(String amountPaid) {
            try{
               double change = cart.calculateChange(Double.parseDouble(amountPaid));
               SaleInfoDTO saleInfo = new SaleInfoDTO(cart.getRunningTotal(), change, cart.getList());
               log.getInstance().addSaleInfoToLog(saleInfo);
               support.firePropertyChange("ChangeHasBeenCalculated", null, change);
            }catch(InsufficientFundsException e){
                support.firePropertyChange("NotEnoughMoney", null, e.getMessage());
            }
    }
}
