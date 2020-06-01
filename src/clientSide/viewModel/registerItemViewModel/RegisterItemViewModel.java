package clientSide.viewModel.registerItemViewModel;

import clientSide.model.SaleLogicModel;
import clientSide.view.ViewHandler;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sharedDataObjects.Item;

import java.beans.PropertyChangeEvent;

/**
 * This class is responsible for setting the values presented in the view.
 * The instance variables of this class are data-bound with the view's.
 * The class makes method calls on the model. It is also the object which listens to fired events done by the model.
 */
public class RegisterItemViewModel {

    private SaleLogicModel model;
    private ViewHandler viewHandler;

    private StringProperty itemQuantity;
    private StringProperty errorMessage;
    private StringProperty totalVAT;
    private StringProperty totalPrice;
    private StringProperty customerID;
    private StringProperty amountPaid;
    private StringProperty changeAmount;
    private ObservableList<String> items;

    /**
     * Creates a new instance
     * @param model The model which contains all the business logic.
     * @param viewHandler The class which is responsible for presenting the different views.
     */
    public RegisterItemViewModel(SaleLogicModel model, ViewHandler viewHandler){
        this.model = model;
        this.viewHandler = viewHandler;
        itemQuantity = new SimpleStringProperty();
        errorMessage = new SimpleStringProperty();
        totalPrice = new SimpleStringProperty();
        totalVAT = new SimpleStringProperty();
        customerID = new SimpleStringProperty();
        amountPaid = new SimpleStringProperty();
        changeAmount = new SimpleStringProperty();
        items = FXCollections.observableArrayList();

        model.addPropertyChangeListener("ItemFound", this::updateSaleInfo);
        model.addPropertyChangeListener("ItemFound", this::updateCartView);
        model.addPropertyChangeListener("CustomerHasDiscount", this::applyDiscount);
        model.addPropertyChangeListener("ChangeHasBeenCalculated", this::returnChange);
        model.addPropertyChangeListener("ItemNotFound", this::updateErrorMessage);
        model.addPropertyChangeListener("NotEnoughMoney", this::updateErrorMessage);
        model.addPropertyChangeListener("InvalidCustomerID", this::updateErrorMessage);

    }

    /**
     * Method which sets the change value owed to the customer.
     * Platform.runLater is a piece of code that can be applied to javaFX elements. It allows me to update GUI components from a non-GUI thread.
     * The desired update will be put in a queue and handled by a GUI thread as soon as possible.
     *
     * @param evt Contains the old and the new value of the event fired by the model.
     */
    private void returnChange(PropertyChangeEvent evt){
        Platform.runLater(() -> changeAmount.setValue(String.valueOf(evt.getNewValue())));
    }

    /**
     * Method which sets the value of the error message.
     * @param evt Contains the old and the new value of the event fired by the model.
     */
    private void updateErrorMessage(PropertyChangeEvent evt){
        Platform.runLater(() -> errorMessage.setValue(String.valueOf(evt.getNewValue())));
    }

    /**
     * Method which sets the value of the total price after applying the discount
     * @param evt Contains the old and the new value of the event fired by the model.
     */
    private void applyDiscount(PropertyChangeEvent evt){
        double discount = (double)evt.getNewValue();
        Platform.runLater(() -> totalPrice.setValue(String.valueOf(Double.parseDouble(totalPrice.getValue()) - discount)));
    }

    /**
     * Method which adds an item to the list of items presented in the view.
     * @param evt Contains the old and the new value of the event fired by the model.
     */
    private void updateCartView(PropertyChangeEvent evt){
        Item item = (Item)evt.getNewValue();
        Platform.runLater(()-> items.add(itemQuantity.getValue() +"x " + item.getItemName()));
    }

    /**
     * Method which updates the totalPrice with the price of the added item.
     * @param evt Contains the old and the new value of the event fired by the model.
     */
    private void updateSaleInfo(PropertyChangeEvent evt){
        Item item = (Item)evt.getNewValue();
        double price = item.getItemPrice() * Integer.parseInt(itemQuantity.getValue());
        double currentPrice = Double.parseDouble(totalPrice.getValue());
        Platform.runLater(()-> totalPrice.setValue(String.valueOf(price + currentPrice)));
    }

    /**
     * Method which requests the model to add an item to the list scanned items.
     * @param itemName The name of the item to be added.
     */
    public void registerItem(String itemName) {
        model.addItemToCart(itemName, Integer.parseInt(itemQuantity.getValue()));
    }
    /**
     * Method which requests the model to fetch a discount on the total price.
     */
    public void handleDiscount() {
        model.requestingDiscount(customerID.getValue());
    }
    /**
     * Method which requests the model to calculate the change owed to the customer.
     */
    public void completePurchase() {
        model.endSale(amountPaid.getValue());
    }

    public StringProperty itemQuantityProperty() {
        return itemQuantity;
    }

    public StringProperty errorMessageProperty() {
        return errorMessage;
    }

    public StringProperty totalPriceProperty() {
        return totalPrice;
    }

    public StringProperty totalVATProperty() {
        return totalVAT;
    }

    public ObservableList<String> itemListProperty() {
        return items;
    }

    public StringProperty customerIDProperty() {
        return customerID;
    }

    public StringProperty paymentAmountProperty() {
        return amountPaid;
    }

    public StringProperty changeAmountProperty() {
        return changeAmount;
    }

}
