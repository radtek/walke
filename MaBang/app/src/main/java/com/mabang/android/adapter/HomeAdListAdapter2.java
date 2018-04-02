package com.mabang.android.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mabang.android.R;
import com.mabang.android.entity.vo.BillboardInfo;

import java.util.List;

/**
 * Created by walke on 2018/2/4.
 * email:1032458982@qq.com
 */

public class HomeAdListAdapter2 extends BaseAdapter {
    private List<BillboardInfo> datas;

    public HomeAdListAdapter2(List<BillboardInfo> datas) {
        this.datas = datas;
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
        AdSelectViewHolder holder=null;
        if (convertView==null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_ad_home, parent, false);
            holder=new AdSelectViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder=((AdSelectViewHolder) convertView.getTag());
        }

        holder.tvText.setText(datas.get(position).getLongAddress()+"");
        if (TextUtils.isEmpty(datas.get(position).getLongAddress()))
            holder.tvText.setText(" ");//服务器地址信息返回为空
        holder.tvItem.setText(""+(position+1));

        return convertView;
    }

    class AdSelectViewHolder{
        private final TextView tvText;
        private final TextView tvItem;

        public AdSelectViewHolder(View view) {

            tvText = ((TextView) view.findViewById(R.id.lah_tvText));
            tvItem = ((TextView) view.findViewById(R.id.lah_tvItem));
            tvItem.setVisibility(View.VISIBLE);
//            tvItem.setVisibility(View.GONE);
        }
    }

}
