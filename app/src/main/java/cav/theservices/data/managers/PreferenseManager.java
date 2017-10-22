package cav.theservices.data.managers;

import android.content.SharedPreferences;

import cav.theservices.utils.ConstantManager;
import cav.theservices.utils.TheServiceApp;

public class PreferenseManager {
    private SharedPreferences mSharedPreferences;

    public PreferenseManager() {
        mSharedPreferences = TheServiceApp.getSharedPreferences();
    }

    public SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

    // выбранный язык
    public int getSelectLand(){
        return mSharedPreferences.getInt(ConstantManager.SELECT_LANG,0);
    }

    public void setSelectLane(int lang) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putInt(ConstantManager.SELECT_LANG,lang);
        edit.apply();
    }
}