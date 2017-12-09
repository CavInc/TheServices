package cav.theservices.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;

import cav.theservices.data.managers.DataManager;
import cav.theservices.data.models.DemandModel;
import cav.theservices.data.networks.Request;
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

                }

                Utils.startAlarmGetDemand(mDataManager.getContext());

                stopSelf();
            }
        }).start();

    }

    // PUSH
    // http://isizov.ru/android-push-notifications/
    // https://habrahabr.ru/post/274169/
}
