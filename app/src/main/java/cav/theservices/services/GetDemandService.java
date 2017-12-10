package cav.theservices.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;

import cav.theservices.data.managers.DataManager;
import cav.theservices.data.models.DemandModel;
import cav.theservices.data.networks.Request;
import cav.theservices.utils.ConstantManager;
import cav.theservices.utils.Utils;

public class GetDemandService extends Service {
    private DataManager mDataManager;
    public GetDemandService() {
        mDataManager = DataManager.getInstance();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("GDS","YES ALRRM");
        reqestTask();
        return  START_REDELIVER_INTENT;
    }

    private void reqestTask() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request();
                ArrayList<DemandModel> model = request.getAllNewDeamand();

                for (DemandModel l:model){
                    mDataManager.getDB().addNewDemand(l);
                    setNotification(l);
                }

                Utils.startAlarmGetDemand(mDataManager.getContext());

                stopSelf();
            }
        }).start();

    }

    private void setNotification(DemandModel l){
        NotificationManager notificationManager = (NotificationManager) mDataManager.getContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = null;
        Notification.Builder builder = new Notification.Builder(mDataManager.getContext());

        // пока пустой intent только сообщения
        Intent intent = new Intent();
        PendingIntent pi = PendingIntent.getActivity(mDataManager.getContext(),l.getID(),intent,PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pi)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setTicker("Заявка")
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Новая заявка")
                .setContentText("А тут у нас должно быть сообщение о услуге и номер откуда пришло")
                .setOngoing(true)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT < 16){
            notification = builder.getNotification(); // до API 16
        }else{
            notification = builder.build();
        }


        notificationManager.notify(ConstantManager.NOTIFY_ID+l.getID(), notification);

    }

    // PUSH
    // http://isizov.ru/android-push-notifications/
    // https://habrahabr.ru/post/274169/
}
