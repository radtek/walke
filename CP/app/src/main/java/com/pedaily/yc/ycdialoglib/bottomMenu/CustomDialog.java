package com.pedaily.yc.ycdialoglib.bottomMenu;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.aipiao.bkpkold.R;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2017/5/6
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CustomDialog extends Dialog {

    public static final int LINEAR = 0;
    public static final int GRID = 1;
    private LinearLayout background;
    private LinearLayout container;
    private TextView titleView;
    private TextView cancel;

    private DialogAdapter adapter;


    private int orientation;
    private int layout;


    CustomDialog(Context context) {
        super(context, R.style.CustomBottomDialog);
        init();
    }

    private void init() {
        setContentView(R.layout.bottom_dialog);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        background = (LinearLayout) findViewById(R.id.background);
        titleView = (TextView) findViewById(R.id.title);
        container = (LinearLayout) findViewById(R.id.container);
        cancel = (TextView) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    void addItems(List<CustomItem> items, OnItemClickListener onItemClickListener) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RecyclerView.LayoutManager manager;

        adapter = new DialogAdapter(getContext() , items, layout, orientation);
        adapter.setItemClick(onItemClickListener);
        if (layout == LINEAR)
            manager = new LinearLayoutManager(getContext(), orientation, false);
        else if (layout == GRID)
            manager = new GridLayoutManager(getContext(), 5, orientation, false);
        else manager = new LinearLayoutManager(getContext(), orientation, false);

        RecyclerView recyclerView = new RecyclerView(getContext());
        recyclerView.setLayoutParams(params);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        container.addView(recyclerView);
    }

    public void title(int title) {
        title(getContext().getString(title));
    }

    public void title(String title) {
        titleView.setText(title);
        titleView.setVisibility(View.VISIBLE);
    }

    public void setCancel(boolean isShow , String text){
        if(isShow){
            cancel.setVisibility(View.VISIBLE);
            cancel.setText(text);
        }else {
            cancel.setVisibility(View.GONE);
        }
    }

    public void layout(int layout) {
        this.layout = layout;
        if (adapter != null) adapter.setLayout(layout);
    }

    public void orientation(int orientation) {
        this.orientation = orientation;
        if (adapter != null) adapter.setOrientation(orientation);
    }

    public void background(int res) {
        background.setBackgroundResource(res);
    }

    void inflateMenu(int menu, OnItemClickListener onItemClickListener) {
        MenuInflater menuInflater = new SupportMenuInflater(getContext());
        MenuBuilder menuBuilder = new MenuBuilder(getContext());
        menuInflater.inflate(menu, menuBuilder);
        List<CustomItem> items = new ArrayList<>();
        for (int i = 0; i < menuBuilder.size(); i++) {
            MenuItem menuItem = menuBuilder.getItem(i);
            items.add(new CustomItem(menuItem.getItemId(), menuItem.getTitle().toString(), menuItem.getIcon()));
        }
        addItems(items, onItemClickListener);
    }

    void setItemClick(OnItemClickListener onItemClickListener) {
        adapter.setItemClick(onItemClickListener);
    }

}
