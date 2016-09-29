/**
 * Created by Alejandro Perez on 9/29/16
 * github page: https://github.com/alejojperez
 */
package com.alejojperez.pi_gpio_ui.core.controls;

import com.alejojperez.pi_gpio_ui.core.contracts.IUserControl;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ToggleButton;

import java.io.IOException;

public class PinControl extends ToggleButton implements IUserControl
{
    @FXML
    private FontAwesomeIconView fontIcon;

    public PinControl()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/alejojperez/pi_gpio_ui/core/controls/Pin/PinControl.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch(IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public Number getIconSize() {
        return this.fontIcon.getGlyphSize();
    }

    public void setIconSize(Number value) {
        this.fontIcon.setGlyphSize(value);
    }
}
