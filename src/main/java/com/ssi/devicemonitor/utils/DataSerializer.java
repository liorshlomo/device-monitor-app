package com.ssi.devicemonitor.utils;

import com.ssi.devicemonitor.entity.Device;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataSerializer implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Device> devices;

    public DataSerializer() {
        if(this.devices == null)
        {
            this.devices = new ArrayList<>();
        }
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }
    public List<Device> getDevices() {
        return devices;
    }

}
