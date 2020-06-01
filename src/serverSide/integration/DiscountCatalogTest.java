package serverSide.integration;


import exceptions.InvalidCustomerIDException;
import org.junit.Before;
import org.junit.Test;
import sharedDataObjects.Item;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class DiscountCatalogTest {

    private Item apple;
    private Item tomato;
    private DiscountCatalog catalog;
    private ArrayList<Item> scannedItems;

    @Before
    public void setUp(){
        apple = new Item(1, "apple", 15, 0, 0.1 , 5);
        tomato = new Item(2, "tomato", 7, 0.10, 0, 10);
        Item burger = new Item(1, "burger", 40, 0.01, 0.1 , 5);
        Item[] items = {apple, tomato, burger};
        catalog = new DiscountCatalog(items);

        scannedItems = new ArrayList<>();
        scannedItems.add(apple);
    }

    @Test(expected = NullPointerException.class)
    public void testNullItems() throws InvalidCustomerIDException{
        catalog.getTotalDiscount(1, null);
    }

    @Test
    public void testSetDiscount(){
        scannedItems.add(tomato);

        double disc = DiscountCatalog.setTotalDiscount(scannedItems);
        assertTrue(disc != 0);

    }

    @Test (expected = InvalidCustomerIDException.class)
    public void testInvalidCustomerID() throws InvalidCustomerIDException{
        catalog.getTotalDiscount(0, scannedItems);
    }

    @Test
    public void testValidCustomerID() throws InvalidCustomerIDException{
        catalog.getTotalDiscount(1, scannedItems);
    }

    @Test
    public void ensureDiscountReturnedIsTheSameAsDiscountSet() throws InvalidCustomerIDException{
        scannedItems.add(tomato);
        double temp = DiscountCatalog.setTotalDiscount(scannedItems);
        double discount = catalog.getTotalDiscount(1, scannedItems);
        assertTrue(temp == discount);
    }

    @Test
    public void testTotalDiscountField() throws InvalidCustomerIDException{
        scannedItems.add(tomato);
        double discount = catalog.getTotalDiscount(1, scannedItems);
        double temp = catalog.getTotalDiscountField();
        assertTrue(discount == temp);
    }
}
