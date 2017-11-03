package cav.theservices.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import cav.theservices.data.models.LangDataModel;
import cav.theservices.data.models.ServiceEditModel;

public class DBConnect {
    private SQLiteDatabase database;
    private DBHelper mDBHelper;

    public DBConnect(Context context){
        mDBHelper = new DBHelper(context,DBHelper.DBName,null,DBHelper.DATABASE_VERSION);
    }

    public void open(){
        database = mDBHelper.getWritableDatabase();
    }

    public void close(){
        if (database!=null) {
            database.close();
        }
    }
    public boolean isOpen(){
        return database.isOpen();
    }

    //-------------------- Запросы к базе ------------------------------

    // добавим услугу
    public void addNewService(ServiceEditModel data){
        open();
        ContentValues values = new ContentValues();
        values.put("icon_file",data.getPhoto());
        values.put("price",data.getPrice());
        int res_id = (int) database.insert(DBHelper.SERVICE_HEAD_TABLE,null,values);

        for (LangDataModel l:data.getData()){
            values.clear();
            values.put("id",res_id);
            values.put("lang_id",l.getLang());
            values.put("title",l.getnTitle());
            values.put("body",l.getBody());
            database.insert(DBHelper.SERVICE_SPEC_TABLE,null,values);
        }
        close();
    }

    public Cursor getListService(int lang){
        String sql="select sh.id,sh.price,sh.icon_file,sh.price,sp.title,sp.body from "+DBHelper.SERVICE_HEAD_TABLE+" sh"+
                " left join "+DBHelper.SERVICE_SPEC_TABLE+" sp on sh.id=sp.id and sp.lang_id ="+lang;
        return database.rawQuery(sql,null);
    }

    public int getCountService(int lang){
        open();
        Cursor cursor = database.rawQuery("select count(1) from "+DBHelper.SERVICE_SPEC_TABLE+" where lang_id="+lang,null);
        cursor.moveToFirst();
        int res = cursor.getInt(0);
        close();
        return res;
    }

    public Cursor getLimitService(int lang,int start,int limit){
        String sql="select sh.id,sh.price,sh.icon_file,sh.price,sp.title,sp.body from "+DBHelper.SERVICE_HEAD_TABLE+" sh"+
                " left join "+DBHelper.SERVICE_SPEC_TABLE+" sp on sh.id=sp.id and sp.lang_id ="+lang+" limit "+limit+" offset "+start;

        Cursor cursor = database.rawQuery(sql,null);
        return cursor;
    }

    // возвращаем 1 карточку
    public Cursor getOneCard(int id,int lang){
        String sql="select sh.id,sh.price,sh.icon_file,sh.price,sp.title,sp.body,sh.big_img_file from "+DBHelper.SERVICE_HEAD_TABLE+" sh"+
                " left join "+DBHelper.SERVICE_SPEC_TABLE+" sp on sh.id=sp.id and sp.lang_id ="+lang+" where sh.id="+id;
        return database.rawQuery(sql,null);
    }


}