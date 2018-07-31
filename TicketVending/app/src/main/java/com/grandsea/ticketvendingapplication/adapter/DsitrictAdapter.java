package com.grandsea.ticketvendingapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grandsea.ticketvendingapplication.R;
import com.grandsea.ticketvendingapplication.model.bean.District;
import com.grandsea.ticketvendingapplication.model.bean.Station;
import com.grandsea.ticketvendingapplication.view.StationGridView;

import java.util.List;

/**
 * Created by Administrator on 2017/9/18.
 */

public class DsitrictAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = "DsitrictAdapter";

    private List<District> list;

    public DsitrictAdapter(List<District> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dest_district, null);
        return new DistrictHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        DistrictHolder districtHolder = (DistrictHolder)holder;
        districtHolder.districtNameTxt.setText(list.get(position).getName());
        final List<Station> stations = list.get(position).getStations();
        StationGridViewAdapter adapter = new StationGridViewAdapter(stations);
        districtHolder.stationGridView.setAdapter(adapter);

        final int districtPosition = position;
        adapter.setOnItemClickListener(new StationGridViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v,int position) {
//                Toast.makeText( v.getContext(),"8006-hello"+stations.get(position).getId()+"=="+stations.get(position).getName(),Toast.LENGTH_LONG).show();
                if(null != mItemClickListener){
                    mItemClickListener.onItemClick(districtPosition,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class DistrictHolder extends RecyclerView.ViewHolder {

        private TextView districtNameTxt;
        private StationGridView stationGridView;

        public DistrictHolder(View view) {
            super(view);
            districtNameTxt =(TextView) view.findViewById(R.id.item_tvDistrictName);
            stationGridView = (StationGridView) view.findViewById(R.id.item_stationGridView);

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
