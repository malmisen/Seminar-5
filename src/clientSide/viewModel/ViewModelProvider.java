package clientSide.viewModel;

import clientSide.model.SaleLogicModel;
import clientSide.view.ViewHandler;
import clientSide.viewModel.registerItemViewModel.RegisterItemViewModel;

/**
 * A class which handles the different view models.
 */
public class ViewModelProvider {
    private RegisterItemViewModel registerItemViewModel;
    /**
     * Create a new instance
     * @param model The model which contains all the business logic.
     * @param vh The view handler which is responsible for presenting the different views.
     */
    public ViewModelProvider(SaleLogicModel model, ViewHandler vh){
        registerItemViewModel = new RegisterItemViewModel(model, vh);
    }

    /**
     * @return registerItemViewModel The view model to be returned.
     */
    public RegisterItemViewModel getRegisterItemViewModel(){
        return registerItemViewModel;
    }
}
