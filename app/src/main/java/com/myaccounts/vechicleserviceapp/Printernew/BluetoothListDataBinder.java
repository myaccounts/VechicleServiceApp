package com.myaccounts.vechicleserviceapp.Printernew;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myaccounts.vechicleserviceapp.R;

import java.util.HashMap;
import java.util.List;

public class BluetoothListDataBinder extends BaseAdapter {

    public static final String KEY_NAME = "name";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_CLASS = "calss";

    LayoutInflater inflater;
    ImageView thumb_image;
    List<HashMap<String, String>> DeviceCollection;
    ViewHolder holder;

    public BluetoothListDataBinder(Activity act, List<HashMap<String, String>> map) {
        this.DeviceCollection = map;
        inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return DeviceCollection.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null) {
            vi = inflater.inflate(R.layout.list_row, null);
            holder = new ViewHolder();
            holder.tvInfo = (TextView) vi.findViewById(R.id.tvInfo); // city
            holder.tvDescription = (TextView) vi.findViewById(R.id.tvDescription); // city weather overview
            holder.tvInfoImage = (ImageView) vi.findViewById(R.id.list_image); // thumb
            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }
        holder.tvInfo.setText(DeviceCollection.get(position).get(KEY_NAME));
        holder.tvDescription.setText(DeviceCollection.get(position).get(KEY_ADDRESS));
        if (DeviceCollection.get(position).containsKey(KEY_CLASS)) {
            int classcode = Integer.valueOf(DeviceCollection.get(position).get(
                    KEY_CLASS));
        } else {
        }
        return vi;
    }

    static class ViewHolder {

        TextView tvInfo;

        TextView tvDescription;
        ImageView tvInfoImage;
    }
}
