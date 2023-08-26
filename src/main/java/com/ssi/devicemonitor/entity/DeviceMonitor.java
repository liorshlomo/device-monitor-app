package com.ssi.devicemonitor.entity;

import com.ssi.devicemonitor.DeviceMonitorApp;
import javafx.application.Platform;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class DeviceMonitor {
    private List<Device> devices;
    private Timer statusUpdateTimer;

    public DeviceMonitor() {
        //devices = new ArrayList<>();
        devices = DeviceMonitorApp.getSavedData().getDevices();
        // Start the timer to simulate status updates every few seconds
        statusUpdateTimer = new Timer();
        statusUpdateTimer.schedule(new StatusUpdateTask(), 0, 5000); // Update every 5 seconds
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void addDevice(Device device) {
        this.devices.add(device);
    }

    public void removeDevice(Device device) {
        devices.remove(device);
    }


    private class StatusUpdateTask extends TimerTask {
        private Random random = new Random();

        @Override
        public void run() {

            synchronized (devices) {
                for (Device device : devices) {
                    // Simulate random status updates
                    boolean isOnline = random.nextBoolean();
                    device.setStatus(isOnline ? "Online" : "Offline");
                }

                // Calculate last and next modification times
                LocalDateTime lastModificationTime = LocalDateTime.now();
                LocalDateTime nextModificationTime = lastModificationTime.plusSeconds(5);

                // Update the labels of controller through the DeviceMonitorApp
                Platform.runLater(() -> {
                    DeviceMonitorApp.UpdateControllerLabels(lastModificationTime, nextModificationTime);
                });}
        }
    }
}
