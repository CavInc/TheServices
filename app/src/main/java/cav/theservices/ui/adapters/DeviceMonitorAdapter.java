package cav.theservices.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cav.theservices.R;
import cav.theservices.data.models.DeviceModel;

public class DeviceMonitorAdapter extends RecyclerView.Adapter<DeviceMonitorAdapter.DeviceMonitorHolder> {

    private List<DeviceModel> mData;
    private Context mContext;

    public DeviceMonitorAdapter(List<DeviceModel> data) {
        mData = data;
    }

    @Override
    public DeviceMonitorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View contentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_item,parent,false);
        return new DeviceMonitorHolder(contentView);
    }

    @Override
    public void onBindViewHolder(DeviceMonitorHolder holder, int position) {
        DeviceModel model = mData.get(position);
        holder.mDeviceName.setText(model.getDeviceText());
    }

    @Override
    public int getItemCount() {
        if (mData!=null) {
            return mData.size();
        }
        return 0;
    }

    public static class DeviceMonitorHolder extends RecyclerView.ViewHolder {

        private ImageView mDeviceIcon;
        private TextView mDeviceName;

        public DeviceMonitorHolder(View itemView) {
            super(itemView);

            mDeviceIcon = (ImageView) itemView.findViewById(R.id.device_icon);
            mDeviceName = (TextView) itemView.findViewById(R.id.device_name);

        }

    }

}