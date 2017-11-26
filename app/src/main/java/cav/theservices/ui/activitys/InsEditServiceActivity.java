package cav.theservices.ui.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import cav.theservices.R;
import cav.theservices.data.managers.DataManager;
import cav.theservices.data.models.LangDataModel;
import cav.theservices.data.models.ServiceEditModel;
import cav.theservices.data.networks.Request;

public class InsEditServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "INS";
    private Button mSaveButton;
    private EditText mTitleServ;
    private EditText mBodyServ;
    private EditText mPrice;

    private Spinner mSpinner;

    private DataManager mDataManager;


    private int oldSelectLang = 1;

    private ArrayList<LangDataModel> langData; // данный на 3 языках

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

        mSpinner = (Spinner) findViewById(R.id.sel_lang_serv);

        langData = new ArrayList<>(3);
        langData.add(new LangDataModel(0,"",""));
        langData.add(new LangDataModel(1,"",""));
        langData.add(new LangDataModel(2,"",""));

        String[] spData = new String[]{"Украинский","Русский","Анлийский"};
        ArrayAdapter<String> spAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,spData);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(spAdapter);
        mSpinner.setSelection(1);

        setSpinerListener();
    }

    private void setSpinerListener() {
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                changeLangEdit(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void changeLangEdit(int position) {
        if (langData.size() == 0 ) return;
        // сохраняем старые данные
        langData.set(oldSelectLang,
                new LangDataModel(oldSelectLang,mTitleServ.getText().toString(),mBodyServ.getText().toString()));

        int id = langData.indexOf(new LangDataModel(position,"",""));
        Log.d(TAG,String.valueOf(id)+" pos :"+position);
        if (id != -1) {
            // нашли
            mTitleServ.setText(langData.get(id).getTitle());
            mBodyServ.setText(langData.get(id).getBody());
        } else {
            // не нашли
            mTitleServ.setText("");
            mBodyServ.setText("");
        }
        oldSelectLang = position;
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
        Float price = 0.0f;
        if (mPrice.getText().length() != 0 ) {
            price = Float.valueOf(mPrice.getText().toString());
        }
        langData.set(oldSelectLang,new LangDataModel(oldSelectLang,title,body));

        final ServiceEditModel data = new ServiceEditModel(price," "," ",langData);
        int rec_id = mDataManager.getDB().addNewService(data);
        data.setId(rec_id);

        // если в режиме администратора то передаем данные на сеть
        if (mDataManager.getPreferenseManager().getAppMode()) {
            if (mDataManager.isOnline()){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Request request = new Request();
                        request.sendService(data);
                    }
                }).start();
            }
        }

        Intent answerIntent = new Intent();
        setResult(RESULT_OK,answerIntent);

    }
}
