package com.aipiao.bkpkold.adapter.mijj;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.aipiao.bkpkold.R;
import com.aipiao.bkpkold.bean.bmob.News;

import java.util.List;

/**
 * Created by caihui on 2018/3/21.
 */

public class CheatsAdapter extends BaseAdapter {


    private final List<News> object;

    public CheatsAdapter(List<News> object) {
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
            convertView = View.inflate(parent.getContext(), R.layout.item_cheast_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(parent.getContext())
                .load(object.get(position).getImage().getUrl())
                .into(holder.newIcon);
        holder.newContent.setText(object.get(position).getTitle() + "");
        return convertView;
    }

    class ViewHolder {
        private ImageView newIcon;
        private TextView newContent;


        ViewHolder(View v) {
            newIcon = (ImageView) v.findViewById(R.id.newIcon);
            newContent = (TextView) v.findViewById(R.id.newContent);
        }

    }
}
