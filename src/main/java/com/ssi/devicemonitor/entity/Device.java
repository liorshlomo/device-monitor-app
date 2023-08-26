package com.ssi.devicemonitor.entity;

import java.io.Serializable;

public abstract class Device implements Serializable {
    private String name;
    private String status;
    private String manufacturer;
    private String deviceType;
    private String version;

    public Device(String name, String manufacturer, String deviceType, String version) {
        this.name = name;
        this.status = "Offline"; // Set initial status to Offline
        this.manufacturer = manufacturer;
        this.deviceType = deviceType;
        this.version = version;
    }
    public Device(String name) {
        this.name = name;
        this.status = "Offline"; // Set initial status to Offline
        this.deviceType = "General Device";
        this.manufacturer = "GD";
        this.version ="GD";
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getManufacturer() {
        return manufacturer;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public String getVersion() {
        return version;
    }
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
    public void setVersion(String version) {
        this.version = version;
    }

}
