/**
 * Created by Alejandro Perez on 9/27/16
 * github page: https://github.com/alejojperez
 */
package com.alejojperez.pi_gpio_ui.modules.dashboard.view_models;

import com.alejojperez.pi_gpio.core.contracts.IPin;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.notifications.NotificationCenter;
import javafx.collections.ObservableMap;

public class PinViewModel implements ViewModel
{
    public ObservableMap<String, IPin> pinsList;
    public String key;
    private String originalKey;
    private IPin record;

    private NotificationCenter notificationCenter;

    public PinViewModel(ObservableMap<String, IPin> pinsList, String key)
    {
        this.pinsList = pinsList;
        this.key = key;

        this.originalKey = this.key.toString();
        this.record = this.pinsList.get(this.key);
    }

    public void saveData()
    {

    }
}
