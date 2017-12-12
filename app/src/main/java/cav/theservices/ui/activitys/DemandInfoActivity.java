package cav.theservices.ui.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import cav.theservices.R;
import cav.theservices.data.managers.DataManager;
import cav.theservices.data.models.DemandDeviceModel;
import cav.theservices.utils.ConstantManager;

public class DemandInfoActivity extends AppCompatActivity implements View.OnClickListener{

    private DataManager mDataManager;
    private String deviceID;
    private ArrayList<DemandDeviceModel> data;

    private TextView mDeviceNameTV;
    private TextView mCommentTV;


    private Button mCancel;
    private Button mWork;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demand_info);

        mDataManager = DataManager.getInstance();

        deviceID = getIntent().getStringExtra(ConstantManager.SELECTED_DEVICE);

        data = mDataManager.getDB().getDemandInDevice(deviceID);

        mDeviceNameTV = (TextView) findViewById(R.id.di_device_name);
        mCommentTV = (TextView) findViewById(R.id.di_demand_comment);

        mWork = (Button) findViewById(R.id.di_run_button);
        mWork.setOnClickListener(this);


        mDeviceNameTV.setText(data.get(0).getDeviceName());
        mCommentTV.setText(data.get(0).getComment());

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.di_run_button){

        }
    }
}
