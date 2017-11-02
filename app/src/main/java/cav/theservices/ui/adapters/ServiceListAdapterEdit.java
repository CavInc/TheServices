package cav.theservices.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cav.theservices.R;
import cav.theservices.data.models.ServiceClientEditModel;
import cav.theservices.data.models.ServiceClientModel;


public class ServiceListAdapterEdit extends ArrayAdapter<ServiceClientEditModel> {
    private LayoutInflater mInflater;
    private int resLayout;
    private ArrayList<ServiceClientEditModel> mData;

    public ServiceListAdapterEdit(Context context, int resource, List<ServiceClientEditModel> objects) {
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
            holder.mTitle = (TextView) row.findViewById(R.id.item_serv_title);
            holder.mBody = (TextView) row.findViewById(R.id.item_serv_body);
            holder.mPrice = (TextView) row.findViewById(R.id.item_serv_price);
            row.setTag(holder);
        } else {
            holder = (ViewHolder)row.getTag();
        }
        ServiceClientEditModel record = getItem(position);
        holder.mTitle.setText(record.getTitle());
        holder.mBody.setText(record.getBody());
        holder.mPrice.setText(record.getPrice().toString());
        return row;
    }

    public void setData(ArrayList<ServiceClientEditModel> data) {
        this.clear();
        this.addAll(data);
    }

    class ViewHolder {
        public TextView mTitle;
        public TextView mBody;
        public TextView mPrice;

    }
}