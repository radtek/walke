package com.aipiao.bkpkold.adapter.news;

 import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
 import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.aipiao.bkpkold.R;
import com.aipiao.bkpkold.bean.wubai.AnalysisAd;

 import java.text.SimpleDateFormat;
 import java.util.Calendar;
 import java.util.Date;
import java.util.List;

/**
 * Created by chennaikang on 2018/3/24.
 */

public class NewsAdapter extends BaseAdapter {


    List<AnalysisAd> news;
    public NewsAdapter(List<AnalysisAd> arrayList) {
        this.news=arrayList;
    }

    @Override
    public int getCount() {
        return  news.size();
    }

    @Override
    public Object getItem(int i) {
        return news.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_news_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        AnalysisAd analysisAd = news.get(position);
        Glide.with(parent.getContext())
                .load("http://5.9188.com/"+analysisAd.getLitpic())
                .into(holder.newsIcon);
        holder.newsTitle.setText(analysisAd.getNtitle()+"");


         holder.newsTime.setText(formatTimeInMillis(Long.parseLong(analysisAd.getNdate())));
        if (analysisAd.getDescription().length()>=10){
            holder.newsContent.setText(analysisAd.getDescription().substring(0,10));
        }else {
            holder.newsContent.setText(analysisAd.getDescription());

        }
        return convertView;
    }
    class  ViewHolder{
        private ImageView newsIcon;
        private TextView newsTitle;
        private TextView newsContent;
        private TextView newsTime;
        ViewHolder(View v) {
            newsIcon = (ImageView)v. findViewById(R.id.newsIcon);
            newsTitle = (TextView) v. findViewById(R.id.newsTitle);
            newsContent = (TextView) v. findViewById(R.id.newsContent);
            newsTime = (TextView) v. findViewById(R.id.newsTime);

        }

    }
    /**
     * 返回的字符串形式是形如：2013-10-20 20:58
     * */
    public static String formatTimeInMillis(long timeInMillis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeInMillis);
        Date date = cal.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "MM-dd HH:mm:ss");
        String fmt = dateFormat.format(date);

        return fmt;
    }


}
