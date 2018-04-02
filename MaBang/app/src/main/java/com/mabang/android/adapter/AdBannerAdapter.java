package com.mabang.android.adapter;

import android.content.Context;
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
import com.mabang.android.R;
import com.mabang.android.entity.vo.BillboardImageInfo;

import java.util.List;

import walke.base.widget.banner.BannerBaseAdapter;

/**
 * Created by walke.Z on 2017/8/11.
 */

public  class AdBannerAdapter extends BannerBaseAdapter<BillboardImageInfo> {

    private List<BillboardImageInfo> mBannerList;


    public AdBannerAdapter(Context context, List<BillboardImageInfo> datas) {
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
        ViewGroup.LayoutParams layoutParams = inflate.getLayoutParams();

        TextView tvTitle = (TextView) inflate.findViewById(R.id.abi_title);
        ImageView ivImg = (ImageView) inflate.findViewById(R.id.abi_img);
        final ProgressBar pbLoading = (ProgressBar) inflate.findViewById(R.id.abi_pbLoading);
        pbLoading.setVisibility(View.VISIBLE);
        final int item=position%mBannerList.size();

        Glide.with(mContext)//
                .load(mBannerList.get(item).getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)//让Glide缓存转换后的资源
                .skipMemoryCache(false)//是否禁止内存缓存加载图片 true为禁止
                .crossFade()//添加图片淡入加载的效果
//                    .dontAnimate() //没有渐变效果
//                .placeholder(R.mipmap.picture_load)//占位图
                .error(R.mipmap.error)//加载出错图
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        pbLoading.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        pbLoading.setVisibility(View.GONE);
                        return false;
                    }
                }).centerCrop().into(ivImg);

//        ImgLoader.getInstance()
//                .setSaveDir(Constants.APP_LOCATION)
//                .loadImage(mContext, mBannerList.get(item).getImageUrl(), mBannerList.get(item).getKey(), ivImg, new ImgLoader.LoadingListener() {
//            /**
//             * @param current
//             * @param total   当total=-1，从网络连接中获取内容长度失败，个别文件例如 http://pic1.win4000.com/wallpaper/8/51bb08e7a700a.jpg
//             */
//            @Override
//            public void loading(int current, int total) {
//                Log.i("walke", "loading: ---------------------- current = "+current+"      total = "+total);
//            }
//
//            @Override
//            public void loadError(Exception exc) {
//                Log.i("walke", "loadError: ---------------------------------exc : "+exc.getMessage());
//                pbLoading.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void loadFinish() {
//                pbLoading.setVisibility(View.GONE);
//            }
//        });

        tvTitle.setVisibility(View.INVISIBLE);
        container.addView(inflate );
        ivImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener!=null)
                    mItemClickListener.onItemClick(item);
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
