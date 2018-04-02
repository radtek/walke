package walke.base.adapter;

import android.widget.BaseAdapter;

/**
 * Created by Walke.Z
 * on 2017/5/17. 53.
 * email：1126648815@qq.com
 */
public abstract class MyBaseAdapter extends BaseAdapter {

    protected int currentPosition;

    public void itemPosition(int position){
        currentPosition = position;
        notifyDataSetChanged();
    }

}
