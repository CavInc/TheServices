package cav.theservices.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;

import cav.theservices.data.managers.DataManager;
import cav.theservices.data.models.DeviceModel;
import cav.theservices.data.networks.Request;

// запрос данных с сервера
public class AdminGetRequestService extends Service {

    private DataManager mDataManager;

    public AdminGetRequestService() {
        mDataManager = DataManager.getInstance();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        requestTask ();
        return START_REDELIVER_INTENT;
    }

    private void requestTask (){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request();
                ArrayList<DeviceModel> data = request.getAllDevice();
                for (DeviceModel l:data){
                    Log.d("RS",l.getDeviceID()+" "+l.getDeviceText());
                }

                stopSelf();
            }
        }).start();

    }
}
