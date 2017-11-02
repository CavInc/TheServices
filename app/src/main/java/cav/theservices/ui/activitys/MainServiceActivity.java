package cav.theservices.ui.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cav.theservices.R;
import cav.theservices.data.managers.DataManager;
import cav.theservices.data.models.ServiceClientModel;
import cav.theservices.data.models.ServiceEditModel;
import cav.theservices.utils.ConstantManager;

public class MainServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MSA";
    private LinearLayout mLL1;
    private LinearLayout mLL2;
    private LinearLayout mLL3;
    private LinearLayout mLL4;
    private LinearLayout mLL5;
    private LinearLayout mLL6;

    private ImageView[] mImageViews;
    private TextView[] mTextViews;

    private DataManager mDataManager;

    private int selLang;

    private int offset = 5;
    private int start = 0;
    private int limit = 5; // количество элементов для выборки

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_service);

        mDataManager = DataManager.getInstance();

        selLang =   getIntent().getIntExtra(ConstantManager.SELECT_LANG,1);

        mLL1 = (LinearLayout) findViewById(R.id.ms_lv_1);
        mLL2 = (LinearLayout) findViewById(R.id.ms_lv_2);
        mLL3 = (LinearLayout) findViewById(R.id.ms_lv_3);
        mLL4 = (LinearLayout) findViewById(R.id.ms_lv_4);
        mLL5 = (LinearLayout) findViewById(R.id.ms_lv_5);
        mLL6 = (LinearLayout) findViewById(R.id.ms_lv_6);

        mLL1.setOnClickListener(this);
        mLL2.setOnClickListener(this);
        mLL3.setOnClickListener(this);
        mLL4.setOnClickListener(this);
        mLL5.setOnClickListener(this);
        mLL6.setOnClickListener(this);

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

        setDataLang();

    }

    @Override
    public void onClick(View view) {
        Log.d(TAG,"TAP");
        if (view.getId() == R.id.ms_lv_6){

        }

    }

    private void setDataLang(){
        int count = mDataManager.getDB().getCountService(1);
        if (count< 6) {
            mLL6.setEnabled(false);
        }

        int i = 0;
        ArrayList<ServiceClientModel> data =  mDataManager.getLimitService(selLang,start,limit);
        for (ServiceClientModel l:data){
            //TODO добавить установку картинки
            mTextViews[i].setText(l.getTitle());
            i +=1;
        }

    }
}
