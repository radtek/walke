package com.aipiao.bkpk.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aipiao.bkpk.R;
import com.aipiao.bkpk.bean.aicai.ExpertManDetail;

import java.util.List;

/**
 * Created by caihui on 2018/3/20.
 */

public class ExperManActivityDetailAdapter extends BaseAdapter {

    private List<ExpertManDetail.DataBean.FanganDetailsBean> fanganDetails;

    public ExperManActivityDetailAdapter(List<ExpertManDetail.DataBean.FanganDetailsBean> fanganDetails) {
        this.fanganDetails = fanganDetails;
    }

    @Override
    public int getCount() {
        return fanganDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return fanganDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_exper_dateil_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ExpertManDetail.DataBean.FanganDetailsBean fanganDetailsBean = fanganDetails.get(position);

        holder.upTextView.setText((position+1) +"");
        holder.numberTextView.setText(fanganDetailsBean.getQihao() + "");
        holder.doubleTextView.setText(fanganDetailsBean.getMulti() + "");
        holder.moneyTextView.setText(fanganDetailsBean.getTouMoney() + "");
        holder.awardTextView.setText(fanganDetailsBean.getZhuanMoney() + "");
        holder.totalTextView.setText(fanganDetailsBean.getLeiMoney() + "");
        if (fanganDetailsBean.getStatus()==0){
            holder.statusTextView.setTextColor(parent.getResources().getColor(R.color.green_color_56d072));
            holder.statusTextView.setText("未中奖");
        }else {
            holder.statusTextView.setTextColor(parent.getResources().getColor(R.color.red));
            holder.statusTextView.setText("中奖");
        }
        return convertView;
    }

    class ViewHolder {
        private TextView upTextView;
        private TextView numberTextView;
        private TextView doubleTextView;
        private TextView moneyTextView;
        private TextView totalTextView;
        private TextView awardTextView;
        private TextView statusTextView;


        ViewHolder(View v) {
            upTextView = (TextView) v.findViewById(R.id.upTextView);
            numberTextView = (TextView) v.findViewById(R.id.numberTextView);
            doubleTextView = (TextView) v.findViewById(R.id.doubleTextView);
            moneyTextView = (TextView) v.findViewById(R.id.moneyTextView);
            totalTextView = (TextView) v.findViewById(R.id.totalTextView);
            awardTextView = (TextView) v.findViewById(R.id.awardTextView);
            statusTextView = (TextView) v.findViewById(R.id.statusTextView);

        }

    }
}
