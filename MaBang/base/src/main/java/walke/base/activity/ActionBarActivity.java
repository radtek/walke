package walke.base.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import walke.base.BaseActivity;
import walke.base.R;
import walke.base.tool.LayoutParamsUtil;
import walke.base.widget.TitleBar;


/**
 * Created by Walke.Z on 2017/4/21.
 * 这是上一层(第二层)封装：
 * ①让子类必须重写几个必要分工明细的方法便于代码编辑提高代码可阅读性
 * ②默认有自定义的标题栏
 * 使用LayoutParams为第二子布局重新配置铺满的宽高，可以有效避免TitleOldActivity的情况
 */
public abstract class ActionBarActivity extends BaseActivity implements TitleBar.TitleBarClickListener,View.OnClickListener {

    protected ViewGroup mRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRootView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.base_activity_action_bar, null);
        setContentView(mRootView);
        //第一子布局
        TitleBar titleBar = ((TitleBar) findViewById(R.id.aab_titleBar));
        titleBar.setTitleBarClickListener(this);
        titleBar.setTitleText(this.getClass().getSimpleName());
        //添加子类布局为第二子布局,
        View inflate = LayoutInflater.from(this).inflate(rootLayoutId(), null);
        ViewGroup.LayoutParams vg_p_mm = LayoutParamsUtil.getVG_P_MM();
        inflate.setLayoutParams(vg_p_mm);

        mRootView.addView(inflate);
        initView(titleBar);
        initData();


    }

    protected abstract int rootLayoutId();

    protected abstract void initView(TitleBar titleBar);

    protected abstract void initData();

    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void rightClick() {

    }

    @Override
    public void onClick(View v) {

    }
}
