package com.aipiao.bkpkold.adapter.fb;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aipiao.bkpkold.R;
import com.aipiao.bkpkold.bean.GameFootball;

import java.util.List;

/**
 * Created by caihui on 2018/4/8.
 */

public class GameFootballAdapter extends BaseAdapter {
    private List<GameFootball.DatasBean> gameFootballs;

    public GameFootballAdapter(List<GameFootball.DatasBean> gameFootballs) {
        this.gameFootballs = gameFootballs;
    }

    @Override
    public int getCount() {
        return gameFootballs.size();
    }

    @Override
    public Object getItem(int position) {
        return gameFootballs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_game_football, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        GameFootball.DatasBean datasBean = gameFootballs.get(position);
        holder.occupationTextView.setText(datasBean.getMname() + "");
        if (datasBean.getColor().contains("#")){
            holder.occupationTextView.setBackgroundColor(Color.parseColor(datasBean.getColor()));
        }
        holder.homeTextView.setText(datasBean.getHn() + "");
        holder.visiTextView.setText(datasBean.getGn() + "");
        String[] times = datasBean.getMt().split(" ");
        if (times.length >= 1) {
            holder.timeTextView.setText(times[1]);
        }
        //首
        String[] mSpfs = datasBean.getSpf().split("\\,");
        if (mSpfs.length >= 2) {
            holder.thead2.setText("胜"+mSpfs[0]);
            holder.thead3.setText("平"+mSpfs[1]);
            holder.thead4.setText("负"+mSpfs[2]);
        }
        //尾
        String[] mRqspf = datasBean.getRqspf().split("\\,");
        if (mRqspf.length >= 2) {
            holder.tend2.setText("让胜"+mRqspf[0]);
            holder.tend3.setText("让平"+mRqspf[1]);
            holder.tend4.setText("让负"+mRqspf[2]);
        }
        return convertView;
    }

    public void notifyDataSetChanged(List<GameFootball.DatasBean> datas) {
        this.gameFootballs = datas;
        notifyDataSetChanged();
    }


    class ViewHolder {
        private TextView occupationTextView;
        private TextView timeTextView;
        private TextView homeTextView;
        private TextView visiTextView;
        private TextView thead1;
        private TextView thead2;
        private TextView thead3;
        private TextView thead4;
        private TextView thead5;
        private TextView tend1;
        private TextView tend2;
        private TextView tend3;
        private TextView tend4;
        private TextView tend5;
        private TextView lookAllDataTextView;


        ViewHolder(View v) {
            occupationTextView = (TextView) v.findViewById(R.id.occupationTextView);
            timeTextView = (TextView) v.findViewById(R.id.timeTextView);
            homeTextView = (TextView) v.findViewById(R.id.homeTextView);
            visiTextView = (TextView) v.findViewById(R.id.visiTextView);
            thead1 = (TextView) v.findViewById(R.id.thead1);
            thead2 = (TextView) v.findViewById(R.id.thead2);
            thead3 = (TextView) v.findViewById(R.id.thead3);
            thead4 = (TextView) v.findViewById(R.id.thead4);
            thead5 = (TextView) v.findViewById(R.id.thead5);
            tend1 = (TextView) v.findViewById(R.id.tend1);
            tend2 = (TextView) v.findViewById(R.id.tend2);
            tend3 = (TextView) v.findViewById(R.id.tend3);
            tend4 = (TextView) v.findViewById(R.id.tend4);
            tend5 = (TextView) v.findViewById(R.id.tend5);
            lookAllDataTextView = (TextView) v.findViewById(R.id.lookAllDataTextView);

        }

    }
}
