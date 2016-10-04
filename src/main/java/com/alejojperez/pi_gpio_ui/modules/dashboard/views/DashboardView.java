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

                            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.POWER_OFF);
                            String color = record.getValue().isInitialized() ? "179e6a" : "d9493e";
                            icon.setGlyphStyle("-fx-fill: #" + color);

                            hBox.getChildren().addAll(icon);
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
