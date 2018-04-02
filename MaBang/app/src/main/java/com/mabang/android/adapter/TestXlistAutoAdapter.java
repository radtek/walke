package com.mabang.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mabang.android.R;
import com.mabang.android.bean.TestBean;

import java.util.List;

/**
 * Created by Walke.Z on 2017/4/28.
 */
public class TestXlistAutoAdapter extends BaseAdapter {

    private List<TestBean> datas;

    public TestXlistAutoAdapter(List<TestBean> datas) {
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
        XListViewHolder holder=null;
        if (convertView==null){
            convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_ad_preselect_item,parent,false);
            holder=new XListViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (XListViewHolder) convertView.getTag();
        }
        TestBean tb = datas.get(position);
        holder.tvText.setText(tb.getText()+"");
        if (tb.isSelected()){
            holder.ivSelect.setImageResource(R.mipmap.ad_preselect_yes);
        }else {
            holder.ivSelect.setImageResource(R.mipmap.ad_preselect_no);
        }

        return convertView;
    }
    private class XListViewHolder {

        private final ImageView ivSelect,ivArrow;
        private final TextView tvText;

        public XListViewHolder(View view) {
            ivSelect = ((ImageView) view.findViewById(R.id.lapi_ivSelect));
            ivArrow = ((ImageView) view.findViewById(R.id.lapi_ivArrow));
            tvText = ((TextView) view.findViewById(R.id.lapi_tvText));
        }
    }

    public void addAll(List<TestBean> list){
        datas.addAll(list);
        notifyDataSetChanged();

    }
    public void changeAll(List<TestBean> list){
        if (datas!=null){
            datas.clear();
        }
        datas.addAll(list);
        notifyDataSetChanged();

    }
}
