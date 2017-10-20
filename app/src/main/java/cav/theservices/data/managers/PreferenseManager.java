package cav.theservices.data.managers;

import android.content.SharedPreferences;

import cav.theservices.utils.TheServiceApp;

public class PreferenseManager {
    private SharedPreferences mSharedPreferences;

    public PreferenseManager() {
        mSharedPreferences = TheServiceApp.getSharedPreferences();
    }

    public SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }
}