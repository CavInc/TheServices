package cav.theservices.data.models;

import java.util.Date;

public class DemandModel {
    private int mID;
    private String mDevice;
    private int mServiceID;
    private String mComment;
    private Date mDate;

    public DemandModel(int ID, String device, int serviceID, String comment) {
        mID = ID;
        mDevice = device;
        mServiceID = serviceID;
        mComment = comment;
    }

    public DemandModel(int ID, String device, int serviceID, String comment, Date date) {
        mID = ID;
        mDevice = device;
        mServiceID = serviceID;
        mComment = comment;
        mDate = date;
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

    public Date getDate() {
        return mDate;
    }
}