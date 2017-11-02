package cav.theservices.ui.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cav.theservices.R;
import cav.theservices.data.managers.DataManager;
import cav.theservices.utils.ConstantManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener {

    private ImageView mLogo;
    private Button mLangButton1;
    private Button mLangButton2;
    private Button mLangButton3;
    private ImageView mLogoView;

    private DataManager mDataManager;

    private TextView mMainLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDataManager = DataManager.getInstance();

        mLogoView = (ImageView) findViewById(R.id.main_logo);

        mMainLabel = (TextView) findViewById(R.id.main_label);

        mLangButton1 = (Button) findViewById(R.id.main_bt_bt1);
        mLangButton2 = (Button) findViewById(R.id.main_bt_bt2);
        mLangButton3 = (Button) findViewById(R.id.main_bt_bt3);

        mLangButton1.setOnClickListener(this);
        mLangButton2.setOnClickListener(this);
        mLangButton3.setOnClickListener(this);

        mLogoView.setOnLongClickListener(this);

        String ml = mDataManager.getPreferenseManager().getMainScreenLabel();
        if (ml!= null) {
            mMainLabel.setText(ml);
        }

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this,MainServiceActivity.class);
        switch (view.getId()){
            case R.id.main_bt_bt1:
                intent.putExtra(ConstantManager.SELECT_LANG,0);
                break;
            case R.id.main_bt_bt2:
                intent.putExtra(ConstantManager.SELECT_LANG,1);
                break;
            case R.id.main_bt_bt3:
                intent.putExtra(ConstantManager.SELECT_LANG,2);
                break;
        }
        startActivity(intent);

    }

    @Override
    public boolean onLongClick(View view) {
        Intent admin = new Intent(this,AdminActivity.class);
        startActivity(admin);
        return true;
    }
}
