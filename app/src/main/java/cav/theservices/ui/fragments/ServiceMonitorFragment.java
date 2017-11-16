package cav.theservices.ui.fragments;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cav.theservices.R;
import cav.theservices.data.managers.DataManager;

public class ServiceMonitorFragment extends Fragment{

    private DataManager mDataManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataManager = DataManager.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.service_monitor_fragment,container,false);

        if (mDataManager.isOnline()) {
            // запрашиваем даные с командного сервера.
        }

        return view;
    }
}