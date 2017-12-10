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

import cav.theservices.R;
import cav.theservices.data.managers.DataManager;
import cav.theservices.services.AdminGetRequestService;
import cav.theservices.services.GetDemandService;
import cav.theservices.ui.activitys.MainServiceActivity;
import cav.theservices.ui.adapters.DeviceMonitorAdapter;
import cav.theservices.utils.ConstantManager;
import cav.theservices.utils.Utils;

public class ServiceMonitorFragment extends Fragment{

    private static final String TAG = "SMF";
    private DataManager mDataManager;

    private UpdateReciver mUpdateReciver;

    private RecyclerView mRecyclerView ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataManager = DataManager.getInstance();

        //TODO временная затычка
        if (mDataManager.isOnline()) {
            // запрашиваем даные с командного сервера.
            getDevices();
        }
        // запускаем запросы новых услуг
        Utils.startAlarmGetDemand(getActivity());

        // ставим слушателя на обновление заявок
        mUpdateReciver = new UpdateReciver();
        IntentFilter updateIntentFilter = new IntentFilter(GetDemandService.ACTION_UPDATE);
        updateIntentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        getActivity().registerReceiver(mUpdateReciver, updateIntentFilter);
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

        DeviceMonitorAdapter adapter = new DeviceMonitorAdapter(mDataManager.getAllDevice());
        mRecyclerView.setAdapter(adapter);

        return view;
    }

    private void getDevices() {
        Intent intent = new Intent(getActivity(), AdminGetRequestService.class);
        intent.putExtra(ConstantManager.DEVICE_ID,mDataManager.getPreferenseManager().getAndroidID());
        getActivity().startService(intent);
    }

    public class UpdateReciver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG,"DEVICE UPDATE");
        }
    }
}