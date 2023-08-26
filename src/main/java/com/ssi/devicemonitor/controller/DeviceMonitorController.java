package com.ssi.devicemonitor.controller;

import com.ssi.devicemonitor.DeviceMonitorApp;
import com.ssi.devicemonitor.entity.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.TimerTask;

public class DeviceMonitorController {
    @FXML
    private Label lastModificationLabel;
    @FXML
    private Label nextModificationLabel;
    @FXML
    private ListView<Device> deviceListView;
    @FXML
    private Button addDeviceButton;
    private Device currDevice;
    public static TextField deviceNameTextField;
    public static TextField deviceManufacturerTextField;
    public static TextField deviceVersionTextField;
    public static TextField locationHardwareTextField;
    public static TextField macAddHardwareTextField;
    public static TextField dateAndTimeTextField;
    public static Button saveButton;
    public static Button cancelButton;
    public static ComboBox<String> deviceTypeComboBox;
    private DeviceMonitor deviceMonitor;
    public static VBox mainVbox;

    public DeviceMonitor getDeviceMonitor() {
        return deviceMonitor;
    }
    public void initialize() {

        if(deviceMonitor == null)
        {
            deviceMonitor = new DeviceMonitor();
        }

        deviceListView.setItems(FXCollections.observableList(deviceMonitor.getDevices()));
        deviceListView.setCellFactory(deviceListView -> new DeviceListCell());

        // Add context menu to ListView
        ContextMenu contextMenu = new ContextMenu();
        MenuItem removeItem = new MenuItem("Remove");
        MenuItem detailsItem = new MenuItem("Details");
        MenuItem editItem = new MenuItem("Edit");

        removeItem.setOnAction(event -> {
            Device selectedDevice = deviceListView.getSelectionModel().getSelectedItem();
            if (selectedDevice != null) {
                deviceMonitor.removeDevice(selectedDevice);
                deviceListView.setItems(FXCollections.observableList(deviceMonitor.getDevices()));
                deviceListView.setCellFactory(deviceListView -> new DeviceListCell());
            }
        });

        detailsItem.setOnAction(event -> {
            Device selectedDevice = deviceListView.getSelectionModel().getSelectedItem();
                if (selectedDevice != null) {
                    showDetails(selectedDevice);
                }
        });

        editItem.setOnAction(event -> {
            Device selectedDevice = deviceListView.getSelectionModel().getSelectedItem();
            if (selectedDevice != null) {
                editDetails(selectedDevice);
            }
        });

        contextMenu.getItems().addAll(removeItem,detailsItem,editItem);
        deviceListView.setContextMenu(contextMenu);

    }

    public void UpdateModificationLabels(LocalDateTime lastModificationTime, LocalDateTime nextModificationTime)
    {
        lastModificationLabel.setText("Last Modification: " + lastModificationTime);
        nextModificationLabel.setText("Next Modification: " + nextModificationTime);
    }

    private void showDetails(Device device) {
        ActionsScenes.showDetailsAlert( device);
    }

    private void editDetails(Device device) {
        currDevice = device;
        initializeCancelOption();
        initializeTextBoxes();
        Scene scene = ActionsScenes.CreateEditScene(device);
        DeviceMonitorApp.primaryStage.setScene(scene);
        addSaveOption("Update");

    }

    private class DataUpdateTask extends TimerTask {
        private Random random = new Random();

        @Override
        public void run() {
            refreshListView();
        }
    }

    @FXML
    private void addDevice(ActionEvent event) {
        initializeCancelOption();
        addDeviceButton.setDisable(true);
        initializeTextBoxes();
        Scene scene = ActionsScenes.CreateAddDeviceScene();
        DeviceMonitorApp.primaryStage.setScene(scene);
    }

    private void initializeCancelOption()
    {
        cancelButton = new Button("Cancel");
        cancelButton.setOnAction(cancelEvent -> handleErrorCancelButton() );
    }

    private void initializeTextBoxes()
    {
        deviceNameTextField = new TextField();
        deviceManufacturerTextField = new TextField();
        deviceVersionTextField = new TextField();
        deviceTypeComboBox = new ComboBox<>();
        deviceTypeComboBox.getItems().addAll("Software", "Hardware","General Device");
        deviceTypeComboBox.setOnAction(eventSelectedType -> {ActionsScenes.HandleDeviceTypeSelection();
            addSaveOption("Save Item");
        });
    }

    private void addSaveOption(String btnName)
    {
        if(saveButton == null)
        {
            saveButton = new Button();
            saveButton.setOnAction(cancelEvent -> handleSaveItem() );
        }
        saveButton.setText(btnName);
        mainVbox.getChildren().add(saveButton);
    }
    private void handleSaveItem()
    {
        if(saveButton.getText().equals("Update"))
        {
            deviceMonitor.removeDevice(currDevice);
            deviceListView.setItems(FXCollections.observableList(deviceMonitor.getDevices()));
            deviceListView.setCellFactory(deviceListView -> new DeviceListCell());
        }

        String name = deviceNameTextField.getText();
        String manufacturer = deviceManufacturerTextField.getText();
        String version = deviceVersionTextField.getText();
        GeneralDevice generalDevice;

        switch (deviceTypeComboBox.getValue())
        {
            case "Software":
                generalDevice = new SoftwareDevice(name,manufacturer,version, dateAndTimeTextField.getText());
                break;
            case "Hardware":
                generalDevice = new HardwareDevice(name, manufacturer,locationHardwareTextField.getText(),version,macAddHardwareTextField.getText());
                break;
            default:
                generalDevice = new GeneralDevice(name);
                generalDevice.setManufacturer(manufacturer);
                generalDevice.setVersion(version);
                break;
        }


        deviceMonitor.addDevice(generalDevice);
        getToTheMainScene();

    }

    private void getToTheMainScene()
    {
        try {
            DeviceMonitorApp.ShowMainScene();
        }catch(Exception e)
        {
            System.out.println("Exception: " + e.getMessage());
            Label lblerror = new Label("Error Getting Back :( ");
            VBox vbox = new VBox(10,lblerror);
            Scene scene = new Scene(vbox);
            DeviceMonitorApp.primaryStage.setScene(scene);
        }
    }
    private void handleErrorCancelButton() {
            getToTheMainScene();
    }

    public void refreshListView() {
        deviceListView.refresh();
    }

    private class DeviceListCell extends ListCell<Device> {
        @Override
        protected void updateItem(Device device, boolean empty) {
            super.updateItem(device, empty);

            if (device == null || empty) {
                setText(null);
                setGraphic(null);
                setStyle(""); // Reset the cell style
            } else {
                setText(device.getName() + " - " + device.getStatus());

                // Set the cell style based on the device status
                if (device.getStatus().equals("Online")) {
                    setStyle("-fx-text-fill: green;");
                } else if (device.getStatus().equals("Offline")) {
                    setStyle("-fx-text-fill: red;");
                } else {
                    setStyle(""); // Reset the cell style
                }
            }
        }
    }

}
