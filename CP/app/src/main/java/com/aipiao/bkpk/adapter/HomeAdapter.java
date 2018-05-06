package com.aipiao.bkpk.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aipiao.bkpk.R;


/**
 * Created by Administrator on 2018/3/11.
 */

public class HomeAdapter extends BaseAdapter {

    //    private  int [] icons={R.mipmap.lot_logo_ssq,
//            R.mipmap.lot_logo_11choose5,
//            R.mipmap.lot_logo_dlt,
//    R.mipmap.lot_logo_3d,R.mipmap.lot_logo_qlc,R.mipmap.lot_logo_qxc,R.mipmap.lot_logo_sfc9,R.mipmap.lot_logo_k3};
//    private  String [] titles={"双色球","11选5","大乐透","福彩3D","七乐彩","七星彩","任选9","快三"};
//
    private String[] descs = {"奖池8.49亿", "大乐透 大快乐", "2元中500万", "截止9:00", "30选7，百万大奖", "有空玩一玩 轻松赢大奖", "赢大奖","幸运"};
    private int[] icons = {R.mipmap.lot_logo_ssq, R.mipmap.lot_logo_dlt, R.mipmap.lot_logo_3d,
            R.mipmap.lot_logo_pl3,
            R.mipmap.lot_logo_pl5, R.mipmap.lot_logo_qxc, R.mipmap.lot_logo_qlc,R.mipmap.lot_logo_11choose5};

    private String[] titles = {"双色球", "大乐透", "福彩3D", "排列3", "排列5", "七乐彩", "七星彩","红11选5"};

    //    @Override
    public int getCount() {
        return icons.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_home_adapter, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.homeIconImageView.setBackgroundResource(icons[position]);
        holder.homeTitleTextView.setText(titles[position]);
        holder.homeDescTextView.setText(descs[position]);

        return convertView;
    }


    class ViewHolder {

        private ImageView homeIconImageView;
        private TextView homeTitleTextView;
        private TextView homeDescTextView;
        private ImageView homeHotImageView;


        ViewHolder(View v) {
            homeIconImageView = (ImageView) v.findViewById(R.id.homeIconImageView);
            homeTitleTextView = (TextView) v.findViewById(R.id.homeTitleTextView);
            homeDescTextView = (TextView) v.findViewById(R.id.homeDescTextView);
        }
    }
}
