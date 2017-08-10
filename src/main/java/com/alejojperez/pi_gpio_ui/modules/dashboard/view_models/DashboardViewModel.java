/**
 * Created by Alejandro Perez on 9/27/16
 * github page: https://github.com/alejojperez
 */
package com.alejojperez.pi_gpio_ui.modules.dashboard.view_models;

import com.alejojperez.pi_gpio.core.contracts.IFileLogger;
import com.alejojperez.pi_gpio.core.contracts.IGPIOController;
import com.alejojperez.pi_gpio.core.contracts.ILogger;
import com.alejojperez.pi_gpio.core.contracts.IPin;
import com.alejojperez.pi_gpio.core.implementations.FileLogger;
import com.alejojperez.pi_gpio.core.implementations.FolderWatcher;
import com.alejojperez.pi_gpio.core.implementations.GPIOController;
import com.alejojperez.pi_gpio.core.implementations.Pin;
import com.alejojperez.pi_gpio_ui.App;
import com.google.inject.Inject;
import com.google.inject.Injector;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import de.saxsys.mvvmfx.utils.notifications.NotificationCenter;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class DashboardViewModel implements ViewModel
{
    private IGPIOController controller;

    @Inject
    private NotificationCenter notificationCenter;

    public DashboardViewModel()
    {
        FolderWatcher.log = true;
        this.controller = GPIOController.getInstance();
        IFileLogger logger = new FileLogger();
        this.controller.registerLogger(logger);

        this.loadPins();
    }

    public ObservableMap<String, IPin> getPinsList()
    {
        return this.controller.getAll();
    }

    private void loadPins()
    {
        //this.controller.addPin("Red", 1).get("Red").registerLogger(App.getCurrentDIModule().getInstance(ILogger.class));
        //this.controller.addPin("Blue", 2).get("Blue").registerLogger(App.getCurrentDIModule().getInstance(ILogger.class));
        //this.controller.addPin("Yellow", 3).get("Yellow").registerLogger(App.getCurrentDIModule().getInstance(ILogger.class));
    }

    //<editor-fold desc="InitializeDestroyPinCommand">

    private Command initializeDestroyPinCommand;

    public Command getInitializeDestroyPinCommand(String pinKey)
    {
        IPin pin = this.controller.get(pinKey);

        if(this.initializeDestroyPinCommand == null)
        {
            this.initializeDestroyPinCommand = new DelegateCommand(() -> new Action()
            {
                @Override
                protected void action() throws Exception
                {
                    String notification = pin.isInitialized() ?
                            "module:dashboard:pinInitialized" :
                            "module:dashboard:pinDestroyed";

                    if(pin.isInitialized()) pin.destroy();
                    else pin.initialize();

                    notificationCenter.publish(notification, pin);
                }
            },
            true);
        }

        return this.initializeDestroyPinCommand;
    }

    //</editor-fold>

    //<editor-fold desc="PinDirectionCommand">

    private Command pinDirectionCommand;

    public Command getPinDirectionCommand(String pinKey)
    {
        IPin pin = this.controller.get(pinKey);

        if(this.pinDirectionCommand == null)
        {
            this.pinDirectionCommand = new DelegateCommand(() -> new Action()
            {
                @Override
                protected void action() throws Exception
                {
                    String notification = pin.getDirection().equals(Pin.GPIO_OUT) ?
                            "module:dashboard:pinDirectionIn" :
                            "module:dashboard:pinDirectionOut";

                    if(pin.getDirection().equals(Pin.GPIO_OUT)) pin.setDirection(Pin.GPIO_IN);
                    else pin.setDirection(Pin.GPIO_OUT);

                    notificationCenter.publish(notification, pin);
                }
            },
                    new SimpleBooleanProperty(pin.isInitialized()),
                    true);
        }

        return this.pinDirectionCommand;
    }

    //</editor-fold>

    //<editor-fold desc="PinOnCommand">

    private Command pinOnCommand;

    public Command getPinOnCommand(String pinKey)
    {
        IPin pin = this.controller.get(pinKey);

        if(this.pinOnCommand == null)
        {
            this.pinOnCommand = new DelegateCommand(() -> new Action()
            {
                @Override
                protected void action() throws Exception
                {
                    pin.setValue(Pin.GPIO_ON);

                    notificationCenter.publish("module:dashboard:pinOn", pin);
                }
            },
            new SimpleBooleanProperty(pin.isInitialized()),
            true);
        }

        return this.pinOnCommand;
    }

    //</editor-fold>

    //<editor-fold desc="PinOffCommand">

    private Command pinOffCommand;

    public Command getPinOffCommand(String pinKey)
    {
        IPin pin = this.controller.get(pinKey);

        if(this.pinOffCommand == null)
        {
            this.pinOffCommand = new DelegateCommand(() -> new Action()
            {
                @Override
                protected void action() throws Exception
                {
                    pin.setValue(Pin.GPIO_OFF);

                    notificationCenter.publish("module:dashboard:pinOff", pin);
                }
            },
                    new SimpleBooleanProperty(pin.isInitialized()),
                    true);
        }

        return this.pinOffCommand;
    }

    //</editor-fold>
}
