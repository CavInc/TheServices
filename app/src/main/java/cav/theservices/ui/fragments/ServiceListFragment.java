package cav.theservices.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import cav.theservices.R;
import cav.theservices.data.managers.DataManager;
import cav.theservices.data.models.ServiceClientEditModel;
import cav.theservices.ui.adapters.ServiceListAdapterEdit;

public class ServiceListFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemLongClickListener {

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
        ArrayList <ServiceClientEditModel> model = new ArrayList<>();
        if (adapter == null){
            adapter = new ServiceListAdapterEdit(this.getContext(),R.layout.service_list_item,model);
            mListView.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        return true;
    }
}