package cav.theservices.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cav.theservices.R;
import cav.theservices.data.models.ServiceClientModel;
/*
  адаптер для режима пользователя
 */
public class ServiceListAdapter extends ArrayAdapter <ServiceClientModel> {
    private LayoutInflater mInflater;
    private int resLayout;

    public ServiceListAdapter(Context context, int resource, List<ServiceClientModel> objects) {
        super(context, resource, objects);
        resLayout = resource;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View row = convertView;
        if (row == null) {
            row = mInflater.inflate(resLayout, parent, false);
            holder = new ViewHolder();

            row.setTag(holder);
        } else {
            holder = (ViewHolder)row.getTag();
        }
        ServiceClientModel record = getItem(position);

        return row;
    }

    class ViewHolder {

    }


}