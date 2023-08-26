package com.ssi.devicemonitor.entity;

import java.io.Serializable;

public class GeneralDevice extends Device implements Serializable {
    public GeneralDevice(String name,String manufacturer, String deviceType, String version) {
        super(name, manufacturer, deviceType, version);
    }
    public GeneralDevice(String name) {
        super(name);
    }
}
