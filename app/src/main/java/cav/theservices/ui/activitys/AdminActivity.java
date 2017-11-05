package cav.theservices.ui.activitys;

import android.preference.PreferenceFragment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import cav.theservices.R;
import cav.theservices.ui.fragments.ServiceListFragment;
import cav.theservices.ui.fragments.ServiceMonitorFragment;
import cav.theservices.ui.fragments.Setting;

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener {
    private Toolbar mToolbar;
    private DrawerLayout mNavigationDrawer;

    private Fragment mServiceMonitor;
    private Fragment mServiceList;
    private Fragment mSetting;

    private Button mMonitorBt;
    private Button mServiceBt;
    private Button mSettingBt;

    public AdminActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mNavigationDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        mServiceMonitor = new ServiceMonitorFragment();
        mServiceList = new ServiceListFragment();
        mSetting = new Setting();

        mMonitorBt = (Button) findViewById(R.id.admin_monitor);
        mServiceBt = (Button) findViewById(R.id.admin_service);
        mSettingBt = (Button) findViewById(R.id.admin_setting);

        mMonitorBt.setOnClickListener(this);
        mServiceBt.setOnClickListener(this);
        mSettingBt.setOnClickListener(this);

        setupToolBar();
        setupDrower();
        changeFragment(mServiceMonitor);
    }

    private void setupDrower() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
      //  navigationView.setCheckedItem(R.id.drawer_tr);
       navigationView.setNavigationItemSelectedListener(this);
    }


    private void setupToolBar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            mNavigationDrawer.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.am_monitor:
                changeFragment(mServiceMonitor);
                break;
            case R.id.am_service:
                changeFragment(mServiceList);
                break;
            case R.id.am_setting:
                changeFragment(mSetting);
                break;
        }
        mNavigationDrawer.closeDrawer(GravityCompat.START);
        return false;
    }

    private void changeFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frgmCont,fragment).commit();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.admin_monitor:
                setingTitle("Наблюдение");
                changeFragment(mServiceMonitor);
                break;
            case R.id.admin_service:
                setingTitle("Услуги");
                changeFragment(mServiceList);
                break;
            case R.id.admin_setting:
                setingTitle("Настройки");
                changeFragment(mSetting);
                break;
        }

    }

    private void setingTitle(String title){
        mToolbar.setTitle(title);
    }
}
