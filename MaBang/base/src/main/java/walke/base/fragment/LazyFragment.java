package walke.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import walke.base.BaseFragment;

/**
 * Created by Walke.Z
 * on 2017/5/15. 29.
 * email：1126648815@qq.com
 */
public abstract class LazyFragment extends BaseFragment {
    protected View mRootView;
    public Context mContext;
    protected boolean isVisible;      // 标志位，标志已经可以显示。
    private boolean isPrepared;       // 标志位，标志已经初始化完成。
    private boolean isFirst = true;   // 标志位，标志是否是第一次进来

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //logI("fragment->setUserVisibleHint");
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisiable();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        setHasOptionsMenu(true);
        //logI("fragment->onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
           /* mRootView = initView();*/
            mRootView = inflater.inflate(getLayoutId(),null);
            initView(mRootView);
        }
        //logI("fragment->onCreateView");
        isPrepared = true;
        lazyLoad();
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //logI("fragment->onActivityCreated");
      /*  isPrepared = true;
        lazyLoad();*/
    }

    protected void lazyLoad() {
        if (!isPrepared || !isFirst ) {//!isVisible ||
            logI(getClass().getName() + "->initData()");
            return;
        }
        logI(getClass().getName() + "->initData()");
        initData();
        isFirst = false;
    }

    //do something
    protected void onInvisible() {
        lazyLoad();
    }
    //do something
    protected void onVisiable() {

    }

    /*public abstract View initView();*/
    public abstract int getLayoutId();

    protected abstract void initView(View rootView);

    public abstract void initData();
}
