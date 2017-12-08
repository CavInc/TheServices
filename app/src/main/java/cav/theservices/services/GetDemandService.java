package cav.theservices.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import cav.theservices.data.networks.Request;

public class GetDemandService extends Service {
    public GetDemandService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        reqestTask();
        return  START_REDELIVER_INTENT;
    }

    private void reqestTask() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request();
                request.getAllNewDeamand();

                stopSelf();
            }
        }).start();

    }

    // PUSH
    // http://isizov.ru/android-push-notifications/
    // https://habrahabr.ru/post/274169/
}
