package cav.theservices.data.managers;

import android.content.SharedPreferences;

import cav.theservices.utils.ConstantManager;
import cav.theservices.utils.TheServiceApp;

public class PreferenseManager {
    private static final String MAIN_SCREEN_LABEL = "MAIN_SCREEN_LABEL";
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

    public String getMainScreenLabel (){
        return mSharedPreferences.getString(MAIN_SCREEN_LABEL,null);
    }
    public void setMainScreenLabel(String label){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(MAIN_SCREEN_LABEL,label);
        editor.apply();
    }

}