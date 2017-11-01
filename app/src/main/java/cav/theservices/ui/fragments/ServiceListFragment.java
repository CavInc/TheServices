package cav.theservices.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import cav.theservices.R;
import cav.theservices.data.managers.DataManager;
import cav.theservices.data.models.ServiceClientEditModel;
import cav.theservices.ui.activitys.InsEditServiceActivity;
import cav.theservices.ui.adapters.ServiceListAdapterEdit;

public class ServiceListFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemLongClickListener {

    private static final int NEW_RECORD = 100;
    private static final String TAG = "SLFAGMENT";
    private DataManager mDataManager;

    private ListView mListView;
    private FloatingActionButton mFab;
    private ServiceListAdapterEdit adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDataManager = DataManager.getInstance();

        View view = inflater.inflate(R.layout.service_list_fragment,container,false);

        mListView = (ListView) view.findViewById(R.id.sr_list_listview);
        mFab = (FloatingActionButton) view.findViewById(R.id.sr_list_fab);

        mFab.setOnClickListener(this);
        mListView.setOnItemLongClickListener(this);

        return view;
    }

    private void updateUI(){
        ArrayList <ServiceClientEditModel> model = mDataManager.getServiceListEdit();
        if (adapter == null){
            adapter = new ServiceListAdapterEdit(this.getContext(),R.layout.service_list_item,model);
            mListView.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG,"On START");
        updateUI();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.sr_list_fab){
            Intent intent = new Intent(getActivity(), InsEditServiceActivity.class);
            startActivityForResult(intent,NEW_RECORD);
        }

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK ){
            updateUI();
        }
    }
}