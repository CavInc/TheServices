package cav.theservices.ui.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import cav.theservices.R;

public class MainServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout mLL1;
    private LinearLayout mLL2;
    private LinearLayout mLL3;
    private LinearLayout mLL4;
    private LinearLayout mLL5;
    private LinearLayout mLL6;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_service);
    }

    @Override
    public void onClick(View view) {

    }
}
