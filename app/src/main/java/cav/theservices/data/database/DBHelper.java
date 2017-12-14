package cav.theservices.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBName = "tsr.db3";
    public static final int DATABASE_VERSION = 1 ;
    public static final String SERVICE_HEAD_TABLE = "service_head";
    public static final String SERVICE_SPEC_TABLE = "service_spec";
    public static final String DEVICE_LIST_TABLE = "device_list";
    public static final String DEMAND_LIST_TABLE = "demand_list";


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updatedDB(db,0,DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updatedDB(db,oldVersion,newVersion);
    }

    private void updatedDB(SQLiteDatabase db,int oldVersion,int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("create table "+SERVICE_HEAD_TABLE+" ("+
                    "id integer not null primary key AUTOINCREMENT," +
                    " icon_file text,"+
                    " price numeric default 0,"+
                    " big_img_file text,"+
                    " status integer default 0)"); // 0 - локальный 1 - передан на сервер 2 - не передан на сервер нужно обработать 99 - удален.

            db.execSQL("create table "+SERVICE_SPEC_TABLE+" (id integer not null,"+
                    " lang_id integer not null,"+ //0 - украинский 1 - русский  2 -анлийский
                    " title text,"+
                    " body text,"+
                    " primary key(id,lang_id))");

            // список устройств (используется в админ режиме)
            db.execSQL("create table "+DEVICE_LIST_TABLE+" ("+
                    " device_id text not null primary key,"+
                    " deviceMode integer,"+"" +
                    " deviceName text)");

            // полученные заявки
            db.execSQL("create table "+DEMAND_LIST_TABLE+"("+
                    "id integer not null primary key,"+
                    "device_id text not null,"+
                    "service_id integer,"+
                    "demand_date text,"+
                    "status integer default 0,"+ // 0 - новый 1 - в работе 2 - выполнен
                    "comment text)");


        }
    }
}