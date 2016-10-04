/**
 * Created by Alejandro Perez on 9/27/16
 * github page: https://github.com/alejojperez
 */
package com.alejojperez.pi_gpio_ui.modules.dashboard.views;

import com.alejojperez.pi_gpio_ui.modules.dashboard.view_models.DashboardViewModel;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardView implements FxmlView<DashboardViewModel>, Initializable
{
    @FXML
    private TableView pinsTable;

    @InjectViewModel
    private DashboardViewModel viewModel;

    public void initialize(URL location, ResourceBundle resources)
    {

    }
}
