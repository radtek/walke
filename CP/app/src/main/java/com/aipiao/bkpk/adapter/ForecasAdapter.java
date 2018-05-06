package com.aipiao.bkpk.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.aipiao.bkpk.R;
import com.aipiao.bkpk.bean.aicai.ForeCastMan;

import java.util.List;

/**
 * Created by caihui on 2018/3/20.
 */

public class ForecasAdapter extends BaseAdapter {

    private List<ForeCastMan.DatasBean> datas;

    public ForecasAdapter(List<ForeCastMan.DatasBean> datas) {
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
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_forecast_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ForeCastMan.DatasBean datasBean = datas.get(position);
        if (datasBean != null) {
            Glide.with(parent.getContext())
                    .load(datasBean.getPhotoUrl())
                    .into(holder.forceUserIcon);
            holder.forceUserName.setText(datasBean.getName() + "");
            holder.forceUserCount.setText(datasBean.getViewCount() + "查看");
            holder.forceUserNum.setText(datasBean.getMaxContinueHit() + "中" + datasBean.getHitLast());
            holder.forceUserMaxNum.setText(datasBean.getMaxContinueHit() + "");
            holder.forceUserUpNum.setText(datasBean.getOrderChange() + "");
            holder.upTextView.setText(datasBean.getOrder()+"");
        }
        return convertView;
    }


    class ViewHolder {
        private ImageView forceUserIcon;
        private TextView forceUserName;
        private TextView forceUserCount;
        private TextView forceUserNum;
        private TextView forceUserMaxNum;
        private TextView forceUserUpNum;
        private  TextView upTextView;

        ViewHolder(View v) {
            upTextView=(TextView) v.findViewById(R.id.upTextView);
            forceUserIcon = (ImageView) v.findViewById(R.id.forceUserIcon);
            forceUserName = (TextView) v.findViewById(R.id.forceUserName);
            forceUserCount = (TextView) v.findViewById(R.id.forceUserCount);
            forceUserNum = (TextView) v.findViewById(R.id.forceUserNum);
            forceUserMaxNum = (TextView) v.findViewById(R.id.forceUserMaxNum);
            forceUserUpNum = (TextView) v.findViewById(R.id.forceUserUpNum);

        }

    }
}
