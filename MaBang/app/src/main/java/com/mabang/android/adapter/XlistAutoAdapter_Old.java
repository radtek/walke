package com.mabang.android.adapter;

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
 * Created by Walke.Z on 2017/4/28.
 */
public class XlistAutoAdapter_Old extends BaseAdapter {

    private List<BillboardInfo> datas;
    private List<Integer> selectedItems;

    public XlistAutoAdapter_Old(List<BillboardInfo> datas, List<Integer> selectedItems) {
        this.datas = datas;
        this.selectedItems=selectedItems;
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
        XListViewHolder holder=null;
        if (convertView==null){
            convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_ad_preselect_item,parent,false);
            holder=new XListViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (XListViewHolder) convertView.getTag();
        }
        BillboardInfo billboardInfo = datas.get(position);
        holder.tvText.setText(billboardInfo.getLongAddress()+"");
        holder.ivSelect.setImageResource(R.mipmap.ad_preselect_no);//先默认不选中
        if (selectedItems!=null&&selectedItems.size()>0){
            for (Integer selectedItem : selectedItems) {
                if (position==selectedItem){
                    holder.ivSelect.setImageResource(R.mipmap.ad_preselect_yes);
                }
            }
        }

        return convertView;
    }
    private class XListViewHolder {

        private final ImageView ivSelect,ivArrow;
        private final TextView tvText;

        public XListViewHolder(View view) {
            ivSelect = ((ImageView) view.findViewById(R.id.lapi_ivSelect));
            ivArrow = ((ImageView) view.findViewById(R.id.lapi_ivArrow));
            tvText = ((TextView) view.findViewById(R.id.lapi_tvText));
        }
    }

    public void addAll(List<BillboardInfo> list){
        datas.addAll(list);
        notifyDataSetChanged();

    }
    public void changeAll(List<BillboardInfo> list){
        if (datas!=null){
            datas.clear();
        }
        datas.addAll(list);
        notifyDataSetChanged();

    }
}
