package cav.theservices.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
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
import cav.theservices.data.networks.Request;
import cav.theservices.ui.activitys.InsEditServiceActivity;
import cav.theservices.ui.adapters.ServiceListAdapterEdit;
import cav.theservices.ui.dialogs.EditDeleteDialog;
import cav.theservices.utils.ConstantManager;

public class ServiceListFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemLongClickListener {

    private static final int NEW_RECORD = 100;
    private static final int EDIT_RECORD = 101;

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
            adapter.setData(model);
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
            intent.putExtra(ConstantManager.MODE_WORK,ConstantManager.SERVICE_NEW);
            startActivityForResult(intent,NEW_RECORD);
        }

    }

    private int selectID;

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
        ServiceClientEditModel model = (ServiceClientEditModel) adapterView.getItemAtPosition(position);
        selectID = model.getId();
        EditDeleteDialog dialog = new EditDeleteDialog();
        dialog.setEditDeleteListener(mEditDeleteListener);
        dialog.show(getFragmentManager(),"ED");
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK ){
            updateUI();
        }
    }

    EditDeleteDialog.EditDeleteListener mEditDeleteListener = new EditDeleteDialog.EditDeleteListener() {

        @Override
        public void selectItem(int rid) {
            if (rid == R.id.dialog_del_item) {
                //TODO сдеся удаление объекта пока без удаления на сервере.
                //mDataManager.getDB().delService(selectID);
                //updateUI();
                new DeleteTask(selectID).execute();
            }
            if (rid == R.id.dialog_edit_item) {
                Intent intent = new Intent(getActivity(),InsEditServiceActivity.class);
                intent.putExtra(ConstantManager.MODE_WORK,ConstantManager.SERVICE_EDIT);
                intent.putExtra(ConstantManager.SELECTED_ID,selectID);
                startActivityForResult(intent,EDIT_RECORD);
            }
        }
    };

    private class DeleteTask extends AsyncTask<Void,Void,Void> {
        private int mServiceID;

        public DeleteTask(int serviceID){
            mServiceID = serviceID;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Request request = new Request();
            request.delService(mServiceID);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //mDataManager.getDB().delService(mServiceID);
            mDataManager.getDB().updateStateService(mServiceID,99); // удалили
            updateUI();
        }
    }
}