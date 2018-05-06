package com.aipiao.bkpk.adapter.big;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aipiao.bkpk.R;
import com.aipiao.bkpk.bean.bmob.News;

import java.util.List;

/**
 * Created by caihui on 2018/3/21.
 */

public class BigGrandAdapter extends BaseAdapter {


    private final List<News> object;

    public BigGrandAdapter(List<News> object) {
        this.object = object;
    }

    @Override
    public int getCount() {
        return object.size();

    }

    @Override
    public Object getItem(int position) {
        return object.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_big_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        News bmobNews = object.get(position);
        holder.moneyTextView.setText(bmobNews.getMoney() + "");
        holder.numberTextView.setText(bmobNews.getQihao() + "");
        if (bmobNews.getContent().length() >= 18) {
            holder.contentTextView.setText(bmobNews.getContent().substring(0, 18)+"");
        } else {
            holder.contentTextView.setText(bmobNews.getContent()+"");
        }

        return convertView;
    }

    class ViewHolder {
        private TextView moneyTextView;
        private TextView numberTextView;
        private TextView contentTextView;

        ViewHolder(View v) {
            moneyTextView = (TextView) v.findViewById(R.id.moneyTextView);
            numberTextView = (TextView) v.findViewById(R.id.numberTextView);
            contentTextView = (TextView) v.findViewById(R.id.contentTextView);

        }

    }
}
