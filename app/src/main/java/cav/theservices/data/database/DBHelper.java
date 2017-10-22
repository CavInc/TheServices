package cav.theservices.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBName = "tsr.db3";
    public static final int DATABASE_VERSION = 0 ;
    public static final String SERVICE_HEAD_TABLE = "service_head";
    public static final String SERVICE_SPEC_TABLE = "service_spec";


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
                    " big_img_file text)");

            db.execSQL("create table "+SERVICE_SPEC_TABLE+" (id integer not null,"+
                    " lang_id integer,"+
                    " title text,"+
                    " body text,"+
                    " primary key(id,lang_id))");

        }
    }
}