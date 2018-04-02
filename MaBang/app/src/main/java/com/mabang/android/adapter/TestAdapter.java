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
 * Created by walke on 2018/2/4.
 * email:1032458982@qq.com
 */

public class TestAdapter extends BaseAdapter {
    private List<TestBean> datas;

    public TestAdapter(List<TestBean> datas) {
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_ad_preselect_item, parent, false);
            holder=new AdSelectViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder=((AdSelectViewHolder) convertView.getTag());
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

    class AdSelectViewHolder{

        private final ImageView ivSelect,ivArrow;
        private final TextView tvText;

        public AdSelectViewHolder(View view) {
            ivSelect = ((ImageView) view.findViewById(R.id.lapi_ivSelect));
            ivArrow = ((ImageView) view.findViewById(R.id.lapi_ivArrow));
            tvText = ((TextView) view.findViewById(R.id.lapi_tvText));
        }
    }

}
