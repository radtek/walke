package com.mabang.android.activity.user;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mabang.android.R;
import com.mabang.android.activity.base.AppActivity;
import com.mabang.android.adapter.AdBannerAdapter;
import com.mabang.android.adapter.AdBigBannerAdapter;
import com.mabang.android.config.Api;
import com.mabang.android.config.Constants;
import com.mabang.android.config.IntentCode;
import com.mabang.android.dao.DBUtil;
import com.mabang.android.entity.vo.AdvanceOrderInfo;
import com.mabang.android.entity.vo.BillboardImageInfo;
import com.mabang.android.entity.vo.BillboardInfo;
import com.mabang.android.entity.vo.Message;
import com.mabang.android.okhttp.HttpReuqest;
import com.mabang.android.utils.AppUtils;
import com.mabang.android.utils.DialogManager;
import com.mabang.android.widget.AdDetailsView;
import com.mabang.android.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import walke.base.tool.WindowUtil;
import walke.base.widget.DialogUtil;
import walke.base.widget.banner.BannerView;

/**
 * Created by walke on 2018/2/4.
 * email:1032458982@qq.com
 */

public class AdDetailsActivity extends AppActivity {
    private BannerView bvTop;
    //    private BannerView bvBig;
    private CustomViewPager bvBig;
    private Button btAddPreselect, btMyPreselect;
    private AdDetailsView advDetails;
    private BillboardInfo mBillboardInfo;
    private LinearLayout llBigPager;
    private List<BillboardImageInfo> imageInfoList = new ArrayList<>();
    private TextView tvBigCancel;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_ad_details;
    }

    @Override
    protected void initView() {
        initTitleViewById(R.id.details_TitleView);
        AppUtils.adaptiveNdk_StatusBar(this, findViewById(R.id.details_statusBar));
        bvTop = ((BannerView) findViewById(R.id.details_adsBanner));
        btAddPreselect = ((Button) findViewById(R.id.details_btAddPreselect));
        btMyPreselect = ((Button) findViewById(R.id.details_btPreselect));
        advDetails = ((AdDetailsView) findViewById(R.id.details_advDetails));
        llBigPager = ((LinearLayout) findViewById(R.id.details_rlBigPager));
        bvBig = ((CustomViewPager) findViewById(R.id.details_bvBig));

        tvBigCancel = ((TextView) findViewById(R.id.details_tvBigCancel));
        llBigPager.setVisibility(View.GONE);

        btAddPreselect.setOnClickListener(this);
        btMyPreselect.setOnClickListener(this);
        tvBigCancel.setOnClickListener(this);

        ViewGroup.LayoutParams bvLayoutParams = bvTop.getLayoutParams();
        int windowWidth = WindowUtil.getWindowWidth(this);
//        float f=293f/447f;//0.66
        float f = 300f / 560f;//0.53
        bvLayoutParams.width = windowWidth;
        bvLayoutParams.height = (int) (windowWidth * f);
        bvTop.setLayoutParams(bvLayoutParams);

//        ViewGroup.LayoutParams bigParams = bvTop.getLayoutParams();
//        int windowHeight = WindowUtil.getWindowHeight(this);
//        bigParams.width = windowWidth;
//        bigParams.height = windowHeight;
//        bvBig.setLayoutParams(bvLayoutParams);

        //一开始默认不显示的，等服务器返回数据后，判断是否显示；
        btAddPreselect.setVisibility(View.GONE);
        btMyPreselect.setVisibility(View.GONE);

    }

    @Override
    protected void initData() {

        Intent intent = getIntent();
        if (intent != null && intent.getSerializableExtra(Constants.BILLBOARD_INFO) != null) {
            mBillboardInfo = (BillboardInfo) intent.getSerializableExtra(Constants.BILLBOARD_INFO);
            enterData(mBillboardInfo);
            requestBillboardInfo(mBillboardInfo.getId());
        }
    }

    /**
     * @param info 广告位是否可用info.isAvailable()
     */
    private void enterData(BillboardInfo info) {//g
        List<BillboardImageInfo> adImageList = info.getAdvertisingImageList();//广告位图
//        List<BillboardImageInfo> acceptanceImageList = info.getAcceptanceImageList();//验收图片集合

        if (adImageList != null) {
            imageInfoList.addAll(adImageList);
        }
//        if (acceptanceImageList != null) {
//            imageInfoList.addAll(acceptanceImageList);
//        }
        if (imageInfoList != null && imageInfoList.size() > 0) {
            AdBannerAdapter adapter = new AdBannerAdapter(this, imageInfoList);
            bvTop.setAdapter(adapter);
            if (imageInfoList.size()>1) {
                bvTop.startRoll(imageInfoList.size() * 20, 2100);
            }
            AdBigBannerAdapter adapterBig = new AdBigBannerAdapter(this, imageInfoList);
            bvBig.setAdapter(adapterBig);
            logI("adapter 设置点击事件");
            adapter.setItemClickListener(new AdBannerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    logI("adapter 点击了 position " + position);
                    llBigPager.setVisibility(View.VISIBLE);
                    bvBig.setSelectionItem(position);
                    bvTop.pause();
                }
            });

        } else {
            // TODO: 2018/2/24 以后要delete
            bvTop.setEmptyView2("暂无图片数据");
        }
        advDetails.setViewByData(info);
    }

    /**
     * @param info 广告位是否可用info.isAvailable()
     */
    private void setViewByData(BillboardInfo info) {//g
        List<BillboardImageInfo> adImageList = info.getAdvertisingImageList();//广告位图
//        List<BillboardImageInfo> acceptanceImageList = info.getAcceptanceImageList();//验收图片集合

        if (adImageList != null) {
            imageInfoList.addAll(adImageList);
        }
//        if (acceptanceImageList != null) {
//            imageInfoList.addAll(acceptanceImageList);
//        }
        if (imageInfoList != null && imageInfoList.size() > 0) {
            AdBannerAdapter adapter = new AdBannerAdapter(this, imageInfoList);
            bvTop.setAdapter(adapter);
            if (imageInfoList.size()>1) {
                bvTop.startRoll(imageInfoList.size() * 20, 2100);
            }
            AdBigBannerAdapter adapterBig = new AdBigBannerAdapter(this, imageInfoList);
            bvBig.setAdapter(adapterBig);
            logI("adapter 设置点击事件");
            adapter.setItemClickListener(new AdBannerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    logI("adapter 点击了 position " + position);
                    llBigPager.setVisibility(View.VISIBLE);
                    bvBig.setSelectionItem(position);
                    bvTop.pause();
                }
            });

        } else {
            // TODO: 2018/2/24 以后要delete
            bvTop.setEmptyView2("暂无图片数据");
        }

        advDetails.setViewByData(info);
        if (!info.isAvailable()) {
            //当不可用
            btAddPreselect.setVisibility(View.GONE);
            btMyPreselect.setVisibility(View.GONE);
        } else {
            btAddPreselect.setVisibility(View.VISIBLE);
//            btMyPreselect.setVisibility(View.VISIBLE);
            if (DBUtil.checkExist(this, info)) {
                btAddPreselect.setBackgroundResource(R.drawable.corners_add_preselect_gray);
                btAddPreselect.setText("已加入预选");
            }
        }
    }

    /**
     * 广告位信息 Api.User_billboard="1007";
     *
     * @param id
     */
    private void requestBillboardInfo(Integer id) {
        BillboardInfo billboardInfo = new BillboardInfo();
        // TODO: 2018/2/14 设置对应参数
        billboardInfo.setAccount(getMemberInfo().getAccount());
        billboardInfo.setToken(getMemberInfo().getToken());
        // TODO: 2018/2/21  替换具体数据
        billboardInfo.setId(id);//
        logI("requestBillboardInfo 广告位信息:");
        httpReuqest.sendMessage(Api.User_billboard, billboardInfo, true, new HttpReuqest.CallBack<BillboardInfo>() {
            @Override
            public void onSuccess(Message message, BillboardInfo result) {
                if (Api.OK == result.getCode()) {
                    logI("result:"+result.getText());
                    // TODO: 2018/2/16 获取相应数据做对应显示
//                    mBillboardInfo=result;
                    Integer integer = mBillboardInfo.getId();
                    setViewByData(result);
                }//
//                toast(result.getText() + "");
            }

            @Override
            public void onError(Exception exc) {
                logI(exc + "");
            }

            @Override
            public void onFinish() {

            }
        });
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.details_btAddPreselect://加入预选
                boolean addOne = DBUtil.addBillboardInfo(this, mBillboardInfo);
                if (addOne) {
                    DialogUtil.tipsOneButton(this, "确定", "成功加入预选", new DialogUtil.OneButtonClickListener() {
                        @Override
                        public void buttonClick() {
                            DialogUtil.dialogDismiss(AdDetailsActivity.this);
                            btAddPreselect.setBackgroundResource(R.drawable.corners_add_preselect_gray);
                            btAddPreselect.setText("已加入预选");
                        }
                    });
                } else {
                    toast("之前已加入预选");
                }
                break;
            case R.id.details_btPreselect://我要预选
                DialogManager.dialogBookingAd(this, new DialogManager.BookingButtonClickListener() {
                    @Override
                    public void onClicked(String name, String phone, String company) {
                        commitPreselectInfo(name, phone, company);
                    }
                });
                break;
            case R.id.details_tvBigCancel://取消bigBannerView
                llBigPager.setVisibility(View.GONE);
                if (bvTop.getAdapter()!=null&&bvTop.getAdapter().getDataSize()>1) {
                    bvTop.reRoll();
                }
                break;
        }
    }

    /**
     * 提交预约信息   加入预选时有个弹窗输入预定资料信息：
     *
     * @param name
     * @param phone
     * @param company
     */
    private void commitPreselectInfo(String name, String phone, String company) {
        AdvanceOrderInfo advanceOrderInfo = new AdvanceOrderInfo();
        // TODO: 2018/2/14 设置对应参数
        advanceOrderInfo.setAccount(getMemberInfo().getAccount());
        advanceOrderInfo.setToken(getMemberInfo().getToken());
        // TODO: 2018/2/21  替换具体数据
        advanceOrderInfo.setName(name);
        advanceOrderInfo.setPhone(phone);
        advanceOrderInfo.setCompany(company);
        List<Integer> billboardList = new ArrayList<>();
        billboardList.add(mBillboardInfo.getId());
        advanceOrderInfo.setBillboardList(billboardList);
        //提交预约信息
        httpReuqest.sendMessage(Api.User_advanceOrder, advanceOrderInfo, true, new HttpReuqest.CallBack<AdvanceOrderInfo>() {
            @Override
            public void onSuccess(Message message, AdvanceOrderInfo result) {
                String text = result.getText();
                logI("提交预约信息返回："+result.toString());
                if (result.getBillboardList()!=null&&result.getBillboardList().size()>0){
                    for (Integer integer : result.getBillboardList()) {
                        logI("BillboardList: integer = "+integer);
                    }
                }

                if (Api.OK == result.getCode()) {
                    // TODO: 2018/2/16 获取相应数据做对应显示
                    DialogManager.dialogDismiss(AdDetailsActivity.this);

                    List<BillboardInfo> billboardInfos = DBUtil.queryAll(AdDetailsActivity.this);
                    boolean deleteOne = DBUtil.deleteOne(AdDetailsActivity.this, mBillboardInfo);
                    List<BillboardInfo> billboardInfos2 = DBUtil.queryAll(AdDetailsActivity.this);

                    if (btAddPreselect.getVisibility() == View.VISIBLE) {
                        btAddPreselect.setVisibility(View.GONE);
                    }
//                    if (btMyPreselect.getVisibility() == View.VISIBLE) {
//                        btMyPreselect.setVisibility(View.GONE);
//                    }
                }//
                toast(text + "");//
            }

            @Override
            public void onError(Exception exc) {
                logI(exc + "");
            }

            @Override
            public void onFinish() {

            }
        });
    }

    @Override
    public void titleViewRightClick() {
        super.titleViewRightClick();
//        DBUtil.queryAll(this);
        Log.i("walke", "titleViewRightClick: ");
    }

    @Override
    public void onBackPressed() {
        if (llBigPager.getVisibility() == View.VISIBLE) {
            llBigPager.setVisibility(View.GONE);
            if (bvTop.getAdapter()!=null&&bvTop.getAdapter().getDataSize()>1) {
                bvTop.reRoll();
            }
        } else {
            setResult(IntentCode.AdDetails_Back);
            super.onBackPressed();
        }
    }

    @Override
    public void titleViewLeftClick() {
        setResult(IntentCode.AdDetails_Back);
        super.titleViewLeftClick();
    }
}
