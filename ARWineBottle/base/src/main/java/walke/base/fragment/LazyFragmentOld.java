package walke.base.fragment;

import walke.base.BaseFragment;

/**
 * Created by Walke.Z
 * on 2017/5/12. 45.
 * email：1126648815@qq.com
 * 我们都知道，fragment放在viewPager里面，viewpager会帮我们预先加载一个，但是当我们要看
 * fragment里面的内容时，我们也许只会去看第一个，不会去看第二个，如果这时候不去实现fragment
 * 的懒加载的话，就会多余的去加载一些数据，造成用户多消耗流量，所以我们采取懒加载的方式。
 * 所谓懒加载，就是当fragment完全可见的时候我们再去加载数据
 *
 * 我们知道，当我们去滑动的时候，fragment会显示与隐藏，当fragment完全显示在我们的眼前时，fragment会调用一个方法
 */
public abstract class LazyFragmentOld extends BaseFragment {

    protected boolean isVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {//frahment从不可见到完全可见的时候，会调用该方法
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()){
            isVisible = true;
            onVisible();
        }else {
            isVisible = false;
            onInvisible();
        }
    }

    protected abstract void lazyLoad();//懒加载的方法,在这个方法里面我们为Fragment的各个组件去添加数据

    protected void onVisible(){
        logI("onVisible");
        lazyLoad();
    }

    protected void onInvisible(){
        logI("onInvisible");
    }

}
