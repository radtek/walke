package com.aipiao.bkpk.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aipiao.bkpk.R;
import com.aipiao.bkpk.bean.aicai.OpenCode;

/**
 * Created by caihui on 2018/3/21.
 * caid
 * 11 双色球
 * 1007  大乐透
 * 1 福彩3d
 * 1005 排列3
 * 1006 排列5
 * 13 七乐彩
 * 1008 七星彩
 * 1201 江苏7位数
 * 1207 浙江6+1
 * 1206  浙江20选5
 * 2001 好彩1
 * 2002 广东36选7
 * 2003 广东26选5
 * 1203 福建36选7
 * 1204 福建31选7
 * 1205 福建22选5
 * 1209 15选5
 * 1210 东方6+1
 */

public class OpenAdapter extends BaseAdapter {
    private OpenCode openCode;

    public OpenAdapter(OpenCode openCode) {
        this.openCode = openCode;
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public Object getItem(int position) {
        return openCode.getKaijiang().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_opem_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        OpenCode.KaijiangBean kaijiangBean = openCode.getKaijiang().get(position);
        String lot = kaijiangBean.getLot();
        String[] lotBe = lot.split("\\,");
        if (kaijiangBean.getCatId() == 11) {
            holder.historytitleTextView.setText("双色球");
            if (lotBe.length >= 5) {
                holder.historyn1.setText(lotBe[0]);
                holder.historyn2.setText(lotBe[1]);
                holder.historyn3.setText(lotBe[2]);
                holder.historyn4.setText(lotBe[3]);
                holder.historyn5.setText(lotBe[4]);
                String[] lotRe = lotBe[lotBe.length - 1].split("\\#");
                holder.historyn6.setText(lotRe[0]);
                holder.historyn7.setText(lotRe[1]);
                holder.historyn8.setVisibility(View.GONE);
            }
        } else if (kaijiangBean.getCatId() == 1007) {
            holder.historytitleTextView.setText("大乐透");
            holder.historyn1.setText(lotBe[0]);
            holder.historyn2.setText(lotBe[1]);
            holder.historyn3.setText(lotBe[2]);
            holder.historyn4.setText(lotBe[3]);
            String[] lotRe = lotBe[4].split("\\#");
            holder.historyn5.setText(lotRe[0]);
            holder.historyn6.setBackgroundResource(R.drawable.blue_radius_style);
            holder.historyn6.setText(lotRe[1]);
            holder.historyn7.setText(lotBe[5]);
            holder.historyn8.setVisibility(View.GONE);
        } else if (kaijiangBean.getCatId() == 1) {
            holder.historytitleTextView.setText("福彩3d");
            holder.historyn1.setText(lotBe[0]);
            holder.historyn2.setText(lotBe[1]);
            holder.historyn3.setText(lotBe[2]);
            holder.historyn4.setVisibility(View.GONE);
            holder.historyn5.setVisibility(View.GONE);
            holder.historyn6.setVisibility(View.GONE);
            holder.historyn7.setVisibility(View.GONE);
            holder.historyn8.setVisibility(View.GONE);
        } else if (kaijiangBean.getCatId() == 1005) {
            holder.historytitleTextView.setText("排列3");
            holder.historyn1.setText(lotBe[0]);
            holder.historyn2.setText(lotBe[1]);
            holder.historyn3.setText(lotBe[2]);

            holder.historyn4.setVisibility(View.GONE);
            holder.historyn5.setVisibility(View.GONE);
            holder.historyn6.setVisibility(View.GONE);
            holder.historyn7.setVisibility(View.GONE);
            holder.historyn8.setVisibility(View.GONE);


        } else if (kaijiangBean.getCatId() == 1006) {
            holder.historytitleTextView.setText("排列5");

            holder.historyn1.setText(lotBe[0]);
            holder.historyn2.setText(lotBe[1]);
            holder.historyn3.setText(lotBe[2]);
            holder.historyn4.setText(lotBe[3]);
            holder.historyn5.setText(lotBe[4]);
            holder.historyn6.setVisibility(View.GONE);
            holder.historyn7.setVisibility(View.GONE);
            holder.historyn8.setVisibility(View.GONE);

        } else if (kaijiangBean.getCatId() == 13) {
            holder.historytitleTextView.setText("七乐彩");
            holder.historyn1.setText(lotBe[0]);
            holder.historyn2.setText(lotBe[1]);
            holder.historyn3.setText(lotBe[2]);
            holder.historyn4.setText(lotBe[3]);
            holder.historyn5.setText(lotBe[4]);
            holder.historyn6.setText(lotBe[5]);
            String[] lotRe = lotBe[6].split("\\#");
            holder.historyn7.setText(lotRe[0]);
            holder.historyn8.setText(lotRe[1]);
        } else if (kaijiangBean.getCatId() == 1008) {
            holder.historytitleTextView.setText("七星彩");
            holder.historyn1.setText(lotBe[0]);
            holder.historyn2.setText(lotBe[1]);
            holder.historyn3.setText(lotBe[2]);
            holder.historyn4.setText(lotBe[3]);
            holder.historyn5.setText(lotBe[4]);
            holder.historyn6.setText(lotBe[5]);
            holder.historyn7.setText(lotBe[6]);
            holder.historyn8.setVisibility(View.GONE);
        }

        holder.historystageTextView.setText(kaijiangBean.getQihao() + "");
        if (TextUtils.isEmpty(kaijiangBean.getJiangci())) {
            holder.historyopentimeTextView.setText(kaijiangBean.getKj_date() + "");
        } else {
            holder.historyopentimeTextView.setText(kaijiangBean.getKj_date() + "      奖金 " + kaijiangBean.getJiangci() + "");

        }
        return convertView;
    }

    class ViewHolder {

        private TextView historytitleTextView;
        private TextView historystageTextView;
        private TextView historyopentimeTextView;
        private LinearLayout ballLinearLayout;
        private TextView historyn1;
        private TextView historyn2;
        private TextView historyn3;
        private TextView historyn4;
        private TextView historyn5;
        private TextView historyn6;
        private TextView historyn7;
        private TextView historyn8;
        private Button historyawardButton;
        private Button numberpredictionButton;
        private Button historychartButton;

        ViewHolder(View v) {
            historytitleTextView = (TextView) v.findViewById(R.id.historytitleTextView);
            historystageTextView = (TextView) v.findViewById(R.id.historystageTextView);
            historyopentimeTextView = (TextView) v.findViewById(R.id.historyopentimeTextView);
            ballLinearLayout = (LinearLayout) v.findViewById(R.id.ballLinearLayout);
            historyn1 = (TextView) v.findViewById(R.id.historyn1);
            historyn2 = (TextView) v.findViewById(R.id.historyn2);
            historyn3 = (TextView) v.findViewById(R.id.historyn3);
            historyn4 = (TextView) v.findViewById(R.id.historyn4);
            historyn5 = (TextView) v.findViewById(R.id.historyn5);
            historyn6 = (TextView) v.findViewById(R.id.historyn6);
            historyn7 = (TextView) v.findViewById(R.id.historyn7);
            historyn8 = (TextView) v.findViewById(R.id.historyn8);

        }

    }
}
