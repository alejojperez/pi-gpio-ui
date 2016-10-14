/**
 * Created by Alejandro Perez on 9/15/16
 * github page: https://github.com/alejojperez
 */
package com.alejojperez.pi_gpio_ui;

import com.alejojperez.pi_gpio_ui.core.contracts.IPresenter;
import com.alejojperez.pi_gpio_ui.modules.dashboard.DashboardModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import de.saxsys.mvvmfx.guice.MvvmfxGuiceApplication;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends MvvmfxGuiceApplication
{
    /**
     * Global Stage (window)
     */
    private static Stage globalStage;
    public static Stage getGlobalStage()
    {
        return globalStage;
    }
    public static Stage setGlobalStage(Stage stage)
    {
        App.globalStage = stage;
        return App.getGlobalStage();
    }

    /**
     * Current Dependency Injector module
     */
    private static Injector currentDIModule;
    public static Injector getCurrentDIModule()
    {
        return App.currentDIModule;
    }
    public static Injector setCurrentDIModule(Injector module) { App.currentDIModule = module; return App.getCurrentDIModule(); }

    public static void main(String...args)
    {
        Application.launch(args);
    }

    public void startMvvmfx(Stage stage) throws Exception
    {
        App.setGlobalStage(stage);

        App.setCurrentDIModule( Guice.createInjector(new DashboardModule()) );
        App.getCurrentDIModule().getInstance(IPresenter.class).show();
    }
}
