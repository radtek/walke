package com.mabang.android.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.StringSignature;
import com.mabang.android.R;
import com.mabang.android.entity.vo.BillboardImageInfo;

import java.util.List;

import walke.base.widget.banner.BannerBaseAdapter;

/**
 * Created by walke.Z on 2017/8/11.
 */

public  class AdBigBannerAdapter extends BannerBaseAdapter<BillboardImageInfo> {

    private List<BillboardImageInfo> mBannerList;

    public AdBigBannerAdapter(Context context, List<BillboardImageInfo> datas) {
        super(context,datas);
        mContext = context;
        mBannerList = datas;
        mInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() { //重写父类方法，取消无限个数
        return mBannerList.size();
    }

    public int getDataSize() {
        return mBannerList.size();
    }

    @Override
    protected Object itemView(ViewGroup container, final int position) {
        //return super.instantiateItem(container, position);
        View inflate = mInflater.inflate(R.layout.ads_big_banner_item, null);//不能用container
        TextView tvTitle = (TextView) inflate.findViewById(R.id.abbi_title);
        ImageView ivImg = (ImageView) inflate.findViewById(R.id.abbi_img);
        final ProgressBar pbLoading = (ProgressBar) inflate.findViewById(R.id.abbi_pbLoading);
        pbLoading.setVisibility(View.VISIBLE);
//        position=position%mBannerList.size();
        Glide.with(mContext)//
                .load(mBannerList.get(position).getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)//
                .skipMemoryCache(false)//是否禁止内存缓存加载图片 true为禁止
                .crossFade()//添加图片淡入加载的效果
                .signature(new StringSignature(mBannerList.get(position).getKey()+""))
//                    .dontAnimate() //没有渐变效果
//                .placeholder(R.mipmap.picture_load)//占位图
                .error(R.mipmap.error)//加载出错图
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        Log.i("walke", "onResourceReady: ------------------------onException");
                        pbLoading.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        pbLoading.setVisibility(View.GONE);
                        Log.i("walke", "onResourceReady: ------------------------onResourceReady");
                        return false;
                    }
                }).into(ivImg);

//        ImgLoader.getInstance()
//                .setSaveDir(Constants.APP_LOCATION)
//                .loadImage(mContext, mBannerList.get(position).getImageUrl(), mBannerList.get(position).getKey(), ivImg, new ImgLoader.LoadingListener() {
//                    /**
//                     * @param current
//                     * @param total   当total=-1，从网络连接中获取内容长度失败，个别文件例如 http://pic1.win4000.com/wallpaper/8/51bb08e7a700a.jpg
//                     */
//                    @Override
//                    public void loading(int current, int total) {
//                        Log.i("walke", "loading: ---------------------- current = "+current+"      total = "+total);
//                    }
//
//                    @Override
//                    public void loadError(Exception exc) {
//                        Log.i("walke", "loadError: ---------------------------------exc : "+exc.getMessage());
//                        pbLoading.setVisibility(View.GONE);
//                    }
//
//                    @Override
//                    public void loadFinish() {
//                        pbLoading.setVisibility(View.GONE);
//                    }
//                });

        tvTitle.setVisibility(View.INVISIBLE);
        container.addView(inflate );
        ivImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener!=null)
                    mItemClickListener.onItemClick(position);
            }
        });
        return inflate;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    private OnItemClickListener mItemClickListener;

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
}
