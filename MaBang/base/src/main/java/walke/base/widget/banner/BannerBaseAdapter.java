package walke.base.widget.banner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by walke.Z on 2017/8/16.
 */

public abstract class BannerBaseAdapter<T> extends PagerAdapter {
    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    public BannerBaseAdapter(Context context, List<T> datas) {
        mContext = context;
        mDatas = datas;
        mInflater= LayoutInflater.from(context);
    }
    public int getDataSize() {
        return mDatas.size();
    }

    @Override
    public int getCount() {
        if (mDatas.size()>1){
            return Integer.MAX_VALUE;//Integer.MAX_VALUE  mDatas.size()
        }else {
            return mDatas.size();//当只有一个时不做无限轮播
        }
//        return Integer.MAX_VALUE;//Integer.MAX_VALUE  mDatas.size()
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //return super.instantiateItem(container, position);

        return itemView(container,position);

}
    protected abstract Object itemView(ViewGroup container, int position);

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    public void changeDatas(List<T> list) {
        if (list==null||list.size()<1)
            return;
        mDatas.clear();
        mDatas.addAll(list);
        notifyDataSetChanged();
        if (mChangeListener!=null)
            mChangeListener.dataChange(list);
    }

    public interface OnDataChangeListener<T>{
        void dataChange(List<T> list);
    }
    private OnDataChangeListener mChangeListener;

    public void setOnDataChangeListener(OnDataChangeListener changeListener) {
        mChangeListener = changeListener;
    }
}
