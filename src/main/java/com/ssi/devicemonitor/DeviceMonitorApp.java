package com.ssi.devicemonitor;

import com.ssi.devicemonitor.controller.DeviceMonitorController;
import com.ssi.devicemonitor.entity.Device;
import com.ssi.devicemonitor.entity.GeneralDevice;
import com.ssi.devicemonitor.utils.DataSerializer;
import com.ssi.devicemonitor.utils.ResourceManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DeviceMonitorApp extends Application {

    public static Stage primaryStage;
    private static DeviceMonitorController controller;
    public static DataSerializer savedData;
    public static DataSerializer getSavedData() {
        return savedData;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        savedData = loadSavedData();
        ShowMainScene();
    }

    @Override
    public void stop() {
        // Save data before the app is closed
        saveData(controller.getDeviceMonitor().getDevices());
    }

    private void saveData(List<Device> devices) {
        try {
            savedData.setDevices(devices);
            ResourceManager.save( savedData , "SavedDevices.save");
            System.out.println("Devices saved successfully.");
        } catch (Exception e) {
            System.out.println("Error Saving Devices: " + e.getMessage());
        }
    }

    private DataSerializer loadSavedData() {
        try {
            return (DataSerializer) ResourceManager.Load("SavedDevices.save");

        } catch (Exception e) {
            System.out.println("Error loading devices: " + e.getMessage());
            return new DataSerializer();
        }
    }

    public static void ShowMainScene() throws Exception
    {
        FXMLLoader loader = new FXMLLoader(DeviceMonitorApp.class.getResource("device_monitor.fxml"));

        if (controller == null) {
            controller = new DeviceMonitorController();

        }

        loader.setController(controller);
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Device Monitor");
        primaryStage.show();
    }

    public static void UpdateControllerLabels(LocalDateTime lastModificationTime, LocalDateTime nextModificationTime)
    {
        controller.UpdateModificationLabels(lastModificationTime, nextModificationTime);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
