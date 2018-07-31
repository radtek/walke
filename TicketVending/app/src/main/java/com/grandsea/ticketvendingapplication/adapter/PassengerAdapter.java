package com.grandsea.ticketvendingapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.grandsea.ticketvendingapplication.R;
import com.grandsea.ticketvendingapplication.model.bean.OrderInfo;
import com.grandsea.ticketvendingapplication.model.bean.Passenger;

import java.util.List;

/**
 * Created by Administrator on 2017/9/19.
 */

public class PassengerAdapter extends RecyclerView.Adapter {

    private List<Passenger> passengers;
    private OrderInfo orderInfo;

    public PassengerAdapter(List<Passenger> passengers,OrderInfo orderInfo) {
        this.passengers = passengers;
        this.orderInfo = orderInfo;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_passenger,null);
        return new PassengerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,int position) {

        PassengerHolder passengerHolder = (PassengerHolder) holder;
        Passenger passenger = passengers.get(position);
        passengerHolder.passengerTxt.setText(passenger.getName()+"");
        passengerHolder.identityTxt.setText(passenger.getIdCard()+"");
        passengerHolder.priceTxt.setText(orderInfo.getPrice()+"");

        final int passengerPosition = position;
        passengerHolder.ivDeletePassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mItemClickListener !=null ){
                    mItemClickListener.onItemClick(passengerPosition);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return passengers.size();
    }

    class PassengerHolder extends RecyclerView.ViewHolder{

        private TextView passengerTxt;
        private TextView identityTxt;
        private TextView priceTxt;
        private ImageView ivDeletePassenger;

        public PassengerHolder(View itemView) {
            super(itemView);
            passengerTxt = (TextView ) itemView.findViewById(R.id.passenger_txt);
            identityTxt = (TextView ) itemView.findViewById(R.id.identity_txt);
            priceTxt = (TextView ) itemView.findViewById(R.id.is_priceChild);
            ivDeletePassenger = (ImageView) itemView.findViewById(R.id.iv_delete_passenger);


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
