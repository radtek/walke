package com.aipiao.bkpkold.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.aipiao.bkpkold.R;
import com.aipiao.bkpkold.bean.aicai.ExpertMan;

import java.util.List;

/**
 * Created by Administrator on 2018/3/20.
 */

public class ExperManAdapter extends BaseAdapter {

    private List<ExpertMan.DatasBean> datas;
    public ExperManAdapter(List<ExpertMan.DatasBean> datas) {
        this.datas=datas;
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
            convertView = View.inflate(parent.getContext(), R.layout.item_exper_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ExpertMan.DatasBean datasBean = datas.get(position);
        if (datasBean!=null &&datasBean.getExpert()!=null){
            Glide.with(parent.getContext())
                    .load(datasBean.getExpert().getPhotoUrl())
                    .into(holder.expertIcon);
            holder.expertName.setText(datasBean.getExpert().getName()+"");
            holder.expertNumber.setText(datasBean.getEndQihao()+"期");
            if (datasBean.getStatus()==1){
                holder.expertStatus.setText("进行中");
            }else {
                holder.expertStatus.setTextColor(parent.getResources().getColor(R.color.red));
                holder.expertStatus.setText("完成");
            }
        }
        return convertView;
    }


    class ViewHolder {

        private ImageView expertIcon;
        private TextView expertName;
        private TextView expertCount;
        private TextView expertNumber;
        private TextView expertStatus;


        ViewHolder(View v) {
            expertIcon = (ImageView) v.findViewById(R.id.expertIcon);
            expertName = (TextView) v.findViewById(R.id.expertName);
            expertCount = (TextView) v.findViewById(R.id.expertCount);
            expertNumber = (TextView) v.findViewById(R.id.expertNumber);
            expertStatus = (TextView) v.findViewById(R.id.expertStatus);


        }

    }
}
