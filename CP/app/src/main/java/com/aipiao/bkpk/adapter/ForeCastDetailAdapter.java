package com.aipiao.bkpk.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aipiao.bkpk.R;
import com.aipiao.bkpk.bean.aicai.ForeCastManDetail;

import java.util.List;

/**
 * Created by caihui on 2018/3/20.
 */

public class ForeCastDetailAdapter extends BaseAdapter {

    private List<ForeCastManDetail.LastRecoderBean> lastRecoder;

    public ForeCastDetailAdapter(List<ForeCastManDetail.LastRecoderBean> lastRecoder) {
        this.lastRecoder = lastRecoder;
    }

    @Override
    public int getCount() {
        return lastRecoder.size();
    }

    @Override
    public Object getItem(int position) {
        return lastRecoder.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_forecast_detail, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ForeCastManDetail.LastRecoderBean lastRecoderBean = lastRecoder.get(position);

        holder.openCode.setText(lastRecoderBean.getQihao()+"\n"+lastRecoderBean.getKaijiangNum());
        holder.yuceCode.setText(lastRecoderBean.getLottery()+"");
        holder.zhanjiCode.setText("中"+lastRecoderBean.getHitCount());
        return convertView;
    }


    class ViewHolder {
        private TextView openCode;
        private TextView yuceCode;
        private TextView zhanjiCode;

        ViewHolder(View v) {

            openCode = (TextView) v.findViewById(R.id.openCode);
            yuceCode = (TextView) v.findViewById(R.id.yuceCode);
            zhanjiCode = (TextView) v.findViewById(R.id.zhanjiCode);
        }

    }
}
