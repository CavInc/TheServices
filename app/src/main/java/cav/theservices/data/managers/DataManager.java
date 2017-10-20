package cav.theservices.data.managers;

import android.content.Context;

import cav.theservices.utils.TheServiceApp;

public class DataManager {
    private static DataManager INSTANCE = null;

    private Context mContext;
    private PreferenseManager mPreferenseManager;

    public static DataManager getInstance() {
        if (INSTANCE==null){
            INSTANCE = new DataManager();
        }
        return INSTANCE;
    }
    public DataManager(){
        this.mContext = TheServiceApp.getContext();
        mPreferenseManager = new PreferenseManager();
    }

    public Context getContext() {
        return mContext;
    }

    public PreferenseManager getPreferenseManager() {
        return mPreferenseManager;
    }
}