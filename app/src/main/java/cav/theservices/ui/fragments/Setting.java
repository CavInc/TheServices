package cav.theservices.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import cav.theservices.R;

public class Setting extends Fragment {

    private Switch mAppMode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.service_fragment,container,false);
        mAppMode = (Switch) view.findViewById(R.id.setting_app_mode);

        return view;
    }


}