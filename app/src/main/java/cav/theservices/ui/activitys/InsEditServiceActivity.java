package cav.theservices.ui.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cav.theservices.R;

public class InsEditServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mSaveButton;
    private EditText mTitleServ;
    private EditText mBodyServ;
    private EditText mPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ins_edit_service);

        mTitleServ = (EditText) findViewById(R.id.title_serv);
        mBodyServ = (EditText) findViewById(R.id.body_serv);
        mPrice = (EditText) findViewById(R.id.price_serv);

        mSaveButton = (Button) findViewById(R.id.save_serv);

        mSaveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }

    private void saveData(){

    }
}
