package cav.theservices.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import cav.theservices.R;
import cav.theservices.data.managers.DataManager;

public class Setting extends Fragment {

    private DataManager mDataManager;

    private Switch mAppMode;
    private EditText mMainLabel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.service_fragment,container,false);
        mDataManager = DataManager.getInstance();

        mAppMode = (Switch) view.findViewById(R.id.setting_app_mode);

        mMainLabel = (EditText) view.findViewById(R.id.setting_main_label);
        mMainLabel.setOnEditorActionListener(mEL);

        return view;
    }

    // возможно переделать .
    TextView.OnEditorActionListener mEL = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            mDataManager.getPreferenseManager().setMainScreenLabel(textView.getText().toString());
            return true;
        }
    };


}