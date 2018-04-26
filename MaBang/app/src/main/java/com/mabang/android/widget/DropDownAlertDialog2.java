package com.mabang.android.widget;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils.TruncateAt;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.mabang.android.R;
import com.mabang.android.entity.vo.AreaInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 下拉弹出框
 *
 * @author xiong
 * @date 2013-8-22 下午03:42:35
 */
public class DropDownAlertDialog2 extends AlertDialog implements View.OnClickListener {
    // 当前设备屏幕分辨率密度比
    private float density;
    // 当前设备屏幕宽度
    private int widthPixels;
    // 当前设备屏幕高度
    private int heightPixels;
    // 背景视图
    private RelativeLayout bgLayout;
    // 空视图
    protected RelativeLayout blankLayout;
    // 主视图
    protected RelativeLayout mainLayout;
    // 取消按钮
    protected Button cancelButton;
    // 确定按钮
    protected Button enterButton;
    // 中间视图
    protected LinearLayout centerLayout;
    // 子项高度
    protected int itemHeight;
    // 中间视图高度
    protected int contentHeight;
    // 布局底部总高度
    protected int bgHeight;
    // 标题控件
    protected TableRow titleRow;
    // 加载层
    private RelativeLayout loadLayout;
    // 子项面板集合
    public ArrayList<DropDownAlertDialogPanel> panelList = new ArrayList<DropDownAlertDialogPanel>();
    // 省份
    public DropDownAlertDialogPanel provincePanel;
    // 城市
    public DropDownAlertDialogPanel cityPanel;
    // 地区
    public DropDownAlertDialogPanel zonePanel;
    // 街道
    public DropDownAlertDialogPanel streetPanel;

    @SuppressWarnings("deprecation")
    public DropDownAlertDialog2(Context context) {
        super(context, R.style.LoadAlertDialog);
        this.setCanceledOnTouchOutside(false);
        this.setTranslucentStatus(true);
        this.density = context.getResources().getDisplayMetrics().density;
        this.widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        this.heightPixels = context.getResources().getDisplayMetrics().heightPixels;

        this.bgLayout = new RelativeLayout(context);
        this.bgLayout.setBackgroundColor(0x33000000);

        this.mainLayout = new RelativeLayout(context);
        this.mainLayout.setId(R.id.alertDialogMainLayoutId);
        this.bgHeight = this.heightPixels;
        int centerHeight = (int) (this.bgHeight * .6);
        RelativeLayout.LayoutParams params = DropDownAlertDialog2.getR_P_MV(centerHeight);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        this.bgLayout.addView(this.mainLayout, params);

        this.blankLayout = new RelativeLayout(context);
        this.bgLayout.addView(this.blankLayout, DropDownAlertDialog2.getR_P_MM());

        this.itemHeight = this.transformIntValue(45);
        params = DropDownAlertDialog2.getR_P_MV(itemHeight);
        TableLayout table = new TableLayout(context);
        table.setColumnShrinkable(0, true);
        table.setColumnStretchable(1, true);
        table.setColumnShrinkable(2, true);
        table.setBackgroundResource(R.drawable.table_gray_title_bg);
        table.setPadding(0, 0, 0, 0);
        this.mainLayout.addView(table, params);

        this.titleRow = new TableRow(context);
        this.titleRow.setGravity(Gravity.CENTER);
        table.addView(this.titleRow);

        int margin = this.transformIntValue(4);
        TableRow.LayoutParams rParams = DropDownAlertDialog2.getTR_P_VV(this.transformIntValue(65), this.itemHeight - margin * 2);
        rParams.topMargin = rParams.bottomMargin = margin;
        rParams.rightMargin = rParams.leftMargin = margin;

        this.cancelButton = new Button(context);
        this.cancelButton.setTypeface(null, Typeface.NORMAL);
        this.cancelButton.setPadding(0, 0, 0, 0);
        this.cancelButton.setTextColor(0xFFFFFFFF);
        this.cancelButton.setTextSize(16);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.cancelButton.setStateListAnimator(null);
        }
        this.cancelButton.setText("取消");
        this.cancelButton.setTextColor(0xFF333333);
        this.cancelButton.setBackgroundColor(0x00000000);
        this.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DropDownAlertDialog2.this.onCheckNoEvent())
                    DropDownAlertDialog2.this.cancel();
                return;
            }
        });
        this.titleRow.addView(this.cancelButton, rParams);

        this.titleRow.addView(new View(context));

        this.enterButton = new Button(context);
        this.enterButton.setTypeface(null, Typeface.NORMAL);
        this.enterButton.setPadding(0, 0, 0, 0);
        this.enterButton.setTextSize(16);
        this.enterButton.setText("确定");
        this.enterButton.setBackgroundColor(0x00000000);
        this.enterButton.setTextColor(0xFFE90000);
        this.enterButton.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.enterButton.setStateListAnimator(null);
        }
        this.titleRow.addView(this.enterButton, rParams);

        this.centerLayout = new LinearLayout(context);
        this.centerLayout.setOrientation(LinearLayout.HORIZONTAL);
        this.centerLayout.setBackgroundColor(0xFFFFFFFF);
        this.contentHeight = centerHeight - this.itemHeight;
        RelativeLayout.LayoutParams p = DropDownAlertDialog2.getR_P_MV(this.contentHeight);
        p.topMargin = this.itemHeight;
        this.mainLayout.addView(this.centerLayout, p);

        RelativeLayout shadowLayout = new RelativeLayout(this.getContext());
        params = DropDownAlertDialog2.getR_P_MV(this.transformIntValue(5));
        params.topMargin = this.itemHeight;
        shadowLayout.setLayoutParams(params);
        shadowLayout.setBackgroundDrawable(new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{0x22000000, 0x00000000}));
        this.mainLayout.addView(shadowLayout);

        shadowLayout = new RelativeLayout(this.getContext());
        params = DropDownAlertDialog2.getR_P_MV(this.transformIntValue(5));
        params.addRule(RelativeLayout.ABOVE, this.mainLayout.getId());
        shadowLayout.setLayoutParams(params);
        shadowLayout.setBackgroundDrawable(new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, new int[]{0x22000000, 0x00000000}));
        this.bgLayout.addView(shadowLayout);

//        this.setOnShowListener(this);

        this.provincePanel = this.createPanel();
        this.cityPanel = this.createPanel();
        this.zonePanel = this.createPanel();
        this.streetPanel = this.createPanel();
        this.changeSize();
        return;
    }

    @Override
    public void onClick(View view) {
        if (view == this.enterButton) {
            if (this.onCheckNoEvent() && this.onAlertDialogEvent != null)
                this.onAlertDialogEvent.onEnter(DropDownAlertDialog2.this);
            return;
        }
    }

    /**
     * 隐藏确定按钮
     */
    public void hideEnter() {
        this.enterButton.setVisibility(View.INVISIBLE);
        return;
    }

    /**
     * 创建
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.bgLayout != null)
            this.setContentView(this.bgLayout, DropDownAlertDialog2.getR_P_VV(this.widthPixels, this.bgHeight));
        return;
    }

    /**
     * 点击确定事件
     *
     * @author xiong
     * @date 2013-11-20 下午01:56:25
     */
    public interface OnAlertDialogEvent {
        public abstract void onEnter(DropDownAlertDialog2 dialog);
    }

    private OnAlertDialogEvent onAlertDialogEvent;

    public void setOnAlertDialogEvent(OnAlertDialogEvent onAlertDialogEvent) {
        this.onAlertDialogEvent = onAlertDialogEvent;
        return;
    }

    /**
     * 将取消按钮变为标题
     *
     * @param title
     */
    public void changeCancelButtonToTitle(String title) {
        if (this.cancelButton.getParent() != null) {
            TextView textView = new TextView(this.getContext());
            textView.setText(title);
            textView.setTextColor(0xFF000000);
            textView.setTextSize(18);
            textView.setPadding(this.transformIntValue(5), 0, 0, 0);
            this.titleRow.removeView(this.cancelButton);
            this.titleRow.addView(textView, 0);
        }
        return;
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = this.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
        return;
    }

    @Override
    public void show() {
        super.show();
//        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
//        lp.width = this.widthPixels; //设置宽度
//        lp.height = this.heightPixels; //设置宽度
//        this.getWindow().setAttributes(lp);
        return;
    }

    /**
     * 清空所有
     */
    public void clear() {
//        this.centerLayout.removeAllViews();
//        this.panelList.clear();
        return;
    }

    /**
     * 显示加载
     */
    public void showLoad() {
        if (this.loadLayout == null) {
            this.loadLayout = new RelativeLayout(this.getContext());
            this.loadLayout.setBackgroundColor(0x44000000);
            this.mainLayout.addView(this.loadLayout, DropDownAlertDialog2.getR_P_MM());
            ProgressBar progressBar = new ProgressBar(this.getContext(), null, android.R.attr.progressBarStyle);
            progressBar.setIndeterminateDrawable(this.getContext().getResources().getDrawable(R.drawable.progress_drawable_white));
            int size = this.transformIntValue(30);
            RelativeLayout.LayoutParams params = DropDownAlertDialog2.getR_P_VV(size, size);
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            this.loadLayout.addView(progressBar, params);
        }
        this.loadLayout.setVisibility(View.VISIBLE);
        return;
    }

    /**
     * 隐藏加载
     */
    public void hideLoad() {
        if (this.loadLayout != null)
            this.loadLayout.setVisibility(View.GONE);
        return;
    }

    /**
     * 添加子项集合
     */
    private DropDownAlertDialogPanel createPanel() {
        DropDownAlertDialogPanel panel = new DropDownAlertDialogPanel(this.getContext());
        int mw = this.transformIntValue(15);
        if (this.panelList.size() > 0) {
            this.addLine();

            LinearLayout line = new LinearLayout(this.getContext());
            line.setLayoutParams(DropDownAlertDialog2.getL_P_VM(mw - 2));
            line.setBackgroundColor(0xFFFCFCFC);
            line.setGravity(Gravity.CENTER);

            ImageView image = new ImageView(this.getContext());
            image.setImageResource(R.mipmap.arrow_small);
            line.addView(image);
            this.centerLayout.addView(line);

            this.addLine();
        }
        panel.index = this.panelList.size();
        this.panelList.add(panel);
        this.centerLayout.addView(panel);
        return panel;
    }

    /**
     * 改变尺寸
     */
    public void changeSize() {
        int mw = this.transformIntValue(15);
        int size = this.panelList.size();
        if (this.streetPanel.getVisibility() == View.GONE)
            size -= 1;
        int width = (this.widthPixels - (mw * (size - 1))) / size;
        LinearLayout.LayoutParams params = DropDownAlertDialog2.getL_P_VM(width);
        for (DropDownAlertDialogPanel p : this.panelList)
            p.setLayoutParams(params);
        return;
    }

    /**
     * 添加竖线
     */
    private void addLine() {
        LinearLayout line = new LinearLayout(this.getContext());
        line.setLayoutParams(DropDownAlertDialog2.getL_P_VM(1));
        line.setBackgroundColor(0xFFE8E8E8);
        line.setGravity(Gravity.CENTER);
        this.centerLayout.addView(line);
        return;
    }

    /**
     * 红线
     *
     * @return
     */
    public LinearLayout createLine(int color) {
        LinearLayout line = new LinearLayout(this.getContext());
        line.setLayoutParams(DropDownAlertDialog2.getL_P_MV(1));
        line.setBackgroundColor(color);
        return line;
    }

    /**
     * 弹出框事件
     *
     * @author xiong
     * @date 2013-8-23 下午02:21:57
     */
    public interface OnDropDownAlertDialogEvent {
        public abstract void onItemChange(DropDownAlertDialog2 dialog, DropDownAlertDialogPanel panel, DropDownAlertDialogItem item);
    }

    public OnDropDownAlertDialogEvent onDropDownAlertDialogEvent;

    public void setOnItemChangeEvent(OnDropDownAlertDialogEvent onDropDownAlertDialogEvent) {
        this.onDropDownAlertDialogEvent = onDropDownAlertDialogEvent;
        return;
    }

    /**
     * 返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
            if (this.loadLayout != null && this.loadLayout.getVisibility() != View.GONE)
                return false;
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 清空所有选择
     */
    public void clearAllCheck() {
        for (DropDownAlertDialogPanel panel : this.panelList)
            panel.clearAllCheck();
        return;
    }

    /**
     * 分栏面板
     *
     * @author xiong
     * @date 2013-8-22 下午05:04:31
     */
    public class DropDownAlertDialogPanel extends ScrollView implements OnTouchListener {
        // 内容视图
        private LinearLayout contentLayout;

        private ArrayList<DropDownAlertDialogItem> itemList = new ArrayList<DropDownAlertDialogItem>();

        private DropDownAlertDialogItem activeItem;

        private int index;

        public DropDownAlertDialogItem getActiveItem() {
            return this.activeItem;
        }

        public AreaInfo getDropDownItemInfo() {
            if (this.activeItem == null)
                return null;
            return this.activeItem.getDropDownItemInfo();
        }

        public ArrayList<DropDownAlertDialogItem> getItemList() {
            return this.itemList;
        }

        public DropDownAlertDialogPanel(Context context) {
            super(context);
            this.setHorizontalFadingEdgeEnabled(false);
            this.setVerticalFadingEdgeEnabled(false);
            this.setScrollbarFadingEnabled(false);
            this.setVerticalScrollBarEnabled(false);
            this.setHorizontalScrollBarEnabled(false);
            this.setFadingEdgeLength(0);

            this.contentLayout = new LinearLayout(context);
            this.contentLayout.setOrientation(LinearLayout.VERTICAL);
            super.addView(this.contentLayout);
            return;
        }

        public void append(List<AreaInfo> list) {
            this.clear();
            if (list == null || list.size() == 0)
                return;
            int i = 0;
//            boolean tag = false;
            for (AreaInfo dropDownItemInfo : list) {
                DropDownAlertDialogItem item = new DropDownAlertDialogItem(this.getContext(), dropDownItemInfo);
                item.index = i;
                item.setOnTouchListener(this);
                item.topLine = DropDownAlertDialog2.this.createLine(0xFFFFFFFF);
                item.bootomLine = DropDownAlertDialog2.this.createLine(0xFFDDDDDD);
                this.addView(item.topLine);
                this.addView(item);
                this.addView(item.bootomLine);
                i++;
            }
//            if (!tag && i > 0)
//                this.activeItem = this.itemList.get(0).setChecked(true);
            return;
        }

        public void append(List<AreaInfo> list, Integer currentValue) {
            this.clear();
            if (list == null || list.size() == 0)
                return;
            int i = 0;
//            boolean tag = false;
            for (AreaInfo dropDownItemInfo : list) {
                DropDownAlertDialogItem item = new DropDownAlertDialogItem(this.getContext(), dropDownItemInfo);
                item.index = i;
                item.setOnTouchListener(this);
                item.topLine = DropDownAlertDialog2.this.createLine(0xFFFFFFFF);
                item.bootomLine = DropDownAlertDialog2.this.createLine(0xFFDDDDDD);
                this.addView(item.topLine);
                this.addView(item);
                this.addView(item.bootomLine);
                if (currentValue != null && currentValue.equals(dropDownItemInfo.getAreaId())) {
                    this.activeItem = item;
                    item.setChecked(true);
//                    tag = true;
                }
                i++;
            }
//            if (!tag && i > 0)
//                this.activeItem = this.itemList.get(0).setChecked(true);
            return;
        }

        public void clear() {
            this.contentLayout.removeAllViews();
            this.activeItem = null;
            this.itemList.clear();
            return;
        }

        public void addView(View child) {
            this.contentLayout.addView(child);
            return;
        }

        public void addView(DropDownAlertDialogItem item) {
            this.contentLayout.addView(item);
            this.itemList.add(item);
            return;
        }

        public int getIndex() {
            return this.index;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (DropDownAlertDialog2.this.loadLayout != null && DropDownAlertDialog2.this.loadLayout.getVisibility() != View.GONE)
                return false;
            if (v instanceof DropDownAlertDialogItem) {
                DropDownAlertDialogItem item = (DropDownAlertDialogItem) v;
                if (item.dropDownItemInfo.isCheck())
                    return false;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        item.changeBackgroundColor(true);
                        break;
                    case MotionEvent.ACTION_OUTSIDE:
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        item.changeBackgroundColor(false);
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            for (DropDownAlertDialogItem dItem : this.itemList)
                                dItem.setChecked(dItem == item);
                            this.activeItem = item;
                            if (DropDownAlertDialog2.this.onDropDownAlertDialogEvent != null)
                                DropDownAlertDialog2.this.onDropDownAlertDialogEvent.onItemChange(DropDownAlertDialog2.this, this, item);
                        }
                        break;
                    default:
                        break;
                }
            }
            return true;
        }

        /**
         * 滚动到选中项
         *
         * @param top
         */
        private void scrollActive(final int top) {
            this.post(new Runnable() {
                @Override
                public void run() {
                    DropDownAlertDialogPanel.this.scrollTo(0, top);
                    return;
                }
            });
            return;
        }

        /**
         * 滚动到选中项
         */
        public void scrollActive() {
            if (this.activeItem != null) {
                int top = ((this.activeItem.index + 1) * (DropDownAlertDialog2.this.itemHeight + 2));
                if (top > DropDownAlertDialog2.this.contentHeight) {
                    this.scrollTo(0, top - DropDownAlertDialog2.this.itemHeight);
                    this.scrollActive(top - DropDownAlertDialog2.this.itemHeight);
                }
            }
            return;
        }

        /**
         * 清空所有选择
         */
        public void clearAllCheck() {
            for (DropDownAlertDialogItem item : this.itemList)
                item.setChecked(false);
            return;
        }
    }

    /**
     * 下拉弹框子项
     *
     * @author xiong
     * @date 2013-8-22 下午04:55:42
     */
    public class DropDownAlertDialogItem extends LinearLayout {
        private int index;

        private AreaInfo dropDownItemInfo;

        private TextView textView;

        private View topLine;

        private View bootomLine;

        private ImageView imageView;

        public DropDownAlertDialogItem(Context context, AreaInfo dropDownItemInfo) {
            super(context);
            this.setOrientation(LinearLayout.HORIZONTAL);
            this.dropDownItemInfo = dropDownItemInfo;
            int padding = DropDownAlertDialog2.this.transformIntValue(5);
            this.setPadding(padding, 0, padding, 0);

            LayoutParams params = DropDownAlertDialog2.getL_P_VV(DropDownAlertDialog2.this.transformIntValue(18), DropDownAlertDialog2.this.transformIntValue(18));
            int margin = (DropDownAlertDialog2.this.itemHeight - params.height) / 2;
            params.topMargin = params.bottomMargin = margin;
            params.rightMargin = padding;
            this.imageView = new ImageView(context);
//            this.imageView.setImageResource(R.mipmap.icon_dropdown_item_check);
//            this.addView(this.imageView, params);

            this.textView = new TextView(context);
            this.textView.setTextSize(14);
            this.textView.setGravity(Gravity.CENTER_VERTICAL);
            this.textView.setText(dropDownItemInfo.getAreaName());
            this.textView.setEllipsize(TruncateAt.MIDDLE);
            this.textView.setLayoutParams(DropDownAlertDialog2.getL_P_MV(DropDownAlertDialog2.this.itemHeight));
            this.addView(this.textView);

            this.setChecked(dropDownItemInfo.isCheck());
            return;
        }

        /**
         * 是否选中
         *
         * @param check
         */
        public DropDownAlertDialogItem setChecked(boolean check) {
            this.dropDownItemInfo.setCheck(check);
            this.imageView.setVisibility(check ? View.VISIBLE : View.INVISIBLE);
            this.textView.setTextColor(check ? 0xFFe00000 : 0xFF000000);
            this.changeBackgroundColor(check);
            return this;
        }

        /**
         * 改变背景颜色
         *
         * @param tag
         */
        public void changeBackgroundColor(boolean tag) {
            this.setBackgroundColor(tag ? 0xFFFFFFFF : 0xFFFAFAFA);
            return;
        }

        public AreaInfo getDropDownItemInfo() {
            return this.dropDownItemInfo;
        }

        public int getIndex() {
            return this.index;
        }

        public void setVisibility(int visibility) {
            super.setVisibility(visibility);
            if (this.topLine != null)
                this.topLine.setVisibility(visibility);
            if (this.bootomLine != null)
                this.bootomLine.setVisibility(visibility);
            return;
        }
    }

//    /**
//     * 弹出框显示的时候
//     */
//    @Override
//    public void onShow(DialogInterface dialog) {
//        for (DropDownAlertDialogPanel panel : this.panelList)
//            panel.scrollActive();
//        return;
//    }

    public String getChooseText() {
        AreaInfo areaInfo = this.provincePanel.getDropDownItemInfo();
        String text = (areaInfo != null) ? areaInfo.getAreaName() : "";
        areaInfo = this.cityPanel.getDropDownItemInfo();
        text += (areaInfo != null) ? areaInfo.getAreaName() : "";
        areaInfo = this.zonePanel.getDropDownItemInfo();
        text += (areaInfo != null) ? areaInfo.getAreaName() : "";
        if (this.streetPanel.getVisibility() != View.GONE) {
            areaInfo = this.streetPanel.getDropDownItemInfo();
            text += (areaInfo != null) ? areaInfo.getAreaName() : "";
        }
        return text;
    }

    protected boolean onCheckNoEvent() {
        return (this.loadLayout == null || this.loadLayout.getVisibility() == View.GONE);
    }

    private int transformIntValue(int value) {
        return (int) (this.density * value);
    }

    private static LinearLayout.LayoutParams getL_P_VM(int value) {
        return new LinearLayout.LayoutParams(value, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    private static LinearLayout.LayoutParams getL_P_MV(int value) {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, value);
    }

    private static LinearLayout.LayoutParams getL_P_VV(int wValue, int hValue) {
        return new LinearLayout.LayoutParams(wValue, hValue);
    }

    private static RelativeLayout.LayoutParams getR_P_MV(int value) {
        return new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, value);
    }

    private static RelativeLayout.LayoutParams getR_P_MM() {
        return new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    private static TableRow.LayoutParams getTR_P_VV(int wValue, int hValue) {
        return new TableRow.LayoutParams(wValue, hValue);
    }

    private static RelativeLayout.LayoutParams getR_P_VV(int wValue, int hValue) {
        return new RelativeLayout.LayoutParams(wValue, hValue);
    }
}
