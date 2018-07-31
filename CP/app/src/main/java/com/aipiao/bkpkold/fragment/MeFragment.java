package com.aipiao.bkpkold.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.aipiao.bkpkold.R;
import com.aipiao.bkpkold.acitivty.user.AboutActivity;
import com.aipiao.bkpkold.acitivty.user.HelpActivity;
import com.aipiao.bkpkold.acitivty.user.KefuViewlActivity;
import com.aipiao.bkpkold.acitivty.user.LoginActivity;
import com.aipiao.bkpkold.acitivty.user.UserInfoActivity;
import com.aipiao.bkpkold.base.BaseFragment;
import com.aipiao.bkpkold.bean.bmob.User;
import com.aipiao.bkpkold.utils.AppUtils;
import com.aipiao.bkpkold.utils.SharepreUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

/**
 * Created by caihui on 2018/3/26.
 */

public class MeFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = MeFragment.class.getName();
    private View viewStatusBar;
    private TextView buttonBack;
    private TextView title;
    private Button buttonRight;
    private ImageView buttonSign;
    private ScrollView scrollView;
    private RelativeLayout rlUnLogin;
    private CircleImageView imageViewTouXiang;
    private TextView tvRegistLogin;
    private ImageView ivQiandao;
    private RelativeLayout rlLogin;
    private CircleImageView imageViewTouXiangLogin;
    private LinearLayout llNickname;
    private TextView tvNickname;
    private ImageView ivPen;
    private TextView tvCaijin;
    private ImageView ivQiandaoLogin;
    private RelativeLayout reChongzhiTikuanLayout;
    private View viewMiddle;
    private Button buttonChongZhi;
    private Button buttonTiKuan;
    private LinearLayout layoutItemLogin;
    private RelativeLayout relUserCenter;
    private LinearLayout llUserCenter;
    private ImageView ivGetGoldDou;
    private RelativeLayout relativelayoutInvite;
    private View viewShareBottomLine;
    private RelativeLayout relativelayoutWeixinLogin;
    private TextView tvWeixinLoginDesc;
    private ImageView imgArrowWeixin;
    private RelativeLayout relativelayoutHelp;
    private ImageView ivWeixinImageArrow;
    private RelativeLayout relativelayoutKefu;
    private ImageView ivQqImageArrow;
    private RelativeLayout relativelayoutCheckVersion;
    private ImageView ivHaveNewVersion;
    private LinearLayout llVersionDesc;
    private TextView textViewVersion;
    private TextView textViewNewVersion;
    private RelativeLayout relativelayoutAbout;
    private RelativeLayout relativelayoutLogout;

    private String objectId = "";

    @Override
    public void onResume() {
        super.onResume();
        objectId = SharepreUtil.getString(getContext(), "objectId");
        if (!TextUtils.isEmpty(objectId)) {
            relativelayoutLogout.setVisibility(View.VISIBLE);
            rlUnLogin.setVisibility(View.GONE);
            rlLogin.setVisibility(View.VISIBLE);
        } else {
            rlUnLogin.setVisibility(View.VISIBLE);
            rlLogin.setVisibility(View.GONE);
        }

        String userString = SharepreUtil.getString(getContext(), "user");
        User user = gson.fromJson(userString, User.class);
        if (user != null) {
            if (!TextUtils.isEmpty(user.getNickname())) {
                tvNickname.setText("昵称 : "+user.getNickname()+"");
            }
            if (!TextUtils.isEmpty(user.getMoon())) {
                tvCaijin.setText("心情 : "+user.getMoon()+"");
            }
        }

        requestUserRequest(objectId);
    }

    private void requestUserRequest(final String objectId) {
        if (!TextUtils.isEmpty(objectId)) {
            BmobQuery<User> query = new BmobQuery<User>();
            query.getObject(objectId, new QueryListener<User>() {
                @Override
                public void done(User user, BmobException e) {
                    if (e == null) {
                        if (!TextUtils.isEmpty(user.getNickname())) {
                            tvNickname.setText("昵称 : "+user.getNickname()+"");
                        } else {
                            tvNickname.setText("未设置昵称");
                        }
                        if (!TextUtils.isEmpty(user.getMoon())) {
                            tvCaijin.setText("心情 : "+user.getMoon()+"");
                        } else {
                            tvCaijin.setText("未设置心情");
                        }
                    } else {
                        toast("获取用户数据异常,请重新登陆");
                    }
                }

            });
        } else {
            Log.e(TAG, "暂无登陆");
        }

    }

    @Override
    protected void initView() {

        viewStatusBar = (View) findView(R.id.view_status_bar);
        buttonBack = (TextView) findView(R.id.button_back);
        title = (TextView) findView(R.id.title);
        buttonRight = (Button) findView(R.id.button_right);
        buttonSign = (ImageView) findView(R.id.button_sign);
        scrollView = (ScrollView) findView(R.id.scroll_view);
        rlUnLogin = (RelativeLayout) findView(R.id.rl_un_login);
        imageViewTouXiang = (CircleImageView) findView(R.id.imageView_tou_xiang);
        tvRegistLogin = (TextView) findView(R.id.tv_regist_login);
        ivQiandao = (ImageView) findView(R.id.iv_qiandao);
        rlLogin = (RelativeLayout) findView(R.id.rl_login);
        imageViewTouXiangLogin = (CircleImageView) findView(R.id.imageView_tou_xiang_login);
        llNickname = (LinearLayout) findView(R.id.ll_nickname);
        tvNickname = (TextView) findView(R.id.tv_nickname);
        ivPen = (ImageView) findView(R.id.iv_pen);
        tvCaijin = (TextView) findView(R.id.tv_caijin);
        ivQiandaoLogin = (ImageView) findView(R.id.iv_qiandao_login);
        reChongzhiTikuanLayout = (RelativeLayout) findView(R.id.re_chongzhi_tikuan_layout);
        viewMiddle = (View) findView(R.id.view_middle);
        buttonChongZhi = (Button) findView(R.id.button_chong_zhi);
        buttonTiKuan = (Button) findView(R.id.button_ti_kuan);
        layoutItemLogin = (LinearLayout) findView(R.id.layout_item_login);
        relUserCenter = (RelativeLayout) findView(R.id.rel_user_center);
        llUserCenter = (LinearLayout) findView(R.id.ll_user_center);
        ivGetGoldDou = (ImageView) findView(R.id.iv_get_gold_dou);
        relativelayoutInvite = (RelativeLayout) findView(R.id.relativelayout_invite);
        viewShareBottomLine = (View) findView(R.id.view_share_bottom_line);
        relativelayoutWeixinLogin = (RelativeLayout) findView(R.id.relativelayout_weixin_login);
        tvWeixinLoginDesc = (TextView) findView(R.id.tv_weixin_login_desc);
        imgArrowWeixin = (ImageView) findView(R.id.img_arrow_weixin);
        relativelayoutHelp = (RelativeLayout) findView(R.id.relativelayout_help);
        ivWeixinImageArrow = (ImageView) findView(R.id.iv_weixin_image_arrow);
        relativelayoutKefu = (RelativeLayout) findView(R.id.relativelayout_kefu);
        ivQqImageArrow = (ImageView) findView(R.id.iv_qq_image_arrow);
        relativelayoutCheckVersion = (RelativeLayout) findView(R.id.relativelayout_check_version);
        ivHaveNewVersion = (ImageView) findView(R.id.iv_have_new_version);
        llVersionDesc = (LinearLayout) findView(R.id.ll_version_desc);
        textViewVersion = (TextView) findView(R.id.textView_version);
        textViewNewVersion = (TextView) findView(R.id.textView_new_version);
        relativelayoutAbout = (RelativeLayout) findView(R.id.relativelayout_about);
        relativelayoutLogout = (RelativeLayout) findView(R.id.relativelayout_logout);
        relativelayoutAbout.setOnClickListener(this);
        relativelayoutLogout.setOnClickListener(this);
        relUserCenter.setOnClickListener(this);
        ivQiandao.setOnClickListener(this);
        relativelayoutKefu.setOnClickListener(this);
        ivQiandaoLogin.setOnClickListener(this);
        tvRegistLogin.setOnClickListener(this);
        relativelayoutHelp.setOnClickListener(this);
        relativelayoutInvite.setOnClickListener(this);
        relativelayoutCheckVersion.setOnClickListener(this);

        relUserCenter.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        textViewVersion.setText("当前版本"+AppUtils.getVersion(getContext()));
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_me;
    }

    public static MeFragment newInstance() {
        MeFragment meFragment = new MeFragment();
        return meFragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_regist_login:
                startActivity(new Intent(getContext(), LoginActivity.class));
                break;
            case R.id.relativelayout_logout://退出登录
                relativelayoutLogout.setVisibility(View.GONE);
                SharepreUtil.putString(getContext(), "objectId", "");
                rlUnLogin.setVisibility(View.VISIBLE);
                rlLogin.setVisibility(View.GONE);
                break;

            case R.id.rel_user_center:
                if (TextUtils.isEmpty(objectId)) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    toast("请先注册或登陆");return;
                }else {
                    startActivity(new Intent(getContext(), UserInfoActivity.class));
                }
                break;

            case R.id.iv_qiandao_login:
//                if (TextUtils.isEmpty(objectId)) {
//                    toast("请先注册或登陆");
//                }
//                startActivity(new Intent(getContext(), SignActivity.class));
                break;
            case R.id.iv_qiandao:
//                if (TextUtils.isEmpty(objectId)) {
//                    toast("请先注册或登陆");
//                }
//                startActivity(new Intent(getContext(), SignActivity.class));
                break;

            case R.id.relativelayout_invite:
                Intent textIntent = new Intent(Intent.ACTION_SEND);
                textIntent.setType("text/plain");
                textIntent.putExtra(Intent.EXTRA_TEXT, "我发现一个很不错的APP哦,快来一起玩吧");
                startActivity(Intent.createChooser(textIntent, "分享"));
                break;

            case R.id.relativelayout_about:
                startActivity(new Intent(getContext(), AboutActivity.class));
                break;

            case  R.id.relativelayout_check_version:
                OkHttpUtils
                        .get()
                        .url("http://www.gocitu.com/json/appversion.json")
                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        toast("当前版本已经最新");
                    }
                    @Override
                    public void onResponse(String response, int id) {
                      toast("当前版本已经最新");
                    }
                });
                break;

            case R.id.relativelayout_help:
                startActivity(new Intent(getContext(), HelpActivity.class));
                break;
            case R.id.relativelayout_kefu:
                startActivity(new Intent(getContext(), KefuViewlActivity.class));
                break;
        }
    }
}
