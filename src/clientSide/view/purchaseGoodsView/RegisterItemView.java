package clientSide.view.purchaseGoodsView;

import clientSide.viewModel.registerItemViewModel.RegisterItemViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class RegisterItemView {

    private RegisterItemViewModel model;

    @FXML
    private TextField itemQuantityField, totalPriceField, totalVATField, customerIDField, paymentAmountField, changeAmountField;
    @FXML
    private ComboBox<String> items = new ComboBox<>();
    @FXML
    private ListView<String> scannedItemsList;
    @FXML
    private TextArea errorMessageField;

    public void init(RegisterItemViewModel model){
        this.model = model;
        model.itemQuantityProperty().bindBidirectional(itemQuantityField.textProperty());
        model.errorMessageProperty().bindBidirectional(errorMessageField.textProperty());
        model.totalPriceProperty().bindBidirectional(totalPriceField.textProperty());
        model.totalVATProperty().bindBidirectional(totalVATField.textProperty());
        model.customerIDProperty().bindBidirectional(customerIDField.textProperty());
        model.paymentAmountProperty().bindBidirectional(paymentAmountField.textProperty());
        model.changeAmountProperty().bindBidirectional(changeAmountField.textProperty());
        scannedItemsList.setItems(model.itemListProperty());

        ObservableList<String> options = FXCollections.observableArrayList("apple", "tomato", "banana", "burger", "pasta", "cigarettes");
        items.setItems(options);
        totalPriceField.textProperty().setValue("0");
        totalVATField.textProperty().setValue("0");

    }

    public void selectItem(ActionEvent actionEvent) {
        model.registerItem(items.getValue());
    }


    @FXML
    void discountRequest(ActionEvent event) {
        model.handleDiscount();
    }


    @FXML
    void purchase(ActionEvent event) {
        model.completePurchase();
    }
}
