package cav.theservices.ui.activitys;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import cav.theservices.R;
import cav.theservices.data.managers.DataManager;
import cav.theservices.data.models.DemandDeviceModel;
import cav.theservices.data.networks.Request;
import cav.theservices.utils.ConstantManager;

public class DemandInfoActivity extends AppCompatActivity implements View.OnClickListener{

    private DataManager mDataManager;
    private String deviceID;
    private ArrayList<DemandDeviceModel> data;

    private TextView mDeviceNameTV;
    private TextView mCommentTV;
    private TextView mDemandDateTV;


    private Button mCancel;
    private Button mWork;
    private Button mClose;

    private int currentIndex = 0; // текущий индекс заявки

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demand_info);

        mDataManager = DataManager.getInstance();

        deviceID = getIntent().getStringExtra(ConstantManager.SELECTED_DEVICE);

        data = mDataManager.getDB().getDemandInDevice(deviceID);

        mDeviceNameTV = (TextView) findViewById(R.id.di_device_name);
        mCommentTV = (TextView) findViewById(R.id.di_demand_comment);
        mDemandDateTV = (TextView) findViewById(R.id.di_demand_date);

        mWork = (Button) findViewById(R.id.di_run_button);
        mWork.setOnClickListener(this);

        mClose = (Button) findViewById(R.id.di_close);
        mClose.setOnClickListener(this);


        mDeviceNameTV.setText(data.get(currentIndex).getDeviceName());
        mCommentTV.setText(data.get(currentIndex).getComment());
        mDemandDateTV.setText(data.get(currentIndex).getDate());

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.di_run_button){
            // TODO для проверки
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Request request = new Request();
                    request.changeDemandStatus(data.get(currentIndex).getDemandId(),ConstantManager.DEMAND_STATUS_OK);
                    // сменили в базе
                    mDataManager.getDB().changeDemandStatus(data.get(currentIndex).getDemandId(),ConstantManager.DEMAND_STATUS_OK);
                    data.remove(currentIndex);
                    currentIndex +=1;
                    if (currentIndex>data.size()) {
                        finish();
                    }
                }
            }).start();

        }
        if (view.getId() == R.id.di_close){
            finish();
        }
    }

    // опрос надо в AsynkTak

    private class ChangeStatus extends AsyncTask<Void,Void,Void> {

        private int mDemandID;
        private int mStatus;

        public ChangeStatus(int demandid,int status){
            mDemandID = demandid;
            mStatus = status;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

        }
    }

}
