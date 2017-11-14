package cav.theservices.data.managers;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

import java.util.ArrayList;

import cav.theservices.data.database.DBConnect;
import cav.theservices.data.models.ServiceClientEditModel;
import cav.theservices.data.models.ServiceClientModel;
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

    // проверяем включен ли интернетик
    public boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /*
    // запрос данных устройства
    public String getAndroidID(){
        return  Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
    */

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

    public ArrayList<ServiceClientModel> getLimitService(int lang, int start, int limit){
        ArrayList<ServiceClientModel> rec = new ArrayList<>();
        mDB.open();
        Cursor cursor = mDB.getLimitService(lang,start,limit);
        while (cursor.moveToNext()){
            rec.add(new ServiceClientModel(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("icon_file"))));
        }
        mDB.close();
        return rec;
    }

    public ServiceClientModel getOneCard(int id,int lang){
        mDB.open();
        Cursor cursor = mDB.getOneCard(id,lang);
        cursor.moveToFirst();
        ServiceClientModel model = new ServiceClientModel(cursor.getInt(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex("title")),
                cursor.getString(cursor.getColumnIndex("body")),
                cursor.getString(cursor.getColumnIndex("big_img_file")),
                cursor.getFloat(cursor.getColumnIndex("price"))
        );
        mDB.close();

        return model;
    }
}