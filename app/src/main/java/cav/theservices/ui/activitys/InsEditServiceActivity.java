package cav.theservices.ui.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import cav.theservices.R;
import cav.theservices.data.managers.DataManager;
import cav.theservices.data.models.LangDataModel;
import cav.theservices.data.models.ServiceEditModel;

public class InsEditServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mSaveButton;
    private EditText mTitleServ;
    private EditText mBodyServ;
    private EditText mPrice;

    private DataManager mDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ins_edit_service);

        mDataManager = DataManager.getInstance();

        mTitleServ = (EditText) findViewById(R.id.title_serv);
        mBodyServ = (EditText) findViewById(R.id.body_serv);
        mPrice = (EditText) findViewById(R.id.price_serv);

        mSaveButton = (Button) findViewById(R.id.save_serv);

        mSaveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.save_serv){
            saveData();
            finish();
        }
    }

    private void saveData(){
        String title = mTitleServ.getText().toString();
        String body = mBodyServ.getText().toString();
        Float price = Float.valueOf(mPrice.getText().toString());
        LangDataModel ld = new LangDataModel(1,title,body); // поменять

        ArrayList<LangDataModel> rec = new ArrayList<>();
        rec.add(ld);

        ServiceEditModel data = new ServiceEditModel(price," "," ",rec);
        mDataManager.getDB().addNewService(data);

        Intent answerIntent = new Intent();
        setResult(RESULT_OK,answerIntent);

    }
}
