package cav.theservices.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cav.theservices.R;
import cav.theservices.data.models.DeviceModel;

public class DeviceMonitorAdapter extends RecyclerView.Adapter<DeviceMonitorAdapter.DeviceMonitorHolder> {

    private List<DeviceModel> mData;
    private Context mContext;
    private DeviceMonitorHolder.CustomClickListener mCustomClickListener;

    public DeviceMonitorAdapter(List<DeviceModel> data,DeviceMonitorHolder.CustomClickListener customClickListener) {
        mData = data;
        mCustomClickListener = customClickListener;
    }

    public void setData(ArrayList<DeviceModel> data){
        mData.clear();
        mData.addAll(data);
    }

    @Override
    public DeviceMonitorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View contentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_item,parent,false);
        return new DeviceMonitorHolder(contentView,mCustomClickListener);
    }

    @Override
    public void onBindViewHolder(DeviceMonitorHolder holder, int position) {
        DeviceModel model = mData.get(position);
        holder.mDeviceName.setText(model.getDeviceText());
        if (model.getDemandCount() !=0 ){
            holder.mDeviceIcon.setImageResource(R.drawable.ic_tablet_android_green_24dp);
        }else {
            holder.mDeviceIcon.setImageResource(R.drawable.ic_tablet_android_black_24dp);
        }
        if (model.getDemandCount() !=0 ){
            holder.mDemandCount.setVisibility(View.VISIBLE);
            holder.mDemandCount.setText(String.valueOf(model.getDemandCount()));
        }else {
            holder.mDemandCount.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (mData!=null) {
            return mData.size();
        }
        return 0;
    }

    public static class DeviceMonitorHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mDeviceIcon;
        private TextView mDeviceName;
        private TextView mDemandCount;

        private CustomClickListener mListener;

        public DeviceMonitorHolder(View itemView,CustomClickListener customClickListener) {
            super(itemView);
            mListener = customClickListener;
            mDeviceIcon = (ImageView) itemView.findViewById(R.id.device_icon);
            mDeviceName = (TextView) itemView.findViewById(R.id.device_name);
            mDemandCount = (TextView) itemView.findViewById(R.id.device_count);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener!=null) {
                mListener.onUserItemClickListener(getAdapterPosition());
            }
        }

        public interface CustomClickListener {
            void onUserItemClickListener(int adapterPosition) ;
        }

    }

}