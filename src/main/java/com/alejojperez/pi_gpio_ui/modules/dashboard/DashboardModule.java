package com.alejojperez.pi_gpio_ui.modules.dashboard;

import com.alejojperez.pi_gpio.core.Utils;
import com.alejojperez.pi_gpio.core.contracts.IGPIOController;
import com.alejojperez.pi_gpio.core.contracts.ILogger;
import com.alejojperez.pi_gpio.core.contracts.IPin;
import com.alejojperez.pi_gpio.core.implementations.GPIOController;
import com.alejojperez.pi_gpio.core.implementations.Pin;
import com.alejojperez.pi_gpio_ui.core.Logger;
import com.alejojperez.pi_gpio_ui.core.contracts.IPresenter;
import com.google.inject.AbstractModule;

/**
 * Created by alejandroperez on 9/2/16.
 */
public class DashboardModule extends AbstractModule
{
    public DashboardModule()
    {
        Utils.configPath = "./target/classes/com/alejojperez/pi_gpio_ui/configuration.xml";
    }

    @Override
    protected void configure()
    {
        this.bind(IPresenter.class).to(Presenter.class);
        this.bind(ILogger.class).to(Logger.class);
        this.bind(IGPIOController.class).toInstance(GPIOController.getInstance());
    }
}
