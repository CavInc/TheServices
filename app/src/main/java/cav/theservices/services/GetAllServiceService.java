package cav.theservices.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.ArrayList;

import cav.theservices.data.managers.DataManager;
import cav.theservices.data.models.ServiceEditModel;
import cav.theservices.data.networks.Request;
import cav.theservices.utils.Utils;

/**
 * для чтения новых сервисов с сервера
 */

public class GetAllServiceService extends Service {
    private DataManager mDataManager;

    public static final String ACTION_UPDATE = "ru.alexanderklimov.intentservice.UPDATE";

    public GetAllServiceService() {
        mDataManager = DataManager.getInstance();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        requestTask();
        //return START_REDELIVER_INTENT;
        return START_NOT_STICKY;
    }

    private void requestTask() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request();
                ArrayList<ServiceEditModel> serviceList = request.getAllService(mDataManager.getPreferenseManager().getAndroidID());
                for (ServiceEditModel l : serviceList) {
                    mDataManager.getDB().addNewService(l);
                }
                Utils.startAlarmGetService(mDataManager.getContext());

                Intent updateIntent = new Intent();
                updateIntent.setAction(ACTION_UPDATE);
                updateIntent.addCategory(Intent.CATEGORY_DEFAULT);
                sendBroadcast(updateIntent);

                stopSelf();
            }
        }).start();
    }
}
