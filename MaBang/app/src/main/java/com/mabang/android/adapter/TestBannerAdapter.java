package com.mabang.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mabang.android.R;
import com.mabang.android.bean.TestBanner;
import com.squareup.picasso.Picasso;

import java.util.List;

import walke.base.widget.banner.BannerBaseAdapter;

/**
 * Created by walke.Z on 2017/8/11.
 */

public  class TestBannerAdapter extends BannerBaseAdapter<TestBanner> {

    private List<TestBanner> mBannerList;

    public TestBannerAdapter(Context context, List<TestBanner> datas) {
        super(context,datas);
        mContext = context;
        mBannerList = datas;
        mInflater=LayoutInflater.from(context);
    }

    public int getDataSize() {
        return mBannerList.size();
    }

    @Override
    protected Object itemView(ViewGroup container, int position) {
        //return super.instantiateItem(container, position);
        View inflate = mInflater.inflate(R.layout.ads_banner_item, null);//不能用container
        TextView tvTitle = (TextView) inflate.findViewById(R.id.abi_title);
        ImageView ivImg = (ImageView) inflate.findViewById(R.id.abi_img);
        position=position%mBannerList.size();
        Picasso.with(mContext).load(mBannerList.get(position).getUrl()).into(ivImg);
        tvTitle.setText(mBannerList.get(position).getTitle());
        container.addView(inflate );
        return inflate;
    }

}
