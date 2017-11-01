package cav.theservices.data.managers;

import android.content.Context;

import cav.theservices.data.database.DBConnect;
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
}