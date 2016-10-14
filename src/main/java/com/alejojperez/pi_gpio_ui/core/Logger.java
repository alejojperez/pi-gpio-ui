/**
 * Created by Alejandro Perez on 10/13/16
 * github page: https://github.com/alejojperez
 */
package com.alejojperez.pi_gpio_ui.core;

import com.alejojperez.pi_gpio.core.contracts.ILogger;

public class Logger implements ILogger
{
    @Override
    public void log(Object obj)
    {
        System.out.println(obj.toString());
    }
}
