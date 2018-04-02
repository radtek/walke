package com.mabang.android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

/**
 * Created by walke on 2018/2/3.
 * email:1032458982@qq.com
 */

public class HomeWorkTopView extends LinearLayout implements View.OnClickListener {
//    private  EditText etSearch;
    private  SearchView etSearch;
    private LinearLayout leftLayout;
    private View stutasBar;
    private ImageView ivLeft,ivSearch;
    private TextView tvCity;
    public HomeWorkTopView(Context context) {
        this(context,null);
    }

    public HomeWorkTopView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HomeWorkTopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        //去掉搜索内容的下划线
        int searchIdLLayout = etSearch.getContext().getResources().
                getIdentifier("android:id/search_plate", null, null);
        LinearLayout searchLLayout = (LinearLayout) etSearch.findViewById(searchIdLLayout);
        searchLLayout.setBackgroundResource(0);//关键在于这里

        int magId = getResources().getIdentifier("android:id/search_mag_icon",null, null);
        ImageView magImage = (ImageView) etSearch.findViewById(magId);
        magImage.setLayoutParams(new LayoutParams(0, 0));

//        int text_search_id = etSearch.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
//        TextView textSearch = (TextView) etSearch.findViewById(text_search_id);
//        textSearch.setTextColor(Color.BLACK);//设置字体颜色
//        textSearch.setBackgroundColor(Color.WHITE);//设置背景颜色
//        textSearch.setTextSize(13);//设置字体大小
//        textSearch.setHint("搜索笔记");//设置水印
//        android.widget.LinearLayout.LayoutParams textSearchLayoutParams =
//                (android.widget.LinearLayout.LayoutParams) textSearch.getLayoutParams();
//        textSearchLayoutParams.height = 75;//设置搜索框高度
//        textSearch.setLayoutParams(textSearchLayoutParams);
    }


    @Override
    public void onClick(View v) {

    }
    public interface HomeTopViewClickListener {
        void onCityClick();

        void onSearchClick();
    }

}
