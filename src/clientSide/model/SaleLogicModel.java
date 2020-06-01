package clientSide.model;

import java.beans.PropertyChangeListener;

public interface SaleLogicModel {
    void addPropertyChangeListener(String eventName, PropertyChangeListener listener);
    void addItemToCart(String itemName, int quantity);
    void requestingDiscount(String value);
    void endSale(String value);
}
