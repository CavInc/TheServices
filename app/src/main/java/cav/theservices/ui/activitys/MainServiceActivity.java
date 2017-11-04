package cav.theservices.ui.activitys;

import android.content.Intent;
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

        setDataLang();

    }

    @Override
    public void onClick(View view) {
        Log.d(TAG,"TAP");
        if (view.getId() == R.id.ms_lv_6){

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
        for (ServiceClientModel l:mDataModel){
            //TODO добавить установку картинки
            mTextViews[i].setText(l.getTitle());
            mLL[i].setVisibility(View.VISIBLE);
            Log.d(TAG, String.valueOf((l.getScreen().length())));
            if (l.getScreen().equals(" ")) {
                Picasso.with(this).load(R.drawable.nofoto).into(mImageViews[i]);
            } else {

            }
            i +=1;
        }
    }
}
