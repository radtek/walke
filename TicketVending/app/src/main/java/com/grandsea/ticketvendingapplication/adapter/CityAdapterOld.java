package com.grandsea.ticketvendingapplication.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.grandsea.ticketvendingapplication.R;
import com.grandsea.ticketvendingapplication.model.bean.City;

import java.util.List;

/**
 * Created by Administrator on 2017/9/18.
 */

public class CityAdapterOld extends RecyclerView.Adapter {

    private List<City> cityList;

    public CityAdapterOld(List<City> cityList) {
        this.cityList = cityList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dest_city, null);
        return new CityHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CityHolder cityHolder = (CityHolder)holder;
        cityHolder.city = cityList.get(position);
        cityHolder.cityNameBt.setText(cityList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }


    private class CityHolder extends RecyclerView.ViewHolder {
        private Button cityNameBt;
        private City city;

        public CityHolder(View itemView) {
            super(itemView);
            cityNameBt =(Button) itemView.findViewById(R.id.cityName_bt);

            cityNameBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null!=mItemClickListener)
                        mItemClickListener.onItemClick(getLayoutPosition());
                }
            });
            //可以这样做点击事件的。不过把点击事件提交给外面处理
           /* cityNameBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),city.getName()+"001",Toast.LENGTH_LONG).show();
                }
            });*/
        }


    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    private OnItemClickListener mItemClickListener;

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
}
