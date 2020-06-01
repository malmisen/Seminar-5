import exceptions.InsufficientFundsException;
import org.junit.Before;
import org.junit.Test;
import sharedDataObjects.Item;
import sharedDataObjects.ScannedGoods;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScannedGoodsTest {

    private ScannedGoods goods;
    private Item apple;


    @Before
    public void setUp(){
        apple = new Item(1, "apple", 10, 0, 1.5, 5);
        goods = new ScannedGoods();
    }


    //testing add item
    @Test(expected = NullPointerException.class)
    public void testAddNullToTheListOfScannedItems(){
        goods.addItem(null, 1);
    }

    @Test
    public void testAddZeroItemToTheListOfScannedItems(){
        goods.addItem(apple, 0);
        assertTrue(goods.getList().size() == 0);

    }

    @Test
    public void testAddOneItemToTheListOfScannedItems(){
        goods.addItem(apple, 1);
        assertTrue(goods.getList().contains(apple));
    }

    @Test
    public void testAddManyOfTheSameItemToTheListOfScannedItems(){
        goods.addItem(apple, 5);
        assertTrue(goods.getList().contains(apple));
        assertTrue(goods.getList().size() == 5);
    }

    @Test
    public void testAddOneItemAndCheckTotalPriceIsUpdated(){
        goods.addItem(apple,1);
        assertTrue(goods.getRunningTotal() == 10);
    }

    @Test
    public void testAddManyOfOneItemAndCheckTotalPriceIsUpdated(){
        goods.addItem(apple, 1);
        assertTrue(goods.getRunningTotal() == 10);
        goods.addItem(apple, 1);
        assertTrue(goods.getRunningTotal() == 20);
        goods.addItem(apple, 2);
        assertTrue(goods.getRunningTotal() == 40);
    }

    //testing calculateChange

    @Test(expected = InsufficientFundsException.class)
    public void testCalculateChangeWhenAmountPaidIsInsufficient() throws InsufficientFundsException{
        goods.addItem(apple, 2);
        goods.calculateChange(10);
    }

    @Test
    public void testCalcChangeWithSufficientFunds() throws InsufficientFundsException{
        goods.addItem(apple, 2);
        double change = goods.calculateChange(20);
        assertTrue(change == 0);
    }

    @Test
    public void testCalcChangeWhenPaymentExceedsWhatIsOwed() throws InsufficientFundsException{
        goods.addItem(apple, 2);
        double change = goods.calculateChange(30);
        assertTrue(change == 10);
    }

    //testing set totalPrice
    @Test
    public void testSetTotalPrice(){
        goods.addItem(apple, 2);
        goods.setTotalPrice(0);
        assertTrue(goods.getRunningTotal() == 0);
    }





}
