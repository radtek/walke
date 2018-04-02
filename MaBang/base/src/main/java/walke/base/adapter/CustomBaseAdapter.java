package walke.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Walke.Z
 * on 2017/5/17. 03.
 * email：1126648815@qq.com
 */
public abstract class CustomBaseAdapter<T> extends BaseAdapter {

    private Context mContext;
    private List<T> mDatas;
    protected LayoutInflater mInflater;

    public CustomBaseAdapter(Context context, List<T> datas) {
        mContext = context;
        mDatas = datas;
        mInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }

    protected int currentPosition;

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void itemPosition(int position){
        currentPosition = position;
        notifyDataSetChanged();
    }
}
