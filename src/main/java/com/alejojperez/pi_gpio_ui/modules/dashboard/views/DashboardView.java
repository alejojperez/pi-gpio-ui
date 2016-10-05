/**
 * Created by Alejandro Perez on 9/27/16
 * github page: https://github.com/alejojperez
 */
package com.alejojperez.pi_gpio_ui.modules.dashboard.views;

import com.alejojperez.pi_gpio.core.contracts.IPin;
import com.alejojperez.pi_gpio_ui.modules.dashboard.view_models.DashboardViewModel;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class DashboardView implements FxmlView<DashboardViewModel>, Initializable
{
    @FXML
    private TableView pinsTable;

    @FXML
    private TableColumn<Map.Entry<String, IPin>, String> tcName;

    @FXML
    private TableColumn<Map.Entry<String, IPin>, String> tcPinNumber;

    @FXML
    private TableColumn<Map.Entry<String, IPin>, String> tcActions;

    @InjectViewModel
    private DashboardViewModel viewModel;

    public void initialize(URL location, ResourceBundle resources)
    {
        this.initializeTable();
    }

    // <editor-fold desc="Helpers">

    protected void initializeTable()
    {
        ObservableMap<String, IPin> pins = viewModel.getPinsList();
        ObservableList<Map.Entry<String, IPin>> listData = FXCollections.observableArrayList(pins.entrySet());

        this.pinsTable.setItems(listData);

        /**
         * Table Column Name
         */
        this.tcName.setCellValueFactory(
                (TableColumn.CellDataFeatures<Map.Entry<String, IPin>, String> p) ->
                        new SimpleStringProperty(p.getValue().getKey())
        );

        /**
         * Table Column Pin Number
         */
        this.tcPinNumber.setCellValueFactory(
                (TableColumn.CellDataFeatures<Map.Entry<String, IPin>, String> p) ->
                        new SimpleStringProperty(Integer.toString(p.getValue().getValue().getPinNumber()))
        );

        /**
         * Table Column Actions
         */
        this.tcActions.setCellFactory(
            new Callback<TableColumn<Map.Entry<String, IPin>, String>, TableCell<Map.Entry<String, IPin>, String>>()
            {
                @Override
                public TableCell call( final TableColumn<Map.Entry<String, IPin>, String> param )
                {
                    return new TableCell<Map.Entry<String, IPin>, String>()
                    {
                        @Override
                        public void updateItem( String item, boolean empty )
                        {
                            super.updateItem( item, empty );
                            this.setText( null );

                            if ( empty ) {
                                this.setGraphic( null );
                            } else {
                                this.setGraphic( this.generateContent() );
                            }
                        }

                        private HBox generateContent()
                        {
                            Map.Entry<String, IPin> record = this.getTableView().getItems().get(this.getIndex());

                            HBox hBox = new HBox();

                            /**
                             * Initialize/Shut Down Button
                             */
                            FontAwesomeIconView iconPowerOff = new FontAwesomeIconView(FontAwesomeIcon.POWER_OFF);
                            iconPowerOff.setGlyphStyle("-fx-fill: #fff");

                            String cssClass, text;
                            if(record.getValue().isInitialized()) {
                                cssClass = "btn-warning";
                                text = "OFF";
                            } else {
                                cssClass = "btn-success";
                                text = "ON";
                            }

                            Button buttonPowerOff = new Button();
                            buttonPowerOff.setText(text);
                            buttonPowerOff.setGraphic(iconPowerOff);
                            buttonPowerOff.getStyleClass().addAll("btn", "btn-xs", cssClass);

                            /**
                             * Delete Button
                             */
                            FontAwesomeIconView iconTimes = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                            iconTimes.setGlyphStyle("-fx-fill: #fff");

                            Button buttonDelete = new Button();
                            buttonDelete.setText("Delete");
                            buttonDelete.setGraphic(iconTimes);
                            buttonDelete.getStyleClass().addAll("btn", "btn-xs", "btn-danger");

                            /**
                             * Add all components
                             */
                            hBox.setSpacing(5);
                            hBox.getChildren().addAll(buttonPowerOff, buttonDelete);
                            hBox.setAlignment(Pos.CENTER);

                            return hBox;
                        }
                    };
                }
            }
        );
    }

    // </editor-fold>
}
