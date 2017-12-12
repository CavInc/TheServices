package cav.theservices.data.models;

import java.util.ArrayList;

public class DemandDeviceModel {
    private String mDeviceId;
    private String mDeviceName;
    private String mComment;
    private int mDemandId;
    private Double mPrice;
    private int mServiceId;

    public DemandDeviceModel(String deviceId, String deviceName, String comment, int demandId, Double price, int serviceId) {
        mDeviceId = deviceId;
        mDeviceName = deviceName;
        mComment = comment;
        mDemandId = demandId;
        mPrice = price;
        mServiceId = serviceId;
    }

    public DemandDeviceModel(String deviceId, String deviceName, String comment, int demandId, Double price, int serviceId, ArrayList<LangDataModel> serviceSpec) {
        mDeviceId = deviceId;
        mDeviceName = deviceName;
        mComment = comment;
        mDemandId = demandId;
        mPrice = price;
        mServiceId = serviceId;
        mServiceSpec = serviceSpec;
    }

    private ArrayList<LangDataModel> mServiceSpec;

    public String getDeviceId() {
        return mDeviceId;
    }

    public String getDeviceName() {
        return mDeviceName;
    }

    public String getComment() {
        return mComment;
    }

    public int getDemandId() {
        return mDemandId;
    }

    public Double getPrice() {
        return mPrice;
    }

    public int getServiceId() {
        return mServiceId;
    }

    public ArrayList<LangDataModel> getServiceSpec() {
        return mServiceSpec;
    }

    public void setServiceSpec(ArrayList<LangDataModel> serviceSpec) {
        mServiceSpec = serviceSpec;
    }
}