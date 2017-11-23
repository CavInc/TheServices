package cav.theservices.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import cav.theservices.R;

public class EditDeleteDialog extends DialogFragment implements View.OnClickListener{

    private EditDeleteListener mListener;

    @Override
    public void onClick(View view) {
        int id= view.getId();
        if (mListener!= null) {
            mListener.selectItem(id);
        }
        dismiss();
    }

    public interface EditDeleteListener {
        public void selectItem(int rid);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.edit_delete_dialog, null);
        v.findViewById(R.id.dialog_edit_item).setOnClickListener(this);
        v.findViewById(R.id.dialog_del_item).setOnClickListener(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("").setView(v);
        return builder.create();
    }

    public void setEditDeleteListener (EditDeleteListener listener){
        mListener = listener;
    }
}