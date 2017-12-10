package cav.theservices.ui.activitys;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cav.theservices.R;
import cav.theservices.data.managers.DataManager;
import cav.theservices.data.networks.Request;
import cav.theservices.utils.ConstantManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener {

    private static final String TAG = "MAINAP";
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
        mDataManager = DataManager.getInstance();
        if (mDataManager.getPreferenseManager().getFullScreen()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        setContentView(R.layout.activity_main);

        mLogoView = (ImageView) findViewById(R.id.main_logo);

        mMainLabel = (TextView) findViewById(R.id.main_label);

        mLangButton1 = (Button) findViewById(R.id.main_bt_bt1);
        mLangButton2 = (Button) findViewById(R.id.main_bt_bt2);
        mLangButton3 = (Button) findViewById(R.id.main_bt_bt3);

        mLangButton1.setOnClickListener(this);
        mLangButton2.setOnClickListener(this);
        mLangButton3.setOnClickListener(this);

        mLogoView.setOnLongClickListener(this);

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
        showLoginInAdminPanel();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        String ml = mDataManager.getPreferenseManager().getMainScreenLabel();
        if (ml!= null) {
            mMainLabel.setText(ml);
        }

        // добавить проверку на сеть и прочеее
        if (mDataManager.isOnline()) {
            registry();
        }
        if (mDataManager.getPreferenseManager().getAppMode()) {
            Log.d(TAG,"ADMIN MODE");
            startAdmin();
        }
    }




    private void registry(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                int mode = ConstantManager.USER_MODE;
                if (mDataManager.getPreferenseManager().getAppMode()) {
                    mode = ConstantManager.ADMIN_MODE;
                }

                Request request = new Request();
                request.registry(mDataManager.getPreferenseManager().getAndroidID(),
                        mode,
                        mDataManager.getPreferenseManager().getNameDevice());
            }
        }).start();

    }

    private void showLoginInAdminPanel(){
        View v =  LayoutInflater.from(this).inflate(R.layout.admin_panel_pasword,null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Пароль на вход в панель администратора")
                .setView(v)
                .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startAdmin();
                    }
                })
                .setNegativeButton(R.string.button_cancel,null)
                .create();
        builder.show();
    }

    private void startAdmin() {
        Intent admin = new Intent(MainActivity.this,AdminActivity.class);
        startActivity(admin);
    }
}
