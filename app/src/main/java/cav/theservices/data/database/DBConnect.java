package cav.theservices.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import cav.theservices.data.models.DemandDeviceModel;
import cav.theservices.data.models.DemandModel;
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
    public int addNewService(ServiceEditModel data){
        open();
        ContentValues values = new ContentValues();
        values.put("icon_file",data.getPhoto());
        values.put("price",data.getPrice());
        if (data.getId() != -1) {
            values.put("id",data.getId());
        }

        int res_id = (int) database.insertWithOnConflict(DBHelper.SERVICE_HEAD_TABLE,null,values,SQLiteDatabase.CONFLICT_REPLACE);

        for (LangDataModel l:data.getData()){
            values.clear();
            values.put("id",res_id);
            values.put("lang_id",l.getLang());
            values.put("title",l.getTitle());
            values.put("body",l.getBody());
            database.insertWithOnConflict(DBHelper.SERVICE_SPEC_TABLE,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        }
        close();
        return res_id;
    }

    // меняем статуст
    public void updateStateService(int id,int status){
        open();
        ContentValues values = new ContentValues();
        values.put("status",status);
        database.update(DBHelper.SERVICE_HEAD_TABLE,values,"id="+id,null);
        close();
    }

    // удалим
    public void delService(int id){
        open();
        /*
        database.delete(DBHelper.SERVICE_HEAD_TABLE,"id="+id,null);
        database.delete(DBHelper.SERVICE_SPEC_TABLE,"id="+id,null);
        */
        ContentValues values = new ContentValues();
        values.put("status",id);
        database.update(DBHelper.SERVICE_HEAD_TABLE,values,"id="+id,null);
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

    // возвращаем 1 карточку для редактирования
    public ServiceEditModel getOneFullCard(int id) {
        ArrayList<LangDataModel> spec = new ArrayList<>();
        open();
        Cursor cursor = database.query(DBHelper.SERVICE_SPEC_TABLE,
                new String[]{"lang_id","title","body"},"id="+id,null,null,null,null);
        while (cursor.moveToNext()){
            spec.add(new LangDataModel(
                    cursor.getInt(cursor.getColumnIndex("lang_id")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("body"))
            ));
        }

        cursor = database.query(DBHelper.SERVICE_HEAD_TABLE,new String[]{"id","price"},"id="+id,null,null,null,null);
        cursor.moveToFirst();
        Float price = cursor.getFloat(cursor.getColumnIndex("price"));

        close();
        ServiceEditModel rec = new ServiceEditModel(id,price," "," ",spec);
        return rec;
    }

    // добавили устройство
    public void addDevices(String deviceid,int devicemode,String devicename){
        open();
        ContentValues values = new ContentValues();
        values.put("device_id",deviceid);
        values.put("deviceMode",devicemode);
        values.put("deviceName",devicename);
        database.insertWithOnConflict(DBHelper.DEVICE_LIST_TABLE,null,values,SQLiteDatabase.CONFLICT_IGNORE);
        close();

    }

    // читаем список устройств
    public  Cursor getAllDevices(){
        String sql = "select dl.device_id,deviceMode,deviceName,count(dml.device_id) as demandCount from device_list dl \n" +
                " LEFT join demand_list dml on dl.device_id=dml.device_id and dml.status = 0 \n" +
                "group by  dl.device_id,deviceMode,deviceName \n" +
                "order by dl.device_id";
        return database.rawQuery(sql,null);
        //return database.query(DBHelper.DEVICE_LIST_TABLE,new String[]{"device_id","deviceName","deviceMode"},null,null,null,null,"device_id");
    }

    // добавим полученные заявки
    public void addNewDemand(DemandModel data){
        open();
        ContentValues values = new ContentValues();
        values.put("id",data.getID());
        values.put("device_id",data.getDevice());
        values.put("service_id",data.getServiceID());
        values.put("comment",data.getComment());
        database.insertWithOnConflict(DBHelper.DEMAND_LIST_TABLE,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        close();
    }

    // получаем информацию о заявках на конкретное устройство
    public ArrayList<DemandDeviceModel>  getDemandInDevice(String deviceId){
        String sql = "select dl.device_id,dl.deviceName,dml.comment,dml.id as demandID,sh.id,sh.price,sh.status,dl.demand_date from device_list dl \n" +
                " LEFT join demand_list dml on dl.device_id=dml.device_id and dml.status = 0 \n" +
                " left join service_head sh on dml.service_id=sh.id\n" +
                "where dl.device_id='"+deviceId+"' ";

        ArrayList<DemandDeviceModel> rec = new ArrayList<>();
        open();
        Cursor cursor = database.rawQuery(sql,null);
        while (cursor.moveToNext()){

            rec.add(new DemandDeviceModel(
                    cursor.getString(cursor.getColumnIndex("device_id")),
                    cursor.getString(cursor.getColumnIndex("deviceName")),
                    cursor.getString(cursor.getColumnIndex("comment")),
                    cursor.getInt(cursor.getColumnIndex("demandID")),
                    cursor.getDouble(cursor.getColumnIndex("price")),
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("demand_date"))
            ));
        }
        close();
        return rec;
    }

    public void changeDemandStatus(int demandId,int status){
        open();
        ContentValues values = new ContentValues();
        values.put("status",status);
        database.update(DBHelper.DEMAND_LIST_TABLE,values,"id="+demandId,null);
        close();
    }

}