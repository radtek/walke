package walke.base;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import walke.base.tool.LogUtil;
import walke.base.tool.ToastUtil;

/**
 * Created by Walke.Z on 2017/4/24.
 */
public abstract class BaseFragment extends Fragment {


    private BaseActivity mActivity;

    /**
     * @return 获取宿主Activity
     */
    protected BaseActivity getHoldingActivity() {
        return mActivity;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (BaseActivity) activity;
    }

    protected void toast(String message){
        if (!TextUtils.isEmpty(message))
            ToastUtil.showToast(getHoldingActivity(),message);
    }
   /* protected void middleToast(String message){
        if (!TextUtils.isEmpty(message))
            ToastUtil.showMiddleToast(getHoldingActivity(),message);
    }*/
//    protected void toastTime(String message,int time){
//        if (!TextUtils.isEmpty(message))
//            ToastUtil.showToastWithTime(getHoldingActivity(),message,time);
//    }
    protected void logI(String message){
        if (!TextUtils.isEmpty(message))
            LogUtil.i(this.getClass().getSimpleName(),"-------------------> "+message);
    }
    protected void logD(String message){
        if (!TextUtils.isEmpty(message))
            LogUtil.d(this.getClass().getSimpleName(),"--------> "+message);
    }
    protected void logE(String message){
        if (!TextUtils.isEmpty(message))
            LogUtil.e(this.getClass().getSimpleName(),"---------      -----------> "+message);
    }
}
