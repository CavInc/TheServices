package cav.theservices.data.managers;

import android.content.SharedPreferences;

import cav.theservices.utils.ConstantManager;
import cav.theservices.utils.TheServiceApp;

public class PreferenseManager {
    private static final String MAIN_SCREEN_LABEL = "MAIN_SCREEN_LABEL";
    private static final String COMMAND_SERVER = "COMMAND_SERVER";
    private static final String APP_MODE = "APP_MODE";
    private static final String FULL_SCREEN = "FULL_SCREEN";
    private static final String ADMIN_PASSWORD = "ADMIN_PASSWORD";
    private static final String DEVICE_NAME = "DEVICE_NAME";
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

    // название на главном экране
    public String getMainScreenLabel (){
        return mSharedPreferences.getString(MAIN_SCREEN_LABEL,null);
    }
    public void setMainScreenLabel(String label){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(MAIN_SCREEN_LABEL,label);
        editor.apply();
    }

    // командный сервер
    public String getComandServerUrl(){
        return mSharedPreferences.getString(COMMAND_SERVER,null);
    }

    public void setCommandServerUrl(String url){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(COMMAND_SERVER,url);
        editor.apply();
    }

    // Режим работы приложения
    public boolean getAppMode() {
        return mSharedPreferences.getBoolean(APP_MODE,false);
    }

    public void setAppMode(boolean mode){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(APP_MODE,mode);
        editor.apply();
    }

    // полноэкранный режим или нет
    public boolean getFullScreen() {
        return mSharedPreferences.getBoolean(FULL_SCREEN,false);
    }

    public void setFullScreen(boolean mode){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(FULL_SCREEN,mode);
        editor.apply();
    }

    // пароль админа
    public String getAdminPassword(){
        return mSharedPreferences.getString(ADMIN_PASSWORD,null);
    }

    public void setAdminPassword(String password){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(ADMIN_PASSWORD,password);
        editor.apply();
    }

    // Имя устройства
    public String getNameDevice() {
        return mSharedPreferences.getString(DEVICE_NAME,null);
    }
    public void setNameDevice(String name) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(DEVICE_NAME,name);
        editor.apply();
    }



}