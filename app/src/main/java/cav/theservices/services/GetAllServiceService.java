package cav.theservices.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * для чтения новых сервисов с сервера
 */

public class GetAllServiceService extends Service {
    public GetAllServiceService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
