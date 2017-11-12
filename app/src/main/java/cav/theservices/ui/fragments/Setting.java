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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import cav.theservices.R;
import cav.theservices.data.managers.DataManager;

public class Setting extends Fragment {

    private DataManager mDataManager;

    private Switch mAppMode;
    private EditText mMainLabel;
    private EditText mComandServer;
    private Switch mFullScreen;
    private EditText mAdmimPass;

    private EditText mDeviceName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.service_fragment,container,false);
        mDataManager = DataManager.getInstance();

        mAppMode = (Switch) view.findViewById(R.id.setting_app_mode);
        mFullScreen = (Switch) view.findViewById(R.id.setting_full_screen);

        mComandServer = (EditText) view.findViewById(R.id.setting_com_serv);
        mComandServer.addTextChangedListener(mComandServerListener);

        mMainLabel = (EditText) view.findViewById(R.id.setting_main_label);
        mMainLabel.addTextChangedListener(mELW);

        mAdmimPass = (EditText) view.findViewById(R.id.setting_admin_pass);
        mAdmimPass.addTextChangedListener(mPasswatcer);

        mDeviceName = (EditText) view.findViewById(R.id.setting_name_device);
        // заполняем поля
        mDeviceName.setText(mDataManager.getPreferenseManager().getNameDevice());
        mDeviceName.addTextChangedListener(mDeviceNameWatcher);

        mAppMode.setChecked(mDataManager.getPreferenseManager().getAppMode());
        mAppMode.setOnCheckedChangeListener(mAppChgListener);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        String lx = mDataManager.getPreferenseManager().getMainScreenLabel();
        if (lx != null && lx.length() != 0 ) {
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

    TextWatcher mComandServerListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            mDataManager.getPreferenseManager().setCommandServerUrl(editable.toString());
        }
    };

    TextWatcher mPasswatcer = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            mDataManager.getPreferenseManager().setAdminPassword(editable.toString());
        }
    };

    TextWatcher mDeviceNameWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            mDataManager.getPreferenseManager().setNameDevice(editable.toString());
        }
    };

    CompoundButton.OnCheckedChangeListener mAppChgListener = new CompoundButton.OnCheckedChangeListener(){

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            mDataManager.getPreferenseManager().setAppMode(b);
        }
    };


}