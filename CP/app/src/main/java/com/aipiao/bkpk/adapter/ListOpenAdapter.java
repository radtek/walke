package com.aipiao.bkpk.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aipiao.bkpk.R;
import com.aipiao.bkpk.bean.bmob.Lottery;

import java.util.List;

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

public class ListOpenAdapter extends BaseAdapter {
    List<Lottery> openCode;

    public ListOpenAdapter(List<Lottery> openCode) {
        this.openCode = openCode;
    }

    @Override
    public int getCount() {
        return openCode.size();
    }

    @Override
    public Object getItem(int position) {
        return openCode.get(position);
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
        Lottery lottery = openCode.get(position);
        String type = lottery.getType();
        holder.historytitleTextView.setTextSize(13);
        if (type.equals("11")) { //双色球
            holder.historytitleTextView.setText("双色球");
            String[] numbers = lottery.getNumbers().split(",");
            if (numbers.length >= 6) {
                holder.historyn1.setText(numbers[0]);
                holder.historyn2.setText(numbers[1]);
                holder.historyn3.setText(numbers[2]);
                holder.historyn4.setText(numbers[3]);
                holder.historyn5.setText(numbers[4]);
                holder.historyn6.setText(numbers[5]);
                holder.historyn6.setBackgroundResource(R.drawable.blue_radius_style);
                holder.historyn7.setBackgroundResource(R.drawable.blue_radius_style);
                holder.historyn7.setText(numbers[6]);
                holder.historyn8.setVisibility(View.GONE);
            }

        } else if (type.equals("1007")) { //大乐透
            holder.historytitleTextView.setText("大乐透");
            String[] numbers = lottery.getNumbers().split(",");
            holder.historyn1.setText(numbers[0]);
            holder.historyn2.setText(numbers[1]);
            holder.historyn3.setText(numbers[2]);
            holder.historyn4.setText(numbers[3]);
            holder.historyn5.setText(numbers[4]);
            holder.historyn6.setText(numbers[5]);
            holder.historyn6.setBackgroundResource(R.drawable.blue_radius_style);
            holder.historyn7.setBackgroundResource(R.drawable.blue_radius_style);
            holder.historyn7.setText(numbers[6]);
            holder.historyn8.setVisibility(View.GONE);
        } else if (type.equals("1")) { //福彩3d
            holder.historytitleTextView.setText("福彩3d");
            String[] numbers = lottery.getNumbers().split(",");
            if (numbers.length >= 2) {
                holder.historyn1.setText(numbers[0]);
                holder.historyn2.setText(numbers[1]);
                holder.historyn3.setText(numbers[2]);
                holder.historyn4.setVisibility(View.GONE);
                holder.historyn5.setVisibility(View.GONE);
                holder.historyn6.setVisibility(View.GONE);
                holder.historyn7.setVisibility(View.GONE);
                holder.historyn8.setVisibility(View.GONE);
            }
        } else if (type.equals("1005")) {
            //排列3
            holder.historytitleTextView.setText("排列3");
            String[] numbers = lottery.getNumbers().split(",");
            holder.historyn1.setText(numbers[0]);
            holder.historyn2.setText(numbers[1]);
            holder.historyn3.setText(numbers[2]);
            holder.historyn4.setVisibility(View.GONE);
            holder.historyn5.setVisibility(View.GONE);
            holder.historyn6.setVisibility(View.GONE);
            holder.historyn7.setVisibility(View.GONE);
            holder.historyn8.setVisibility(View.GONE);

        } else if (type.equals("1006")) {
            //排列5
            holder.historytitleTextView.setText("排列5");
            String[] numbers = lottery.getNumbers().split(",");
            holder.historyn1.setText(numbers[0]);
            holder.historyn2.setText(numbers[1]);
            holder.historyn3.setText(numbers[2]);
            holder.historyn4.setText(numbers[3]);
            holder.historyn5.setText(numbers[4]);
            holder.historyn6.setVisibility(View.GONE);
            holder.historyn7.setVisibility(View.GONE);
            holder.historyn8.setVisibility(View.GONE);
        } else if (type.equals("13")) {//七乐彩
            holder.historytitleTextView.setText("七乐彩");
            String[] numbers = lottery.getNumbers().split(",");
            if (numbers.length >= 7) {
                holder.historyn1.setText(numbers[0]);
                holder.historyn2.setText(numbers[1]);
                holder.historyn3.setText(numbers[2]);
                holder.historyn4.setText(numbers[3]);
                holder.historyn5.setText(numbers[4]);
                holder.historyn6.setText(numbers[5]);
                holder.historyn7.setText(numbers[6]);
                holder.historyn7.setBackgroundResource(R.drawable.red_radius_style);
                holder.historyn8.setText(numbers[7]);
            }

        } else if (type.equals("1008")) { //七星彩
            holder.historytitleTextView.setText("七星彩");
            String[] numbers = lottery.getNumbers().split(",");
            if (numbers.length >= 6) {
                holder.historyn1.setText(numbers[0]);
                holder.historyn2.setText(numbers[1]);
                holder.historyn3.setText(numbers[2]);
                holder.historyn4.setText(numbers[3]);
                holder.historyn5.setText(numbers[4]);
                holder.historyn6.setText(numbers[5]);
                holder.historyn6.setBackgroundResource(R.drawable.red_radius_style);
                holder.historyn7.setBackgroundResource(R.drawable.red_radius_style);
                holder.historyn7.setText(numbers[6]);
                holder.historyn8.setVisibility(View.GONE);
            }
        }

        holder.historystageTextView.setText(lottery.getNo() + "");
        holder.historyopentimeTextView.setText(lottery.getTime() + "");

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
