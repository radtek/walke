package walke.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import walke.base.BaseFragment;

/**
 * Created by Walke.Z on 2017/4/24.
 */
public abstract class AppFragment extends BaseFragment {

    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        if (mRootView==null) {
            mRootView = inflater.inflate(rootLayoutId(), container, false);
        }
        initView(mRootView,savedInstanceState);
        initData();
        return mRootView;
    }

    protected abstract int rootLayoutId();

    protected abstract void initView(View rootView, Bundle savedInstanceState);

    protected abstract void initData();

}
