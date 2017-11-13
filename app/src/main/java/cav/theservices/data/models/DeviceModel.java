package cav.theservices.data.models;

public class DeviceModel {
    private int mID;
    private String mDeviceID;
    private int mDeviceMode;
    private String mDeviceText;

    public DeviceModel(int ID, String deviceID, int deviceMode, String deviceText) {
        mID = ID;
        mDeviceID = deviceID;
        mDeviceMode = deviceMode;
        mDeviceText = deviceText;
    }

    public DeviceModel(String deviceID, int deviceMode, String deviceText) {
        mDeviceID = deviceID;
        mDeviceMode = deviceMode;
        mDeviceText = deviceText;
    }

    public int getID() {
        return mID;
    }

    public String getDeviceID() {
        return mDeviceID;
    }

    public int getDeviceMode() {
        return mDeviceMode;
    }

    public String getDeviceText() {
        return mDeviceText;
    }
}