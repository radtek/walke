package com.mabang.android.activity.user;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mabang.android.R;
import com.mabang.android.activity.base.AppActivity;
import com.mabang.android.adapter.XlistAutoAdapter;
import com.mabang.android.config.Api;
import com.mabang.android.config.Constants;
import com.mabang.android.config.IntentCode;
import com.mabang.android.dao.DBUtil;
import com.mabang.android.entity.vo.AdvanceOrderInfo;
import com.mabang.android.entity.vo.BillboardInfo;
import com.mabang.android.entity.vo.CheckAdvanceInfo;
import com.mabang.android.entity.vo.Message;
import com.mabang.android.okhttp.HttpReuqest;
import com.mabang.android.utils.DialogManager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import walke.base.widget.DialogUtil;
import walke.base.widget.IconTextView;
import walke.base.widget.xlist.XListView;

/**
 * Created by walke on 2018/2/4.
 * email:1032458982@qq.com
 */
public class AdPreselectActivity extends AppActivity {

    private XListView mXListView;
    private Button btPreselect;
    private RelativeLayout redactLayout;
    private List<BillboardInfo> mBillboardInfoList = new ArrayList<>();
    private XlistAutoAdapter mAdapter;
    private IconTextView selectAll, selectDelete;
    private TextView lvEmptyView;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_ad_preselect;
    }

    @Override
    protected void initView() {
        initTitleViewById(R.id.aap_TitleView);
//        mTitleView.getTvRight().setVisibility(View.VISIBLE);
        mTitleView.getTvRight().setText("编辑");
        mXListView = ((XListView) findViewById(R.id.aap_xListView));
        lvEmptyView = ((TextView) findViewById(R.id.aap_lvEmptyView));

        mXListView.setEmptyView(lvEmptyView);
        btPreselect = ((Button) findViewById(R.id.aap_btPreselect));
        redactLayout = ((RelativeLayout) findViewById(R.id.aap_redactLayout));//redact:编辑
        redactLayout.setVisibility(View.GONE);
        selectAll = ((IconTextView) findViewById(R.id.aap_selectAll));
        selectDelete = ((IconTextView) findViewById(R.id.aap_selectDelete));

        btPreselect.setOnClickListener(this);
        selectAll.setOnClickListener(this);
        selectDelete.setOnClickListener(this);

        //进入时：预选所选按钮是默认隐藏，只有当列表中数据条数大于0时
        btPreselect.setVisibility(View.GONE);


        mXListView.setPullRefreshEnable(false);//true
        mXListView.setLoadMoreEnable(false);//true

        mXListView.setXListRefreshOrLoadMoreListener(new XListView.XListRefreshOrLoadMoreListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mXListView.stopRefresh();
                    }
                },700);
            }

            @Override
            public void onAutoLoadMore() {

            }
        });

    }

    @Override
    protected void initData() {
        mBillboardInfoList = DBUtil.queryAll(this);//本地数据库预选广告位列表
        if (mBillboardInfoList != null && mBillboardInfoList.size() > 0) {
            checkAdvance();
            btPreselect.setVisibility(View.VISIBLE);
        }
        mXListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String topRightText = mTitleView.getTvRight().getText().toString().trim();
                Integer clickItem = position - 1;
                if (redactLayout.getVisibility() == View.GONE) {//编辑布局不显示，点击item进入详情界面
                    //可以进入详情页面
                    Intent intent = new Intent(AdPreselectActivity.this, AdDetailsActivity.class);
                    intent.putExtra(Constants.BILLBOARD_INFO, mBillboardInfoList.get(clickItem));
//                    startActivity(intent);
                    startActivityForResult(intent, IntentCode.AdPreselect_to_AdDetails);
                } else {//编辑布局显示，点击item进行勾选
                    if (position < 1)
                        return;
                    BillboardInfo info = mBillboardInfoList.get(clickItem);
                    if (info.isSelected())
                        info.setSelected(false);
                    else
                        info.setSelected(true);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * 检查预约信息
     */
    private void checkAdvance() {//api
        CheckAdvanceInfo checkAdvanceInfo = new CheckAdvanceInfo();
        checkAdvanceInfo.setAccount(getMemberInfo().getAccount());
        checkAdvanceInfo.setToken(getMemberInfo().getToken());
        //// TODO: 2018/2/21  替换具体数据  将想要检查的BillboardInfo列表遍历后存到map中然后上传 ?
        LinkedHashMap<Integer, Boolean> billboardMap = new LinkedHashMap<>();
        //billboardMap 检查预约广告位是否可用：true为可用; false为占用或其它
        for (BillboardInfo billboardInfo : mBillboardInfoList) {
            logI("检查预约信息:mBillboardInfoList遍历取出id放到Map提交：" + billboardInfo.toString());
            billboardMap.put(billboardInfo.getId(), true);//true
        }
        checkAdvanceInfo.setBillboardMap(billboardMap);
        logI("checkAdvance 检查预约信息:");
        httpReuqest.sendMessage(Api.User_checkAdvance, checkAdvanceInfo, true, new HttpReuqest.CallBack<CheckAdvanceInfo>() {
            @Override
            public void onSuccess(Message message, CheckAdvanceInfo result) {
                if (Api.OK == result.getCode()) {
                    // TODO: 2018/2/16 获取相应数据做对应显示
                    LinkedHashMap<Integer, Boolean> bMap = result.getBillboardMap();
                    //筛选出可用的
                    filtrateBillBoardInfo(bMap);

                    if (mBillboardInfoList.size() < 1) {
                        return;
                    }
                    //默认全部选中
                    for (BillboardInfo billboardInfo : mBillboardInfoList) {
                        logI("检查预约信息 返回后+筛选出可用的：billboardInfo=" + billboardInfo.toString());
                        billboardInfo.setSelected(true);
                    }
                    mAdapter = new XlistAutoAdapter(mBillboardInfoList);
                    mXListView.setAdapter(mAdapter);
                }//
                toast(result.getText() + "");
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

    /**
     * 筛选出可用的
     */
    private void filtrateBillBoardInfo(LinkedHashMap<Integer, Boolean> bMap) {
        for (Integer integer : bMap.keySet()) {
            boolean canUse = bMap.get(integer);//true为可用; false为占用或其它
            logI("result.getBillboardMap: integer = " + integer + "   canUse = " + canUse);
            if (!canUse) {//记录不可用，之后从list中移除
                removeBillboardById(integer);
            }
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == btPreselect) {//点击预定所选
//            commitPreselectInfo();
            if (isNoSelected()) {
                toast("广告位不能为空，请编辑、选择");
                return;
            }
            DialogManager.dialogBookingAd(this, new DialogManager.BookingButtonClickListener() {
                @Override
                public void onClicked(String name, String phone, String company) {
                    commitPreselectInfo(name, phone, company);
                }
            });
        } else if (v == selectDelete) {//
            if (mBillboardInfoList != null && mBillboardInfoList.size() > 0) {
                boolean isHasSelect = false;
                for (BillboardInfo billboardInfo : mBillboardInfoList) {
                    if (billboardInfo.isSelected()) {
                        isHasSelect = true;
                        break;
                    }
                }
                if (isHasSelect) {
                    DialogUtil.tipsTwoButton(this, "取消", "确定", "删除所选广告位？", new DialogUtil.TwoButtonClickListener() {
                        @Override
                        public void leftOnClick() {
                        }

                        @Override
                        public void rightOnClick() {
                            removeSelectBillboard();
                        }
                    });
                } else {
                    toast("请选择广告位");
                }
            } else {
                toast("没有广告位信息");
            }
        } else if (v == selectAll) {//全选：包括全打勾，全去勾

            if (mBillboardInfoList != null && mBillboardInfoList.size() > 0) {
                boolean isAllSelected = true;

                for (BillboardInfo billboardInfo : mBillboardInfoList) {
                    if (false == billboardInfo.isSelected()) {
                        isAllSelected = false;
                        break;
                    }
                }

                if (isAllSelected) {
                    for (BillboardInfo billboardInfo : mBillboardInfoList) {
                        billboardInfo.setSelected(false);
                    }
                } else {
                    for (BillboardInfo billboardInfo : mBillboardInfoList) {
                        billboardInfo.setSelected(true);
                    }
                }
                mAdapter.notifyDataSetChanged();
            } else {
                toast("没有广告位信息");
            }
        }
    }

    private void removeSelectBillboard() {
        List<BillboardInfo> deleteInfos = new ArrayList<>();
        //遍历将选中的广告位id存到一个集合里
        for (BillboardInfo billboardInfo : mBillboardInfoList) {
            if (billboardInfo.isSelected()) {
                deleteInfos.add(billboardInfo);
            }
        }
        boolean deleteList = DBUtil.deleteList(this, deleteInfos);
        if (deleteList) {
            mBillboardInfoList.removeAll(deleteInfos);
            mAdapter.notifyDataSetChanged();
            btPreselect.setVisibility(View.GONE);
        } else {
            logI("删除失败");
        }
    }

    /**
     * @return m没有广告位被选中
     */
    private boolean isNoSelected() {
        for (BillboardInfo billboardInfo : mBillboardInfoList) {
            if (billboardInfo.isSelected())
                return false;
        }
        return true;
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
        final List<Integer> billboardList = new ArrayList<>();
        for (BillboardInfo billboardInfo : mBillboardInfoList) {
            logI("提交预约信息: billboardInfo=" + billboardInfo.toString());
            if (billboardInfo.isSelected()) {
                billboardList.add(billboardInfo.getId());
            }
        }

        advanceOrderInfo.setBillboardList(billboardList);
        logI("提交预约信息:");
        //提交预约信息
        httpReuqest.sendMessage(Api.User_advanceOrder, advanceOrderInfo, true, new HttpReuqest.CallBack<AdvanceOrderInfo>() {
            @Override
            public void onSuccess(Message message, AdvanceOrderInfo result) {
                if (Api.OK == result.getCode()) {
                    DialogManager.dialogDismiss(AdPreselectActivity.this);
                    // TODO: 2018/2/16 获取相应数据做对应显示
                    removeBillboardList(billboardList);//要去断点看看返回数据有没有广告位id列表，有则用返回的
                }//
                toast(result.getText() + "");
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
//        super.titleViewRightClick();
        if (redactLayout.getVisibility() == View.VISIBLE) {
            redactLayout.setVisibility(View.GONE);
            mTitleView.getTvRight().setText("编辑");
            if (mBillboardInfoList != null && mBillboardInfoList.size() > 0) { //列表中有数据的情况下
//                boolean hasSelect=false;
//                for (BillboardInfo billboardInfo : mBillboardInfoList) {
//                    if (billboardInfo.isSelected()){
//                        hasSelect=true;
//                        break;
//                    }
//                }
//                if (hasSelect)
//                    btPreselect.setVisibility(View.VISIBLE);//在点编辑的时候，预定所选按钮如果有显示的
//                else
//                    btPreselect.setVisibility(View.GONE);//在点编辑的时候，预定所选按钮如果有显示的
                btPreselect.setVisibility(View.VISIBLE);//在点编辑的时候，预定所选按钮如果有显示的

            }
        } else {
            redactLayout.setVisibility(View.VISIBLE);
            mTitleView.getTvRight().setText("取消");
            if (mBillboardInfoList != null && mBillboardInfoList.size() > 0) { //列表中有数据的情况下
                btPreselect.setVisibility(View.GONE);//在点编辑的时候，预定所选按钮如果有显示的
            }
        }
    }

    /**
     * billboardList 是提交的广告位id集合
     *
     * @param commitBillboardList
     */
    private void removeBillboardList(List<Integer> commitBillboardList) {

        for (Integer integer : commitBillboardList) {
            removeBillboardById(integer);
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 移除广告位byId
     *
     * @param billboardId
     */
    private void removeBillboardById(Integer billboardId) {
        BillboardInfo info = null;
        for (BillboardInfo billboardInfo : mBillboardInfoList) {
            if (billboardId.equals(billboardInfo.getId())) {
                info = billboardInfo;
                boolean deleteOne = DBUtil.deleteOne(this, billboardInfo);//也从本的数据苦中删除
                logI("移除广告位byId: deleteOne=" + deleteOne);
                if (!deleteOne) {
                    logI("移除广告位byId:从本的数据库中删除预选信息失败");
                }

            }
        }
        if (info != null) {
            logI("移除广告位byId: info = " + info.toString());
            mBillboardInfoList.remove(info);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (IntentCode.AdDetails_Back == resultCode) {
//            mBillboardInfoList = DBUtil.queryAll(this);//本地数据库预选广告位列表
//            checkAdvance();
//        }
    }
}
