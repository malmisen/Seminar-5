package clientSide.view;

import clientSide.model.SaleLogicModel;
import clientSide.view.purchaseGoodsView.RegisterItemView;
import clientSide.viewModel.ViewModelProvider;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Responsible for handling and presenting the different views
 */
public class ViewHandler {
    private ViewModelProvider viewModelProvider;
    private Stage mainStage;

    /**
     * Creates new instance
     *
     * @param mainStage The current stage
     * @param model The model which contains all the business logic
     */
    public ViewHandler(Stage mainStage, SaleLogicModel model){
        viewModelProvider = new ViewModelProvider(model, this);
        this.mainStage = mainStage;
    }

    /**
     * Method start. loads and displays the view
     */
    public void start(){
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("purchaseGoodsView/RegItems.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        RegisterItemView addItemToCartView = loader.getController();
        addItemToCartView.init(viewModelProvider.getRegisterItemViewModel());
        mainStage.setTitle("register items");

        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.show();
    }

}
