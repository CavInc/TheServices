package cav.theservices.ui.activitys;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cav.theservices.R;
import cav.theservices.data.managers.DataManager;
import cav.theservices.data.models.ServiceClientModel;
import cav.theservices.data.models.ServiceEditModel;
import cav.theservices.services.GetAllServiceService;
import cav.theservices.utils.ConstantManager;

public class MainServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MSA";

    private LinearLayout[] mLL;

    private ImageView[] mImageViews;
    private TextView[] mTextViews;

    private DataManager mDataManager;

    private int selLang;

    private ArrayList<ServiceClientModel> mDataModel;

    private int offset = 5;
    private int start = 0;
    private int limit = 5; // количество элементов для выборки

    private int directListing = ConstantManager.DIRECT_LEFT;

    private UpdateReciver mUpdateReciver;
    private int viewCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_service);

        mDataManager = DataManager.getInstance();

        selLang =   getIntent().getIntExtra(ConstantManager.SELECT_LANG,1);
        mLL = new LinearLayout[6];

        mLL[0] = (LinearLayout) findViewById(R.id.ms_lv_1);
        mLL[1] = (LinearLayout) findViewById(R.id.ms_lv_2);
        mLL[2] = (LinearLayout) findViewById(R.id.ms_lv_3);
        mLL[3] = (LinearLayout) findViewById(R.id.ms_lv_4);
        mLL[4] = (LinearLayout) findViewById(R.id.ms_lv_5);
        mLL[5] = (LinearLayout) findViewById(R.id.ms_lv_6);

        mLL[0].setOnClickListener(this);
        mLL[1].setOnClickListener(this);
        mLL[2].setOnClickListener(this);
        mLL[3].setOnClickListener(this);
        mLL[4].setOnClickListener(this);
        mLL[5].setOnClickListener(this);

        mImageViews = new ImageView[5];
        mTextViews = new TextView[5];

        mImageViews[0] = (ImageView) findViewById(R.id.ms_img1);
        mImageViews[1] = (ImageView) findViewById(R.id.ms_img2);
        mImageViews[2] = (ImageView) findViewById(R.id.ms_img3);
        mImageViews[3] = (ImageView) findViewById(R.id.ms_img4);
        mImageViews[4] = (ImageView) findViewById(R.id.ms_img5);

        mTextViews[0] = (TextView) findViewById(R.id.ms_title_1);
        mTextViews[1] = (TextView) findViewById(R.id.ms_title_2);
        mTextViews[2] = (TextView) findViewById(R.id.ms_title_3);
        mTextViews[3] = (TextView) findViewById(R.id.ms_title_4);
        mTextViews[4] = (TextView) findViewById(R.id.ms_title_5);

        mUpdateReciver = new UpdateReciver();

        IntentFilter updateIntentFilter = new IntentFilter(GetAllServiceService.ACTION_UPDATE);
        updateIntentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(mUpdateReciver, updateIntentFilter);

        getAllService();// тестовая для проверки

        setDataLang();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mUpdateReciver);
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG,"TAP");
        if (view.getId() == R.id.ms_lv_6){
            if (directListing == ConstantManager.DIRECT_LEFT) {
                start += offset;
            } else {
                start -= offset;
                if (start<0) start = 0;
            }
            setDataLang();
        } else {
            Intent intent = new Intent(this,CardServiceActivity.class);
            switch (view.getId()){
                case R.id.ms_lv_1:
                    intent.putExtra(ConstantManager.SERVICE_ID,mDataModel.get(0).getId());
                    break;
                case R.id.ms_lv_2:
                    intent.putExtra(ConstantManager.SERVICE_ID,mDataModel.get(1).getId());
                    break;
                case R.id.ms_lv_3:
                    intent.putExtra(ConstantManager.SERVICE_ID,mDataModel.get(2).getId());
                    break;
                case R.id.ms_lv_4:
                    intent.putExtra(ConstantManager.SERVICE_ID,mDataModel.get(3).getId());
                    break;
                case R.id.ms_lv_5:
                    intent.putExtra(ConstantManager.SERVICE_ID,mDataModel.get(4).getId());
                    break;
            }
            startActivity(intent);
        }

    }

    private void setDataLang(){
        int count = mDataManager.getDB().getCountService(1);
        if (count!=0 ) {
            for (int i = 0; i < 6; i++) {
                mLL[i].setVisibility(View.INVISIBLE);
            }
        }

        if (count > 6) {
            mLL[5].setVisibility(View.VISIBLE);
        }

        int i = 0;
        mDataModel =  mDataManager.getLimitService(selLang,start,limit);
        viewCount = mDataModel.size();

        if (viewCount<5 && count>6) {
            directListing = ConstantManager.DIRECT_RIGTH;
        } else {
            directListing = ConstantManager.DIRECT_LEFT;
        }

        for (ServiceClientModel l:mDataModel){
            //TODO добавить установку картинки
            mTextViews[i].setText(l.getTitle());
            mLL[i].setVisibility(View.VISIBLE);
            //Log.d(TAG, String.valueOf((l.getScreen().length())));
            if (l.getScreen().equals(" ")) {
                Picasso.with(this).load(R.drawable.nofoto).into(mImageViews[i]);
            } else {

            }
            i +=1;
        }
    }

    // тестовая для проверки
    private void getAllService(){
        Intent intent = new Intent(this, GetAllServiceService.class);
        startService(intent);
    }

    public interface ServiceChangeListener {
        public void changeListener();
    }

    public class UpdateReciver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG,"RECIVER UPDATE");
            setDataLang();
        }
    }
}
