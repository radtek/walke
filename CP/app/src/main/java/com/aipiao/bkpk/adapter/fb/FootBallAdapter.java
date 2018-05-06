package com.aipiao.bkpk.adapter.fb;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.aipiao.bkpk.R;
import com.aipiao.bkpk.bean.wubai.PushFb;

import java.util.List;

/**
 * Created by caihui on 2018/3/21.
 */

public class FootBallAdapter extends BaseAdapter {
    private List<PushFb.DataBean.MatchesBean> matches;

    public FootBallAdapter(List<PushFb.DataBean.MatchesBean> matches) {
        this.matches = matches;
    }

    @Override
    public int getCount() {
        if (matches.size() >= 2) {
            return 2;
        } else {
            return matches.size();
        }

    }

    @Override
    public Object getItem(int position) {
        return matches.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_home_fb, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PushFb.DataBean.MatchesBean matchesBean = matches.get(position);

        //主队
        Glide.with(parent.getContext())
                .load(matchesBean.getHomelogo())
                .into(holder.fbHomeIconIcon);
        holder.weekTextView.setText(matchesBean.getOrder() + "  " + matchesBean.getSimpleleague());
        holder.homeTextView.setText(matchesBean.getHomesxname() + "");
        holder.timeTextView.setText(matchesBean.getMatchtime() + "");
        holder.homeScore.setText(matchesBean.getHomestanding() + "");
        holder.homeInto.setText(matchesBean.getHome_red_counts() + "");
        holder.homeIntos.setText(matchesBean.getHomehalfscore() + "");
        //客队
        Glide.with(parent.getContext())
                .load(matchesBean.getAwaylogo())
                .into(holder.fbKeduiIcon);
        holder.KeduiTextView.setText(matchesBean.getAwaysxname() + "");
        holder.KeduiScore.setText(matchesBean.getHomestanding());
        holder.KeduiInto.setText(matchesBean.getAway_red_counts() + "");
        holder.KeduiIntos.setText(matchesBean.getAwayhalfscore() + "");
        return convertView;
    }

    class ViewHolder {
        private TextView weekTextView;
        private TextView timeTextView;
        private ImageView fbHomeIconIcon;
        private TextView homeTextView;
        private TextView homeScore;
        private TextView homeInto;
        private TextView homeIntos;
        private ImageView fbKeduiIcon;
        private TextView KeduiTextView;
        private TextView KeduiScore;
        private TextView KeduiInto;
        private TextView KeduiIntos;


        ViewHolder(View v) {
            weekTextView = (TextView) v.findViewById(R.id.weekTextView);
            timeTextView = (TextView) v.findViewById(R.id.timeTextView);
            fbHomeIconIcon = (ImageView) v.findViewById(R.id.fbIcon);
            homeTextView = (TextView) v.findViewById(R.id.homeTextView);
            homeScore = (TextView) v.findViewById(R.id.homeScore);
            homeInto = (TextView) v.findViewById(R.id.homeInto);
            homeIntos = (TextView) v.findViewById(R.id.homeIntos);
            fbKeduiIcon = (ImageView) v.findViewById(R.id.fbKeduiIcon);
            KeduiTextView = (TextView) v.findViewById(R.id.KeduiTextView);
            KeduiScore = (TextView) v.findViewById(R.id.KeduiScore);
            KeduiInto = (TextView) v.findViewById(R.id.KeduiInto);
            KeduiIntos = (TextView) v.findViewById(R.id.KeduiIntos);

        }

    }
}
