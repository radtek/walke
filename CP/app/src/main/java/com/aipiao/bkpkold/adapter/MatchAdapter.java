package com.aipiao.bkpkold.adapter;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aipiao.bkpkold.R;
import com.aipiao.bkpkold.bean.GameFootball;

import java.util.List;

/**
 * Created by caihui on 2018/4/10.
 */

public class   MatchAdapter extends BaseAdapter {
    private  List<GameFootball.DatasBean> datas;
    public MatchAdapter(List<GameFootball.DatasBean> datas) {
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
            convertView = View.inflate(parent.getContext(), R.layout.item_fb_tyoe_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        GameFootball.DatasBean datasBean = datas.get(position);
        holder.colorImageView.setBackgroundColor(Color.parseColor(datasBean.getColor()));
        holder.socreTypeTextView.setText(datasBean.getMname()+"");
        return convertView;
    }

    class ViewHolder {

        private ImageView colorImageView;
        private TextView socreTypeTextView;


        ViewHolder(View v) {
            colorImageView = (ImageView) v.findViewById(R.id.colorImageView);
            socreTypeTextView = (TextView)  v.findViewById(R.id.socreTypeTextView);
        }
    }
}
