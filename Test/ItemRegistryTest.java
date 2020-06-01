import exceptions.CouldNotConnectToDatabaseException;
import exceptions.FetchItemException;
import org.junit.Before;
import org.junit.Test;

import serverSide.integration.ItemRegistry;
import sharedDataObjects.Item;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ItemRegistryTest {

    private ItemRegistry registry;
    private Item apple;
    private Item tomato;

    @Before
    public void setUp(){
        apple = new Item(1, "apple", 15, 0, 0.1 , 5);
        tomato = new Item(2, "tomato", 7, 0.03, 0, 10);
        Item burger = new Item(1, "burger", 40, 0.01, 0.1 , 5);
        Item[] items = {apple, tomato, burger};
        registry = new ItemRegistry(items);
    }

    @Test(expected = NullPointerException.class)
    public void fetchItemWithNullAsItemName() throws FetchItemException, CouldNotConnectToDatabaseException{
        registry.fetchItem(null, 1);
    }

    @Test (expected = FetchItemException.class)
    public void fetchItemWithQuantityZero() throws FetchItemException, CouldNotConnectToDatabaseException {
        registry.fetchItem("apple", 0);
    }

    @Test
    public void fetchOneItem()throws FetchItemException, CouldNotConnectToDatabaseException{
        Item temp = registry.fetchItem("apple", 1);
        assertTrue(temp.equals(apple));
    }

    @Test(expected = FetchItemException.class)
    public void fetchTooLargeQuantityOfAnItem() throws FetchItemException, CouldNotConnectToDatabaseException{
        registry.fetchItem("apple", 1000000);
    }

    @Test(expected = CouldNotConnectToDatabaseException.class)
    public void fetchNonExistingItem() throws FetchItemException, CouldNotConnectToDatabaseException{
        registry.fetchItem("Sugar", 1);
    }

    @Test(expected = FetchItemException.class)
    public void fetchZeroOfNonExistingItem() throws FetchItemException, CouldNotConnectToDatabaseException{
        registry.fetchItem("Sugar", 0);
    }

    @Test(expected = CouldNotConnectToDatabaseException.class)
    public void fetchTooLargeQuantityOfNonExistingItem() throws FetchItemException, CouldNotConnectToDatabaseException{
        registry.fetchItem("Sugar", 1000000);
    }

    @Test
    public void makeSureItemQuantityIsDecreasedAfterBeingFetched() throws FetchItemException, CouldNotConnectToDatabaseException{
        int quantity = apple.getItemQuantity();
        Item fetchedItem = registry.fetchItem("apple", 1);
        assertTrue(fetchedItem.getItemQuantity() < quantity);
        assertTrue(fetchedItem.getItemQuantity() == quantity - 1);
    }

    @Test(expected = FetchItemException.class)
    public void fetchOneItemMultipleTimesUntilItemRunsOutOfStock() throws FetchItemException, CouldNotConnectToDatabaseException{
       for(int i = 0; i < 10; i++){
          registry.fetchItem("apple", 1);
       }
       assertTrue(apple.getItemQuantity() == 0);
    }

    @Test(expected = FetchItemException.class)
    public void fetchManyOfOneItemUntilItemRunsOutOfStock() throws FetchItemException, CouldNotConnectToDatabaseException{
        int originalQuantity = apple.getItemQuantity();
        registry.fetchItem("apple", 2);
        assertTrue(apple.getItemQuantity() == originalQuantity - 2);
        registry.fetchItem("apple", 2);
        assertTrue((originalQuantity - 4) == apple.getItemQuantity());
        registry.fetchItem("apple", 2);

    }

    @Test
    public void ensureOtherItemPropertiesRemainUntouchedWhenFetchingDifferentItem() throws FetchItemException, CouldNotConnectToDatabaseException{
        int tomatoOriginalQuantity = tomato.getItemQuantity();
        registry.fetchItem("apple", 1);
        assertTrue(tomatoOriginalQuantity == tomato.getItemQuantity());
    }

    @Test
    public void updateItemQuantityAfterItHasBeenDepleted() throws FetchItemException, CouldNotConnectToDatabaseException{
        registry.fetchItem("apple", 5);
        setUp();
        registry.fetchItem("apple", 1);
        assertTrue(apple.getItemQuantity() == 4);
    }
}
