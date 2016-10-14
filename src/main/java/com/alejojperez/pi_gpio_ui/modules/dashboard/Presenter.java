package com.alejojperez.pi_gpio_ui.modules.dashboard;

import com.alejojperez.pi_gpio_ui.App;
import com.alejojperez.pi_gpio_ui.core.contracts.IPresenter;
import com.alejojperez.pi_gpio_ui.modules.dashboard.view_models.DashboardViewModel;
import com.alejojperez.pi_gpio_ui.modules.dashboard.views.DashboardView;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Created by alejandroperez on 9/2/16.
 */
public class Presenter implements IPresenter
{
    public void show()
    {
        Stage stage = App.getGlobalStage();

        stage.setResizable(true);

        //set Stage boundaries to visible bounds of the main screen
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setMinWidth(800);
        stage.setMinHeight(600);
//        stage.setWidth(primaryScreenBounds.getWidth());
//        stage.setHeight(primaryScreenBounds.getHeight());

        stage.setTitle("Traveler Thinker: Dashboard");
        ViewTuple<DashboardView, DashboardViewModel> viewTuple = FluentViewLoader.fxmlView(DashboardView.class).load();

        Parent root = viewTuple.getView();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
