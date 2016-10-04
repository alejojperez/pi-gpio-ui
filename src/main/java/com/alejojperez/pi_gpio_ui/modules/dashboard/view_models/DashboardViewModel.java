/**
 * Created by Alejandro Perez on 9/27/16
 * github page: https://github.com/alejojperez
 */
package com.alejojperez.pi_gpio_ui.modules.dashboard.view_models;

import com.alejojperez.pi_gpio.core.contracts.IGPIOController;
import com.alejojperez.pi_gpio.core.implementations.GPIOController;
import de.saxsys.mvvmfx.ViewModel;

public class DashboardViewModel implements ViewModel
{
    private IGPIOController controller;

    public DashboardViewModel()
    {
        this.controller = GPIOController.getInstance();
        this.refreshPins();
    }

    public void refreshPins()
    {
        this.controller.addPin("Red", 1);
        this.controller.addPin("Blue", 2);
        this.controller.addPin("Yellow", 3);
    }
}
