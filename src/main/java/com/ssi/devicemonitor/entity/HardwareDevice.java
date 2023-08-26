package com.ssi.devicemonitor.entity;

import java.io.Serializable;

public class HardwareDevice extends GeneralDevice implements Serializable {
    private String location;
    private String macAddress;
    public HardwareDevice(String name, String manufacturer,  String location, String version, String macAddress) {
        super(name, manufacturer, "Hardware", version);
        setLocation(location);
        setMacAddress(macAddress);
    }

    public String getLocation() {
        return location;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}
