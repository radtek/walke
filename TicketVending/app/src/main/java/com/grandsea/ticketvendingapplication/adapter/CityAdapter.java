package com.grandsea.ticketvendingapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.grandsea.ticketvendingapplication.R;
import com.grandsea.ticketvendingapplication.model.bean.City;

import java.util.List;

/**
 * Created by Grandsea09 on 2017/10/8.
 */

public class CityAdapter extends BaseAdapter {
    private List<City> cityList;
    private int currentPosition=0;

    public CityAdapter(List<City> cityList) {
        this.cityList = cityList;
    }

    @Override
    public int getCount() {
        return cityList.size();
    }

    @Override
    public Object getItem(int position) {
        return cityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CityHolder holder=null;
        if (convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city,parent,false);
            holder=new CityHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder=(CityHolder) convertView.getTag();
        }
        if (position==currentPosition) {
            convertView.setBackgroundResource(R.mipmap.city_selected);//R.drawable.bg_return_button
            holder.tvTitle.setTextColor(parent.getContext().getResources().getColor(R.color.text_white));
        } else {
            convertView.setBackgroundResource(R.mipmap.city_unselect);//R.drawable.bg_layout_corners
            holder.tvTitle.setTextColor(parent.getContext().getResources().getColor(R.color.text_006aae));
        }
        holder.tvTitle.setText(cityList.get(position).getName());
        return convertView;
    }

    public void itemPosition(int position){
        currentPosition = position;
        notifyDataSetChanged();
    }

    class CityHolder {


        private TextView tvTitle;

        public CityHolder(View view) {
            tvTitle = ((TextView) view.findViewById(R.id.ic_city));
        }
    }

}
