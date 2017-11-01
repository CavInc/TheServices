package cav.theservices.data.models;

import java.util.ArrayList;
import java.util.HashMap;

public class ServiceEditModel{
    private int mId;
    private Float mPrice;
    private String mPhoto;
    private String mBigPhoto;

    private ArrayList<LangDataModel> mData;

    public ServiceEditModel(int id, Float price, String photo, String bigPhoto, ArrayList<LangDataModel> data) {
        mId = id;
        mPrice = price;
        mPhoto = photo;
        mBigPhoto = bigPhoto;
        mData = data;
    }

    public ServiceEditModel(Float price, String photo, String bigPhoto, ArrayList<LangDataModel> data) {
        mPrice = price;
        mPhoto = photo;
        mBigPhoto = bigPhoto;
        mData = data;
    }

    public int getId() {
        return mId;
    }

    public Float getPrice() {
        return mPrice;
    }

    public String getPhoto() {
        return mPhoto;
    }

    public String getBigPhoto() {
        return mBigPhoto;
    }

    public ArrayList<LangDataModel> getData() {
        return mData;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setPrice(Float price) {
        mPrice = price;
    }

    public void setPhoto(String photo) {
        mPhoto = photo;
    }

    public void setBigPhoto(String bigPhoto) {
        mBigPhoto = bigPhoto;
    }

    public void setData(ArrayList<LangDataModel> data) {
        mData = data;
    }
}