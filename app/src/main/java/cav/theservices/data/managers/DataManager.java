package cav.theservices.data.managers;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import cav.theservices.data.database.DBConnect;
import cav.theservices.data.models.ServiceClientEditModel;
import cav.theservices.utils.TheServiceApp;

public class DataManager {
    private static DataManager INSTANCE = null;

    private Context mContext;
    private PreferenseManager mPreferenseManager;
    private DBConnect mDB;

    public static DataManager getInstance() {
        if (INSTANCE==null){
            INSTANCE = new DataManager();
        }
        return INSTANCE;
    }
    public DataManager(){
        this.mContext = TheServiceApp.getContext();
        mPreferenseManager = new PreferenseManager();
        mDB = new DBConnect(mContext);
    }

    public Context getContext() {
        return mContext;
    }

    public PreferenseManager getPreferenseManager() {
        return mPreferenseManager;
    }

    public DBConnect getDB() {
        return mDB;
    }

    // ===================== некоторые запросы к базе ====================
    public ArrayList<ServiceClientEditModel> getServiceListEdit(){
        ArrayList<ServiceClientEditModel> rec = new ArrayList<>();
        mDB.open();
        Cursor cursor = mDB.getListService(1); // поменять
        while (cursor.moveToNext()) {
            //ServiceClientEditModel(int id, String title, String body, String screen, Float price, String image)
            rec.add(new ServiceClientEditModel(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("body")),
                    "",
                    cursor.getFloat(cursor.getColumnIndex("price")),
                    ""));
        }
        mDB.close();
        return  rec;
    }
}