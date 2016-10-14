/**
 * Created by Alejandro Perez on 9/27/16
 * github page: https://github.com/alejojperez
 */
package com.alejojperez.pi_gpio_ui.modules.dashboard.views;

import com.alejojperez.pi_gpio.core.contracts.IPin;
import com.alejojperez.pi_gpio_ui.modules.dashboard.view_models.DashboardViewModel;
import com.google.inject.Inject;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.utils.notifications.NotificationCenter;
import javafx.application.Platform;
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

    @Inject
    private NotificationCenter notificationCenter;

    public void initialize(URL location, ResourceBundle resources)
    {
        this.initializeTable();
        this.initializeNotificationSubscribers();
    }

    // <editor-fold desc="Helpers">

    private void initializeNotificationSubscribers()
    {
        this.notificationCenter.subscribe("module:dashboard:pinInitialized", (key, payload) -> {
            Platform.runLater(() -> pinsTable.refresh());
        });
    }

    private void initializeTable()
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
                            IPin pin = record.getValue();
                            String pinKey = record.getKey();

                            HBox hBox = new HBox();

                            /**
                             * On Button
                             */
                            FontAwesomeIconView onIcon = new FontAwesomeIconView(FontAwesomeIcon.TOGGLE_ON);
                            onIcon.setGlyphStyle("-fx-fill: #fff");

                            Button buttonOn = new Button();
                            buttonOn.setGraphic(onIcon);
                            buttonOn.getStyleClass().addAll("btn", "btn-xs", "btn-success");
                            buttonOn.disableProperty().bind(viewModel.getPinOnCommand(pinKey).executableProperty().not());
                            buttonOn.setOnAction(event -> viewModel.getPinOnCommand(pinKey).execute());

                            /**
                             * Off Button
                             */
                            FontAwesomeIconView offIcon = new FontAwesomeIconView(FontAwesomeIcon.TOGGLE_OFF);
                            onIcon.setGlyphStyle("-fx-fill: #fff");

                            Button buttonOff = new Button();
                            buttonOff.setGraphic(offIcon);
                            buttonOff.getStyleClass().addAll("btn", "btn-xs", "btn-danger");
                            buttonOff.disableProperty().bind(viewModel.getPinOffCommand(pinKey).executableProperty().not());
                            buttonOff.setOnAction(event -> viewModel.getPinOffCommand(pinKey).execute());

                            /**
                             * Initialize/Destroy Button
                             */
                            FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.POWER_OFF);
                            deleteIcon.setGlyphStyle("-fx-fill: #fff");

                            String cssClass, text;
                            if(pin.isInitialized()) {
                                cssClass = "btn-warning";
                                text = "Destroy";
                            } else {
                                cssClass = "btn-primary";
                                text = "Initialize";
                            }

                            Button buttonInitializeDestroy = new Button();
                            buttonInitializeDestroy.setText(text);
                            buttonInitializeDestroy.setGraphic(deleteIcon);
                            buttonInitializeDestroy.getStyleClass().addAll("btn", "btn-xs", cssClass);
                            buttonInitializeDestroy.setOnAction(event -> viewModel.getInitializeDestroyPinCommand(pinKey).execute());

                            /**
                             * Add all components
                             */
                            hBox.setSpacing(5);
                            hBox.getChildren().addAll(buttonOn, buttonOff, buttonInitializeDestroy);
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
