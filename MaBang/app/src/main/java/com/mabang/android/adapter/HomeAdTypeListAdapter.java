package com.mabang.android.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mabang.android.R;
import com.mabang.android.entity.vo.BillboardInfo;

import java.util.List;


/**
 * Created by walke on 2018/2/4.
 * email:1032458982@qq.com
 */

public class HomeAdTypeListAdapter extends HomeAdListAdapter {
    private List<BillboardInfo> datas;
//    private List<BillboardInfo> typeList;

    public HomeAdTypeListAdapter(List<BillboardInfo> datas) {
        super(datas);
        this.datas = datas;
    }

    public HomeAdTypeListAdapter(List<BillboardInfo> datas, List<BillboardInfo> typeList) {
        super(datas);
        this.datas = datas;
//        this.typeList=typeList;
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
        AdSelectViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_ad_home_type, parent, false);
            holder = new AdSelectViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = ((AdSelectViewHolder) convertView.getTag());
        }

        BillboardInfo binfo = datas.get(position);

        String longAddress = binfo.getLongAddress();
        if (TextUtils.isEmpty(longAddress))
            holder.tvText.setText(" ");//服务器地址信息返回为空
        else {
            int indexOf = longAddress.indexOf("区");
            longAddress = longAddress.substring(indexOf + 1, longAddress.length());
            holder.tvText.setText(longAddress + "");
        }
        if (binfo.getAdvanceType() == 0) {// 1为可预订的，2为我预订的，0就是其他数据
            holder.ivItem.setImageResource(R.mipmap.home_ad_list_gray);
            holder.tvItem.setTextColor(parent.getContext().getResources().getColor(R.color.adListGray));
        } else if (binfo.getAdvanceType() == 1) {
            holder.ivItem.setImageResource(R.mipmap.home_ad_list_blue);
            holder.tvItem.setTextColor(parent.getContext().getResources().getColor(R.color.adListBlue));
        } else if (binfo.getAdvanceType() == 2) {
            holder.ivItem.setImageResource(R.mipmap.home_ad_list_blue_mine);
            holder.tvItem.setTextColor(parent.getContext().getResources().getColor(R.color.adListBlue));
        }
        holder.tvItem.setText("" + (position + 1));

        return convertView;
    }


    private boolean isFitList(List<BillboardInfo> infos, BillboardInfo bInfo) {
        if (infos != null) {
            for (BillboardInfo info : infos) {
                if (info.getId() != null && info.getId().equals(bInfo.getId()))
                    return true;
            }
        }
        return false;
    }

    class AdSelectViewHolder {
        private final TextView tvText;
        private final TextView tvItem;
        private final ImageView ivItem;

        public AdSelectViewHolder(View view) {

            tvText = ((TextView) view.findViewById(R.id.laht_tvText));
            tvItem = ((TextView) view.findViewById(R.id.laht_tvItem));
            ivItem = ((ImageView) view.findViewById(R.id.laht_ivSelect));
            tvItem.setVisibility(View.VISIBLE);
//            tvItem.setVisibility(View.GONE);
        }
    }

}
