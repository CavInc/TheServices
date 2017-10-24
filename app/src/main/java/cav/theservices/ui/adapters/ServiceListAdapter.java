package cav.theservices.ui.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

import cav.theservices.data.models.ServiceClientModel;

/*
  адаптер для режима пользователя
 */
public class ServiceListAdapter extends ArrayAdapter <ServiceClientModel> {

    public ServiceListAdapter(Context context, int resource, List<ServiceClientModel> objects) {
        super(context, resource, objects);
    }


}