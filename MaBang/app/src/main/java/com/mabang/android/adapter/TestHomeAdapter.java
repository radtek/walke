package com.mabang.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mabang.android.R;

import java.util.List;

/**
 * Created by walke on 2018/2/4.
 * email:1032458982@qq.com
 */

public class TestHomeAdapter extends BaseAdapter {
    private List<String> datas;

    public TestHomeAdapter(List<String> datas) {
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdSelectViewHolder holder=null;
        if (convertView==null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_ad_home, parent, false);
            holder=new AdSelectViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder=((AdSelectViewHolder) convertView.getTag());
        }

        holder.tvText.setText(datas.get(position)+"");

        return convertView;
    }

    class AdSelectViewHolder{
        private final TextView tvText;

        public AdSelectViewHolder(View view) {

            tvText = ((TextView) view.findViewById(R.id.lah_tvText));
        }
    }

}
