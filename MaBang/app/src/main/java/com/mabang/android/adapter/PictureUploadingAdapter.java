package com.mabang.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mabang.android.R;
import com.mabang.android.entity.vo.BillboardImageInfo;

import java.util.List;

import walke.base.tool.ToastUtil;

/**
 * Created by walke on 2018/2/4.
 * email:1032458982@qq.com
 */

public class PictureUploadingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<BillboardImageInfo> mBillboardImageInfos;
    private BillboardImageInfo infoAdd = new BillboardImageInfo();//占位
    private Context mContext;


    public PictureUploadingAdapter(Context context, List<BillboardImageInfo> mImages) {
        mContext = context;
        mBillboardImageInfos =mImages;
        mBillboardImageInfos.add(0,infoAdd);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.gv_ad_picture_item, parent, false);
        RVPictureHolder pictureHolder = new RVPictureHolder(inflate);
        return pictureHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final RVPictureHolder pictureHolder = (RVPictureHolder) holder;
        if (position==0) {
            pictureHolder.ivCancel.setVisibility(View.GONE);
            pictureHolder.vCancelClick.setVisibility(View.GONE);
            pictureHolder.ivPicture.setImageResource(R.mipmap.picture_uploading_add);
        }else {
            pictureHolder.ivCancel.setVisibility(View.VISIBLE);
            pictureHolder.vCancelClick.setVisibility(View.VISIBLE);
            BillboardImageInfo billboardImageInfo = mBillboardImageInfos.get(position);
            String imageUrl = billboardImageInfo.getImageUrl();
            Log.i("walke", "imageUrl = "+imageUrl);

            pictureHolder.pbLoading.setVisibility(View.VISIBLE);
            Glide.with(mContext)//
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//让Glide缓存转换后的资源
                    .skipMemoryCache(false)//是否禁止内存缓存加载图片 true为禁止
                    .crossFade()//添加图片淡入加载的效果
//                    .dontAnimate() //没有渐变效果
//                    .thumbnail(0.1f)//设置缩略图支持：先加载缩略图 然后在加载全图
//                    .placeholder(R.mipmap.picture_load)//占位图
                    .error(R.mipmap.error)//加载出错图
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            pictureHolder.pbLoading.setVisibility(View.GONE);//占位进度条
                            return false;
                        }
                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            pictureHolder.pbLoading.setVisibility(View.GONE);//占位进度条
                            return false;
                        }
                    })
                    .into(pictureHolder.ivPicture);

//            ImgLoader.getInstance()
//                    .setSaveDir(Constants.APP_LOCATION)
//                    .loadImage(mContext, billboardImageInfo.getImageUrl(), billboardImageInfo.getKey(), pictureHolder.ivPicture, new ImgLoader.LoadingListener() {
//                        /**
//                         * @param current
//                         * @param total   当total=-1，从网络连接中获取内容长度失败，个别文件例如 http://pic1.win4000.com/wallpaper/8/51bb08e7a700a.jpg
//                         */
//                        @Override
//                        public void loading(int current, int total) {
//                            Log.i("walke", "loading: ---------------------- current = "+current+"      total = "+total);
//                        }
//
//                        @Override
//                        public void loadError(Exception exc) {
//                            Log.i("walke", "loadError: ---------------------------------exc : "+exc.getMessage());
//                            pictureHolder.pbLoading.setVisibility(View.GONE);
//                        }
//
//                        @Override
//                        public void loadFinish() {
//                            pictureHolder.pbLoading.setVisibility(View.GONE);
//                        }
//                    });

        }
    }

    @Override
    public int getItemCount() {
        return mBillboardImageInfos.size();
    }

    class RVPictureHolder extends RecyclerView.ViewHolder {

        private ProgressBar pbLoading;
        private ImageView ivCancel, ivPicture;
        private View vCancelClick;

        public RVPictureHolder(View view) {
            super(view);
            ivPicture = ((ImageView) view.findViewById(R.id.gapi_ivPicture));
            ivCancel = ((ImageView) view.findViewById(R.id.gapi_ivCancel));
            vCancelClick = view.findViewById(R.id.gapi_vCancelClick);
            pbLoading = (ProgressBar) view.findViewById(R.id.gapi_pbLoading);
            ivPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2018/2/5 获取手机相册图片或者启动相机拍照上传
                    int layoutPosition = getLayoutPosition();
                    if (layoutPosition==0) {
                        if (mBillboardImageInfos.size() >= 14) {
                            ToastUtil.showToast(mContext, "最多上传13张图片");
                            return;
                        }
                        if (mOnPictureSelectListener != null)
                            mOnPictureSelectListener.onAdd(layoutPosition);
                    }
                }
            });
            vCancelClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int layoutPosition = getLayoutPosition();
                    if (layoutPosition!=0) {
                        if (mOnPictureSelectListener != null)
                            mOnPictureSelectListener.onDelete(layoutPosition);
                    }
                }
            });
        }
    }

    public interface OnPictureSelectListener {
        void onAdd(int position);

        void onDelete(int position);
    }

    private OnPictureSelectListener mOnPictureSelectListener;

    public void setOnPictureSelectListener(OnPictureSelectListener onPictureSelectListener) {
        mOnPictureSelectListener = onPictureSelectListener;
    }

    public void addBillboardImageInfo(BillboardImageInfo info) {
        mBillboardImageInfos.add(info);
        notifyDataSetChanged();

    }

    public void delete(int position) {
        if (position >= 0 && position < mBillboardImageInfos.size()) {
            mBillboardImageInfos.remove(position);
            notifyDataSetChanged();
        }
    }


}
