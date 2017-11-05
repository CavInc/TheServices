package cav.theservices.ui.dialogs;

import android.app.DialogFragment;

public class AdminPasswordDialog extends DialogFragment {

    public interface OnRequestListener {
        public void OnPositive();
        public void OnNegative();
    }

}