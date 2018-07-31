package com.grandsea.ticketvendingapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.grandsea.ticketvendingapplication.R;
import com.grandsea.ticketvendingapplication.model.bean.Station;

import java.util.List;

/**
 * Created by Administrator on 2017/9/18.
 */

public class StationGridViewAdapter extends BaseAdapter {
    public static final String TAG="StationGridViewAdapter" ;
    private List<Station> stations;

    public StationGridViewAdapter(List<Station> stations) {
        this.stations = stations;
    }

    @Override
    public int getCount() {
        return stations.size();
    }

    @Override
    public Object getItem(int position) {
        return stations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        StationHolder holdder;
        if (convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dest_station,parent,false);
            holdder=new StationHolder(convertView,position);
            convertView.setTag(holdder);
        }else {
            holdder= (StationHolder) convertView.getTag();
        }
        holdder.stationNameBt.setText(stations.get(position).getName());
        holdder.stationNameBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != mItemClickListener){
//                    Toast.makeText(v.getContext(),"007hello"+stations.get(position).getId()+"=="+stations.get(position).getName(),Toast.LENGTH_LONG).show();
                    mItemClickListener.onItemClick(v,position);
                }
               /* Toast.makeText(parent.getContext(),stations.get(position).getName()+"==>",Toast.LENGTH_LONG).show();
                //数据传输给下一个页面

                Station station = stations.get(position);

                Intent intent = new Intent(parent.getContext(),ShiftActivity.class);
                Log.i(TAG,"**************START*****点击了站点：***************"+station.getId()+" "+station.getName());
                PostShiftParams shiftParams = new PostShiftParams();
                shiftParams.setDepartCityId(departCityId);
                shiftParams.setDestCityId(destCityId);
                shiftParams.setDepartDate(DateUtil.formatYYYYMMDD(new Date())); //TODO 默认今天，待修改
                shiftParams.setGetOnId(getOnId);
                shiftParams.setGetOnStation("珠海机场");                           //TODO 待修改位置
                shiftParams.setTakeOffId(station.getId());
                shiftParams.setTakeOffStation(station.getName());
                intent.putExtra(IntentKey.SHIFT_OBJ,shiftParams);
                parent.getContext().startActivity(intent);*/
            }
        });
        return convertView;
    }


    class StationHolder {

        private Button stationNameBt;

        public StationHolder(View view,final int position) {
            stationNameBt = (Button)view.findViewById(R.id.item_btStationName);

        }
    }

    public interface OnItemClickListener{
        void onItemClick(View v ,int position);
    }

    private OnItemClickListener mItemClickListener;

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
}
