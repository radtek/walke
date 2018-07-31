package com.grandsea.ticketvendingapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.grandsea.ticketvendingapplication.R;
import com.grandsea.ticketvendingapplication.model.bean.District;
import com.grandsea.ticketvendingapplication.model.bean.Station;
import com.grandsea.ticketvendingapplication.util.ViewUtil;

import java.util.List;

/**
 * Created by Grandsea09 on 2017/10/8.
 */

public class CityStationAdapter extends BaseAdapter {


    private List<District> list;

    public CityStationAdapter(List<District> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int districtPosition, View convertView, ViewGroup parent) {
        StationHolder holder=null;
        final int index=districtPosition;
        if (convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city_station,parent,false);
            holder=new StationHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder=((StationHolder) convertView.getTag());
        }
        String name = list.get(districtPosition).getName();
        holder.tvTitle.setText(name);
        final List<Station> stations = list.get(districtPosition).getStations();
//        stations.addAll(stations);//当index=0时，不仅调用一次getView方法，即getView这时会走多次(测试为3次)
       /* if (districtPosition==1) {
            stations.addAll(stations);
            stations.addAll(stations);
            stations.addAll(stations);
            stations.addAll(stations);
        }*/
        StationGridViewAdapter adapter = new StationGridViewAdapter(stations);
        holder.gridView.setAdapter(adapter);
        adapter.setOnItemClickListener(new StationGridViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v,int position) {
                if(null != mItemClickListener){
                    mItemClickListener.onItemClick(index,position);
                }
            }
        });

        int viewHeight = ViewUtil.getViewHeight(holder.relativeLayout);
        int layoutHeight = ViewUtil.getViewHeight(holder.linearLayout);
        if (viewHeight>layoutHeight){
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) holder.linearLayout.getLayoutParams();
            lp.height=viewHeight;
            holder.linearLayout.setLayoutParams(lp);
        }else {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) holder.relativeLayout.getLayoutParams();
            lp.height=layoutHeight;
            holder.relativeLayout.setLayoutParams(lp);
        }
        return convertView;
    }

    class StationHolder {

        private LinearLayout linearLayout;
        private RelativeLayout relativeLayout;
        private GridView gridView;
        private TextView tvTitle;

        public StationHolder(View view) {
            tvTitle = ((TextView) view.findViewById(R.id.ics_title));
            gridView = ((GridView) view.findViewById(R.id.ics_gridView));
            linearLayout = ((LinearLayout) view.findViewById(R.id.ics_LinearLayout));
            relativeLayout = ((RelativeLayout) view.findViewById(R.id.ics_RelativeLayout));
        }
    }


    public interface OnItemClickListener{
        void onItemClick(int districtPosition, int stationPosition);
    }

    private OnItemClickListener mItemClickListener;

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }


}
