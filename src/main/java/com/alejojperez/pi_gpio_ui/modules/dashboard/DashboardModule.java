package com.alejojperez.pi_gpio_ui.modules.dashboard;

import com.alejojperez.pi_gpio_ui.core.contracts.IPresenter;
import com.google.inject.AbstractModule;

/**
 * Created by alejandroperez on 9/2/16.
 */
public class DashboardModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(IPresenter.class).to(Presenter.class);
    }
}
