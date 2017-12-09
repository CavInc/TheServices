package cav.theservices.data.models;

public class DemandModel {
    private int mID;
    private String mDevice;
    private int mServiceID;
    private String mComment;

    public DemandModel(int ID, String device, int serviceID, String comment) {
        mID = ID;
        mDevice = device;
        mServiceID = serviceID;
        mComment = comment;
    }

    public int getID() {
        return mID;
    }

    public String getDevice() {
        return mDevice;
    }

    public int getServiceID() {
        return mServiceID;
    }

    public String getComment() {
        return mComment;
    }
}