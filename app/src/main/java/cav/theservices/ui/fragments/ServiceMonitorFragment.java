package cav.theservices.ui.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cav.theservices.R;
import cav.theservices.data.managers.DataManager;
import cav.theservices.data.models.DeviceModel;
import cav.theservices.services.AdminGetRequestService;
import cav.theservices.services.GetDemandService;
import cav.theservices.ui.activitys.DemandInfoActivity;
import cav.theservices.ui.activitys.MainServiceActivity;
import cav.theservices.ui.adapters.DeviceMonitorAdapter;
import cav.theservices.utils.ConstantManager;
import cav.theservices.utils.Utils;

public class ServiceMonitorFragment extends Fragment{

    private static final String TAG = "SMF";
    private DataManager mDataManager;

    private UpdateReciver mUpdateReciver;

    private RecyclerView mRecyclerView ;

    private DeviceMonitorAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataManager = DataManager.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG,"START");

        //TODO временная затычка
        if (mDataManager.isOnline()) {
            // запрашиваем даные с командного сервера.
            getDevices();
        }

        // запускаем запросы новых услуг
        if (mDataManager.getPreferenseManager().getAppMode()) {
            Utils.startAlarmGetDemand(getActivity());
        }

        if (mDataManager.getPreferenseManager().getAppMode()) {
            // ставим слушателя на обновление заявок
            mUpdateReciver = new UpdateReciver();
            IntentFilter updateIntentFilter = new IntentFilter(GetDemandService.ACTION_UPDATE);
            updateIntentFilter.addCategory(Intent.CATEGORY_DEFAULT);
            getActivity().registerReceiver(mUpdateReciver, updateIntentFilter);
        }
        updateUI();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG,"PAUSE");
        if (mUpdateReciver != null) {
            getActivity().unregisterReceiver(mUpdateReciver);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG,"STOP");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.service_monitor_fragment,container,false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.monitor_rv);

        if (mDataManager.isOnline()) {
            // запрашиваем даные с командного сервера.
        }
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 4);
        mRecyclerView.setLayoutManager(linearLayoutManager);



        return view;
    }

    private void updateUI(){
        ArrayList<DeviceModel> model = mDataManager.getAllDevice();

        if (adapter == null) {
            adapter = new DeviceMonitorAdapter(model,mCustomClickListener);
            mRecyclerView.setAdapter(adapter);
        }else {
            adapter.setData(model);
            adapter.notifyDataSetChanged();
        }

    }

    private void getDevices() {
        Intent intent = new Intent(getActivity(), AdminGetRequestService.class);
        intent.putExtra(ConstantManager.DEVICE_ID,mDataManager.getPreferenseManager().getAndroidID());
        getActivity().startService(intent);
    }

    DeviceMonitorAdapter.DeviceMonitorHolder.CustomClickListener mCustomClickListener = new DeviceMonitorAdapter.DeviceMonitorHolder.CustomClickListener() {
        @Override
        public void onUserItemClickListener(int adapterPosition) {
            Log.d(TAG,String.valueOf(adapterPosition));
            DeviceModel model = (DeviceModel) adapter.getItemPost(adapterPosition);
            if (model.getDemandCount() !=0 ) {
                Intent intent = new Intent(getActivity(), DemandInfoActivity.class);
                intent.putExtra(ConstantManager.SELECTED_DEVICE, model.getDeviceID());
                startActivity(intent);
            }
        }
    };

    public class UpdateReciver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG,"DEVICE UPDATE");
            updateUI();
        }
    }


}