package com.grandsea.ticketvendingapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grandsea.ticketvendingapplication.R;
import com.grandsea.ticketvendingapplication.model.bean.Shift;

import java.util.List;

/**
 * Created by Administrator on 2017/9/19.
 */

public class ShiftAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Shift> shiftList;

    public ShiftAdapter(List<Shift> shiftList) {
        this.shiftList = shiftList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shift,parent,false);
        return new ShiftHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ShiftHolder shiftHolder = (ShiftHolder) holder;
        shiftHolder.sort.setText((position+1)+"");
        Shift shift = shiftList.get(position);
        shiftHolder.time.setText(shift.getDepartTime());
        shiftHolder.priceMan.setText(shift.getPrice()+"");
        shiftHolder.priceChild.setText(shift.getHalfPrice()+"");//半价
        shiftHolder.priceStudent.setText(shift.getStudentPrice()+"");
        if (shift.getTicketNum()>9)
            shiftHolder.surplus.setText("充足");
        else if(shift.getTicketNum()<1)
            shiftHolder.surplus.setText("已售完");
        else
            shiftHolder.surplus.setText(shift.getTicketNum()+"");


    }

    @Override
    public int getItemCount() {
        return shiftList.size();
    }

    class ShiftHolder extends RecyclerView.ViewHolder{

        private TextView sort,time,priceMan,priceChild,priceStudent,surplus;

        public ShiftHolder(View itemView) {
            super(itemView);
            sort = (TextView) itemView.findViewById(R.id.is_sort);
            time = (TextView) itemView.findViewById(R.id.is_time);
            priceMan = (TextView) itemView.findViewById(R.id.is_priceMan);
            priceChild = (TextView) itemView.findViewById(R.id.is_priceChild);
            priceStudent = (TextView) itemView.findViewById(R.id.is_priceStudent);
            surplus = (TextView) itemView.findViewById(R.id.is_surplus);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(null != mItemClickListener){
                        mItemClickListener.onItemClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    private OnItemClickListener mItemClickListener;

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

}
