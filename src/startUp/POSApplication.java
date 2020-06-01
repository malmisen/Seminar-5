package startUp;

import clientSide.model.SaleLogicImp;
import clientSide.model.SaleLogicModel;
import clientSide.view.ViewHandler;
import javafx.application.Application;
import javafx.stage.Stage;
import serverSide.integration.DiscountCatalog;
import serverSide.integration.ItemRegistry;
import sharedDataObjects.Item;

public class POSApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Item apple = new Item(1, "apple", 15, 0, 0.1 , 5);
        Item tomato = new Item(2, "tomato", 7, 0.03, 0, 10);
        Item burger = new Item(1, "burger", 40, 0.01, 0.1 , 5);
        Item pasta = new Item(2, "pasta", 20, 0.03, 0, 10);
        Item banana = new Item(1, "banana", 5, 0, 0.1 , 5);


        Item[] items = {apple, tomato, burger, pasta, banana};
        ItemRegistry registry = new ItemRegistry(items);
        DiscountCatalog catalog = new DiscountCatalog(items);

        SaleLogicModel model = new SaleLogicImp(registry, catalog);
        ViewHandler viewHandler = new ViewHandler(primaryStage, model);
        viewHandler.start();

    }
}