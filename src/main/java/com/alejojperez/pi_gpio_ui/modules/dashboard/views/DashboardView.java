/**
 * Created by Alejandro Perez on 9/27/16
 * github page: https://github.com/alejojperez
 */
package com.alejojperez.pi_gpio_ui.modules.dashboard.views;

import com.alejojperez.pi_gpio.core.contracts.IPin;
import com.alejojperez.pi_gpio.core.implementations.Pin;
import com.alejojperez.pi_gpio_ui.modules.dashboard.view_models.DashboardViewModel;
import com.google.inject.Inject;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.utils.notifications.NotificationCenter;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
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
    private TableColumn<Map.Entry<String, IPin>, String> tcInitialized;

    @FXML
    private TableColumn<Map.Entry<String, IPin>, String> tcDirection;

    @FXML
    private TableColumn<Map.Entry<String, IPin>, String> tcOnOff;

    @FXML
    private Button btnEdit;

    @InjectViewModel
    private DashboardViewModel viewModel;

    @Inject
    private NotificationCenter notificationCenter;

    public void initialize(URL location, ResourceBundle resources)
    {
        this.initializeTable();
        this.initializeButtons();
        this.initializeNotificationSubscribers();
    }

    public void refreshTable()
    {
        this.pinsTable.refresh();
    }

    // <editor-fold desc="Helpers">

    private void initializeButtons()
    {
        this.btnEdit.disableProperty().bind(Bindings.isEmpty(this.pinsTable.getSelectionModel().getSelectedItems()));
    }

    private void initializeNotificationSubscribers()
    {
        this.notificationCenter.subscribe("module:dashboard:pinInitialized", (key, payload) -> {
            Platform.runLater(this::refreshTable);
        });

        this.notificationCenter.subscribe("module:dashboard:pinDestroyed", (key, payload) -> {
            Platform.runLater(this::refreshTable);
        });

        this.notificationCenter.subscribe("module:dashboard:pinDirectionIn", (key, payload) -> {
            Platform.runLater(this::refreshTable);
        });

        this.notificationCenter.subscribe("module:dashboard:pinDirectionOut", (key, payload) -> {
            Platform.runLater(this::refreshTable);
        });

        this.notificationCenter.subscribe("module:dashboard:pinOn", (key, payload) -> {
            Platform.runLater(this::refreshTable);
        });

        this.notificationCenter.subscribe("module:dashboard:pinOff", (key, payload) -> {
            Platform.runLater(this::refreshTable);
        });
    }

    private void initializeTable()
    {
        this.pinsTable.setItems(
                FXCollections.observableArrayList(viewModel.getPinsList().entrySet())
        );

        /**
         * Refresh TableView every time there is a change on the controller's pins
         */
        ObservableMap<String, IPin> pins = viewModel.getPinsList();
        pins.addListener((MapChangeListener<String, IPin>) change -> refreshTable());

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
         * Table Column Initialized
         */
        this.tcInitialized.setCellFactory(
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
                             * Initialized Icon
                             */
                            String color =  pin.isInitialized() ? "1b9957" : "d9493e";
                            FontAwesomeIcon initializedIcon = pin.isInitialized() ? FontAwesomeIcon.THUMBS_UP : FontAwesomeIcon.THUMBS_DOWN;
                            FontAwesomeIconView initializedIconView = new FontAwesomeIconView(initializedIcon);
                            initializedIconView.setGlyphStyle("-fx-fill: #"+color);


                            /**
                             * Add all components
                             */
                            hBox.setSpacing(5);
                            hBox.getChildren().addAll(initializedIconView);
                            hBox.setAlignment(Pos.CENTER);

                            return hBox;
                        }
                    };
                }
            }
        );

        /**
         * Table Column Direction
         */
        this.tcDirection.setCellFactory(
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

                                if(pin.isInitialized()) {
                                    /**
                                     * Direction Icon
                                     */
                                    String color = pin.getDirection().equals(Pin.GPIO_IN) ? "1b9957" : "d9493e";
                                    FontAwesomeIcon directionIcon = pin.getDirection().equals(Pin.GPIO_IN) ? FontAwesomeIcon.SIGN_IN : FontAwesomeIcon.SIGN_OUT;
                                    FontAwesomeIconView directionIconView = new FontAwesomeIconView(directionIcon);
                                    directionIconView.setGlyphStyle("-fx-fill: #" + color);

                                    directionIconView.setOnMouseClicked(event -> {
                                        if(event.getButton().equals(MouseButton.PRIMARY))
                                        {
                                            if(event.getClickCount() == 2)
                                            {
                                                viewModel.getPinDirectionCommand(pinKey).execute();
                                            }
                                        }
                                    });

                                    hBox.getChildren().addAll(directionIconView);
                                } else {
                                    Text text = new Text("N/A");
                                    text.setStyle("-fx-text-fill: #d9493e");
                                    hBox.getChildren().addAll(text);
                                }

                                /**
                                 * Add all components
                                 */
                                hBox.setSpacing(5);
                                hBox.setAlignment(Pos.CENTER);

                                return hBox;
                            }
                        };
                    }
                }
        );

        /**
         * Table Column OnOff
         */
        this.tcOnOff.setCellFactory(
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

                                if(pin.isInitialized()) {
                                    /**
                                     * Direction Icon
                                     */
                                    String color = pin.getValue().equals(Pin.GPIO_ON) ? "1b9957" : "d9493e";
                                    FontAwesomeIconView onOffIconView = new FontAwesomeIconView(FontAwesomeIcon.POWER_OFF);
                                    onOffIconView.setGlyphStyle("-fx-fill: #" + color);

                                    onOffIconView.setOnMouseClicked(event -> {
                                        if(event.getButton().equals(MouseButton.PRIMARY))
                                        {
                                            if(event.getClickCount() == 2)
                                            {
                                                if(pin.getValue().equals(Pin.GPIO_ON))
                                                    viewModel.getPinOffCommand(pinKey).execute();
                                                else
                                                    viewModel.getPinOnCommand(pinKey).execute();

                                            }
                                        }
                                    });

                                    hBox.getChildren().addAll(onOffIconView);
                                } else {
                                    Text text = new Text("N/A");
                                    text.setStyle("-fx-text-fill: #d9493e");
                                    hBox.getChildren().addAll(text);
                                }

                                /**
                                 * Add all components
                                 */
                                hBox.setSpacing(5);
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
