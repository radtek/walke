package walke.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by walke.Z on 2017/9/4.
 */

public class SimpleRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<T> mDatas;

    public SimpleRecyclerViewAdapter(List<T> datas) {
        mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, null);
        return new SimpleViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SimpleViewHolder simpleViewHolder = (SimpleViewHolder) holder;
        simpleViewHolder.text.setText(mDatas.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder{

        private TextView text;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            text = ((TextView) itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null!=mItemClickListener)
                        mItemClickListener.onItemClick(getLayoutPosition(),mDatas.get(getLayoutPosition()));
                }
            });
        }
    }

    public interface OnItemClickListener<T>{
        void onItemClick(int position, T t);
    }

    private OnItemClickListener mItemClickListener;

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
}
