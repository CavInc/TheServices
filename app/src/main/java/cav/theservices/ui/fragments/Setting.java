package cav.theservices.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
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
        mMainLabel.addTextChangedListener(mELW);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        String lx = mDataManager.getPreferenseManager().getMainScreenLabel();
        if (lx.length() != 0 ) {
            mMainLabel.setText(lx);
        }
    }

    TextWatcher mELW = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            mDataManager.getPreferenseManager().setMainScreenLabel(editable.toString());
        }
    };


}