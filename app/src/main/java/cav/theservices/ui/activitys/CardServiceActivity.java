package cav.theservices.ui.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import cav.theservices.R;
import cav.theservices.data.managers.DataManager;
import cav.theservices.data.models.ServiceClientModel;
import cav.theservices.data.networks.Request;
import cav.theservices.utils.ConstantManager;

public class CardServiceActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "CSA";
    private DataManager mDataManager;
    private ServiceClientModel mModel;

    private ImageView mBigImage;
    private TextView mTitle;
    private TextView mBody;
    private TextView mPrice;

    private EditText mComment;

    private Button mSendButton;

    private int mRecordID;
    private String mDeviceID = "0000000000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_service);

        mDataManager = DataManager.getInstance();


        mRecordID = getIntent().getIntExtra(ConstantManager.SERVICE_ID,-1); // id записи
        int lang = getIntent().getIntExtra(ConstantManager.SELECT_LANG,1); // id языка

        mModel = mDataManager.getOneCard(mRecordID,lang);

        mBigImage = (ImageView) findViewById(R.id.card_img);
        mTitle  = (TextView) findViewById(R.id.card_title);
        mBody = (TextView) findViewById(R.id.card_spec);
        mPrice = (TextView) findViewById(R.id.card_price);
        mComment = (EditText) findViewById(R.id.card_comment);

        mSendButton = (Button) findViewById(R.id.card_button);
        mSendButton.setOnClickListener(this);

        setupData();

    }

    private void setupData() {
        mTitle.setText(mModel.getTitle());
        mBody.setText(mModel.getBody());
        mPrice.setText(String.valueOf(mModel.getPrice()));
        if (mModel.getScreen() == null || mModel.getScreen().length() == 1){
            Picasso.with(this).load(R.drawable.nofoto).into(mBigImage);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.card_button) {
            Log.d(TAG,"SEND DEMAND");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Request request = new Request();
                    request.sendDemand(mDeviceID,mRecordID);
                }
            }).start();
        }
    }
}
