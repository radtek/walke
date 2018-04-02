package com.mabang.android.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mabang.android.R;
import com.mabang.android.entity.vo.BillboardInfo;

import java.util.List;

/**
 * Created by walke on 2018/2/4.
 * email:1032458982@qq.com
 */

public class HomeAdListAdapter extends BaseAdapter {
    private List<BillboardInfo> datas;
//    private List<BillboardInfo> typeList;

    public HomeAdListAdapter(List<BillboardInfo> datas) {
        this.datas = datas;
    }

//    public HomeAdListAdapter(List<BillboardInfo> datas,List<BillboardInfo> typeList) {
//        this.datas = datas;
//        this.typeList=typeList;
//    }

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
        AdSelectViewHolder holder=null;
        if (convertView==null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_ad_home, parent, false);
            holder=new AdSelectViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder=((AdSelectViewHolder) convertView.getTag());
        }

        BillboardInfo binfo = datas.get(position);

        holder.tvText.setText(binfo.getLongAddress()+"");
        if (TextUtils.isEmpty(binfo.getLongAddress()))
            holder.tvText.setText(" ");//服务器地址信息返回为空


        holder.ivItem.setImageResource(R.mipmap.home_ad_list_gray);
        holder.tvItem.setTextColor(parent.getContext().getResources().getColor(R.color.adListGray));
        holder.tvItem.setText(""+(position+1));

        return convertView;
    }


    private boolean isFitList(List<BillboardInfo> infos, BillboardInfo bInfo) {
        if (infos!=null) {
            for (BillboardInfo info : infos) {
                if (info.getId().equals(bInfo.getId()))
                    return true;
            }
        }
        return false;
    }

    class AdSelectViewHolder{
        private final TextView tvText;
        private final TextView tvItem;
        private final ImageView ivItem;

        public AdSelectViewHolder(View view) {

            tvText = ((TextView) view.findViewById(R.id.lah_tvText));
            tvItem = ((TextView) view.findViewById(R.id.lah_tvItem));
            ivItem = ((ImageView) view.findViewById(R.id.lah_ivSelect));
            tvItem.setVisibility(View.VISIBLE);
//            tvItem.setVisibility(View.GONE);
        }
    }

}
