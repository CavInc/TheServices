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
        return START_REDELIVER_INTENT;
    }

    private void requestTask() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request();
                ArrayList<ServiceEditModel> serviceList = request.getAllService();
                for (ServiceEditModel l : serviceList) {
                    mDataManager.getDB().addNewService(l);
                }
                Utils.startAlarmGetService(mDataManager.getContext());
            }
        }).start();
    }
}
