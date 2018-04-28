package walke.base.activity;

import android.os.Bundle;
import android.view.View;

import walke.base.BaseActivity;

/**
 * Created by Walke.Z on 2017/4/21.
 * 这是上一层(第二层)封装
 * ①让子类必须重写几个必要分工明细的方法便于代码编辑提高代码可阅读性
 * ②默认无(自定义的)标题栏
 */
public abstract class AppActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(rootLayoutId());
        initView();
        initData();

    }

    protected abstract int rootLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    @Override
    public void onClick(View v) {

    }

}
