package cav.theservices.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import cav.theservices.utils.ConstantManager;

/**
  для опроса по времени командного сервера
 */
public class AlarmReciver extends BroadcastReceiver {
    private Context mContext;
    private int mReceiverID;

    public AlarmReciver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");
        mContext = context;
        mReceiverID = intent.getIntExtra(ConstantManager.ALARM_TYPE,-1);
        Log.d("RECIVER ", String.valueOf(mReceiverID));
        if (mReceiverID == ConstantManager.ALARM_GETSERVICE){
            Intent intentService = new Intent(mContext, GetAllServiceService.class);
            mContext.startService(intentService);
        }
    }


}
