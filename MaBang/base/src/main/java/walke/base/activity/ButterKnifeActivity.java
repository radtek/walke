package walke.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import walke.base.BaseActivity;


/**
 * Created by walke.Z on 2017/8/8.
 * 参考：  http://www.cnblogs.com/zhaoyanjun/p/6016341.html
 * 直接引用依赖也行 -- compile 'com.jakewharton:butterknife:8.4.0'
 *      2、Fragment ButterKnife.bind(this, mRootView)
 *      3、属性布局不能用private or static 修饰，否则会报错
 *      4、setContentView()不能通过注解实现。
 *      5、ButterKnife不能再在library module中使用,这是因为你的library中的
 *      R字段的id值不是final类型的，但是你自己的应用module中却是final类型的。
 *      针对这个问题，有人在Jack的github上issue过这个问题，他本人也做了回答
 *      http://www.th7.cn/Program/Android/201701/1070892.shtml
 */

public abstract class ButterKnifeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId());
//        ButterKnife.bind(this);
        initData();
    }

    public abstract int layoutId();

    protected abstract void initData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //ButterKnife.unbind(this)// 1、来进行解除绑定，现如今新版的Api中已经去除了unbind方法了
    }
}
