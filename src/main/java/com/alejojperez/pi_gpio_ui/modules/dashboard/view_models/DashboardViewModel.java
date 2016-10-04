/**
 * Created by Alejandro Perez on 9/27/16
 * github page: https://github.com/alejojperez
 */
package com.alejojperez.pi_gpio_ui.modules.dashboard.view_models;

import com.alejojperez.pi_gpio.core.contracts.IGPIOController;
import com.alejojperez.pi_gpio.core.contracts.IPin;
import com.alejojperez.pi_gpio.core.implementations.GPIOController;
import de.saxsys.mvvmfx.ViewModel;
import javafx.collections.ObservableMap;

public class DashboardViewModel implements ViewModel
{
    private IGPIOController controller;

    public DashboardViewModel()
    {
        this.controller = GPIOController.getInstance();
        this.loadPins();
    }

    public ObservableMap<String, IPin> getPinsList()
    {
        return this.controller.getAll();
    }

    public void loadPins()
    {
        this.controller
                .addPin("Red", 1)
                .addPin("Blue", 2)
                .addPin("Yellow", 3);
    }
}
