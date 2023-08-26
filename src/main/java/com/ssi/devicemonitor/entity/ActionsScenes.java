package com.ssi.devicemonitor.entity;

import com.ssi.devicemonitor.controller.DeviceMonitorController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ActionsScenes {
    private static Label lblName = new Label("Name");
    private static Label lblManu = new Label("Manufacturer");
    private static Label lblVersion = new Label("Device Version");
    private static Label lblLocation = new Label("Location");
    private static Label lblMacAdd = new Label("MAC Address");
    private static Label lblInstallationDateAndTime = new Label("Installation date and Time");
    private static Label lblDeviceType = new Label("Device Type");
    private static VBox softwareDetailsContainer  = new VBox(10);;
    private static VBox hardwareDetailsContainer = new VBox(10);;

    public static Scene CreateEditScene (Device device) {

        DeviceMonitorController.deviceNameTextField.setText(device.getName());
        DeviceMonitorController.deviceManufacturerTextField.setText(device.getManufacturer());
        DeviceMonitorController.deviceVersionTextField.setText(device.getVersion());


        DeviceMonitorController.mainVbox = new VBox(10,DeviceMonitorController.cancelButton,lblName, DeviceMonitorController.deviceNameTextField, lblManu, DeviceMonitorController.deviceManufacturerTextField,lblVersion,DeviceMonitorController.deviceVersionTextField );

//        if(device.getDeviceType().equals("Software"))
//        {
//            DeviceMonitorController.deviceTypeComboBox.setValue("Software");
//            lblDeviceType = new Label("Device Type : Software");
//            SoftwareDevice softwareDevice = (SoftwareDevice) device;
//            DeviceMonitorController.dateAndTimeTextField = new TextField(softwareDevice.getInstallationDateAndTime());
//
//            DeviceMonitorController.mainVbox.getChildren().addAll(lblDeviceType,lblInstallationDateAndTime,DeviceMonitorController.dateAndTimeTextField);
//
//        } else if(device.getDeviceType().equals("Hardware")){
//            DeviceMonitorController.deviceTypeComboBox.setValue("Hardware");
//            lblDeviceType = new Label("Device Type : Hardware");
//            HardwareDevice hardwareDevice = (HardwareDevice) device;
//            DeviceMonitorController.locationHardwareTextField = new TextField(hardwareDevice.getLocation());
//            DeviceMonitorController.macAddHardwareTextField = new TextField(hardwareDevice.getMacAddress());
//
//            DeviceMonitorController.mainVbox.getChildren().addAll(lblDeviceType,lblLocation,DeviceMonitorController.locationHardwareTextField,lblMacAdd,DeviceMonitorController.macAddHardwareTextField);
//
//        }
        setSpecificDeviceTypeForEdit(device);
        defineVbox();
        return new Scene(DeviceMonitorController.mainVbox);
    }

    private static void setSpecificDeviceTypeForEdit(Device device)
    {
        Label lblDeviceType ;
        switch (device.getDeviceType())
        {
            case "Software":
                DeviceMonitorController.deviceTypeComboBox.setValue("Software");
                lblDeviceType = new Label("Device Type : Software");
                SoftwareDevice softwareDevice = (SoftwareDevice) device;
                DeviceMonitorController.dateAndTimeTextField = new TextField(softwareDevice.getInstallationDateAndTime());

                DeviceMonitorController.mainVbox.getChildren().addAll(lblDeviceType,lblInstallationDateAndTime,DeviceMonitorController.dateAndTimeTextField);

                break;
            case "Hardware":
                DeviceMonitorController.deviceTypeComboBox.setValue("Hardware");
                lblDeviceType = new Label("Device Type : Hardware");
                HardwareDevice hardwareDevice = (HardwareDevice) device;
                DeviceMonitorController.locationHardwareTextField = new TextField(hardwareDevice.getLocation());
                DeviceMonitorController.macAddHardwareTextField = new TextField(hardwareDevice.getMacAddress());

                DeviceMonitorController.mainVbox.getChildren().addAll(lblDeviceType,lblLocation,DeviceMonitorController.locationHardwareTextField,lblMacAdd,DeviceMonitorController.macAddHardwareTextField);

                break;
            default:
                DeviceMonitorController.deviceTypeComboBox.setValue("General Device");
                break;
        }
    }

    public static Scene CreateAddDeviceScene () {
        DeviceMonitorController.mainVbox = new VBox(10,DeviceMonitorController.cancelButton,lblName, DeviceMonitorController.deviceNameTextField, lblManu, DeviceMonitorController.deviceManufacturerTextField,lblVersion,DeviceMonitorController.deviceVersionTextField, lblDeviceType,DeviceMonitorController.deviceTypeComboBox
        );
        defineVbox();
        return new Scene(DeviceMonitorController.mainVbox);
    }

    private static void defineVbox()
    {
        DeviceMonitorController.mainVbox.setAlignment(Pos.CENTER);
        DeviceMonitorController.mainVbox.setPadding(new Insets(50,50,50,50));
    }

    public static void showDetailsAlert(Device device)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Device Details");
        alert.setHeaderText(device.getName() + "'s" + " Details");
        String details = getDetailsInformation(device);
        alert.setContentText(details);

        alert.showAndWait();
    }

    private static String getDetailsInformation(Device device) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Name: %s%n", device.getName()));
        sb.append(String.format("Manufacturer: %s%n", device.getManufacturer()));
        sb.append(String.format("Version: %s%n", device.getVersion()));
        sb.append(String.format("Device Type: %s%n", device.getDeviceType()));

        if (device instanceof SoftwareDevice) {
            SoftwareDevice softwareDevice = (SoftwareDevice) device;
            sb.append(String.format("Installation Date And Time: %s%n", softwareDevice.getInstallationDateAndTime()));
        } else if (device instanceof HardwareDevice) {
            HardwareDevice hardwareDevice = (HardwareDevice) device;
            sb.append(String.format("Location: %s%n", hardwareDevice.getLocation()));
            sb.append(String.format("MAC Address: %s%n", hardwareDevice.getMacAddress()));
        }

        return sb.toString();
    }

    private static void showSoftwareDetails() {
        DeviceMonitorController.dateAndTimeTextField = new TextField();

        Label lblLocation = new Label("Installation date and Time");

        if(!hardwareDetailsContainer.getChildren().isEmpty())
        {
            DeviceMonitorController.mainVbox.getChildren().remove(hardwareDetailsContainer);
        }

        // Clear existing details if needed
        hardwareDetailsContainer.getChildren().clear();
        softwareDetailsContainer.getChildren().addAll(lblLocation, DeviceMonitorController.dateAndTimeTextField);
        DeviceMonitorController.mainVbox.getChildren().add(softwareDetailsContainer);

    }

    private static void showHardwareDetails() {
        DeviceMonitorController.locationHardwareTextField = new TextField();
        DeviceMonitorController.macAddHardwareTextField = new TextField();

        Label lblLocation = new Label("Location");
        Label lblMacAdd = new Label("MAC Address");

        // if the current children of the mainVbox is software remove it
        if(!softwareDetailsContainer.getChildren().isEmpty())
        {
            DeviceMonitorController.mainVbox.getChildren().remove(softwareDetailsContainer);
        }

        softwareDetailsContainer.getChildren().clear();
        hardwareDetailsContainer.getChildren().addAll(lblLocation, DeviceMonitorController.locationHardwareTextField, lblMacAdd, DeviceMonitorController.macAddHardwareTextField);
        DeviceMonitorController.mainVbox.getChildren().add(hardwareDetailsContainer);

    }

    public static void HandleDeviceTypeSelection() {

        if(DeviceMonitorController.saveButton != null)
        {
            DeviceMonitorController.mainVbox.getChildren().remove(DeviceMonitorController.saveButton);
            DeviceMonitorController.saveButton = null;
        }
        String selectedType = DeviceMonitorController.deviceTypeComboBox.getValue();

        switch (selectedType)
        {
            case "Software":
                showSoftwareDetails();
                break;
            case "Hardware":
                showHardwareDetails();
                break;
            default:
                if(!softwareDetailsContainer.getChildren().isEmpty())
                {
                    DeviceMonitorController.mainVbox.getChildren().remove(softwareDetailsContainer);
                }
                if(!hardwareDetailsContainer.getChildren().isEmpty())
                {
                    DeviceMonitorController.mainVbox.getChildren().remove(hardwareDetailsContainer);
                }
                break;
        }

    }
}
