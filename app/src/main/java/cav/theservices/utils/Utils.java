package cav.theservices.utils;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cav.theservices.services.AlarmReciver;

public class Utils {

    public static void startAlarmGetRequest(){

    }

    public static void startAlarmGetService(Context context){
        Log.d("UTIL","START ALARM");
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(context, AlarmReciver.class);
        intent.putExtra(ConstantManager.ALARM_TYPE,ConstantManager.ALARM_GETSERVICE);
        PendingIntent pi= PendingIntent.getBroadcast(context,8700, intent,0);

        int period = 2; // 2 минуты
        am.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+(1000*60*period),pi);

    }

    public static void startAlarmGetDemand(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,AlarmReciver.class);
        intent.putExtra(ConstantManager.ALARM_TYPE,ConstantManager.ALARM_DEMAND);
        PendingIntent pi = PendingIntent.getBroadcast(context,8710,intent,0);

        int period = 30;
        am.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+(1000*period),pi);
    }

    public static Date StrToDateMask(String data, String mask) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(mask);
        return format.parse(data);
    }
}


