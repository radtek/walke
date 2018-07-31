package com.aipiao.bkpkold.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.caiyi.data.TrendData;


import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.aipiao.bkpkold.R;
import com.aipiao.bkpkold.base.BaseFragment;
import com.aipiao.bkpkold.bean.LottrtyAd;
import com.aipiao.bkpkold.fragment.news.NewsFragment;
import com.aipiao.bkpkold.views.TopView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * Created by caihui on 2018/3/23.
 */

public class FindFragment extends BaseFragment {

    private  TopView findTobView;
    private SmartTabLayout viewpagertab;
    private ViewPager viewpager;





    @Override
    protected void initView() {
        findTobView=findView(R.id.findTobView);
        viewpagertab = (SmartTabLayout) findView(R.id.viewpagertab);
        viewpager = (ViewPager) findView(R.id.viewpager);




    }

    @Override
    protected void initData() {
        findTobView.getLeftImageView().setVisibility(View.GONE);
        findTobView.getTopTitleTextView().setText("发现资讯");
        Bundle ssqbuild=new Bundle();
        ssqbuild.putString("gid","01");

        Bundle dltbuild=new Bundle();
        dltbuild.putString("gid","50");

        Bundle fbbuild=new Bundle();
        fbbuild.putString("gid","70");

        Bundle lqbuild=new Bundle();
        lqbuild.putString("gid","71");

        Bundle zcbuild=new Bundle();
        zcbuild.putString("gid","80");

        Bundle singbuild=new Bundle();
        singbuild.putString("gid","86");

        Bundle fucaibuild=new Bundle();
        fucaibuild.putString("gid","03");

        Bundle pl3build=new Bundle();
        pl3build.putString("gid","53");

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getFragmentManager(), FragmentPagerItems.with(getContext())
                .add("双色球",NewsFragment.class,ssqbuild)
                .add("超级大乐透",NewsFragment.class,dltbuild)
                .add("竞彩足球",NewsFragment.class,fbbuild)
                .add("竞彩篮球",NewsFragment.class,lqbuild)
                .add("足彩",NewsFragment.class,zcbuild)
                .add("北京单场",NewsFragment.class,singbuild)
                .add("福彩3D",NewsFragment.class,fucaibuild)
                .add("排列3",NewsFragment.class,pl3build)
                .create());

        viewpager.setAdapter(adapter);
        viewpagertab.setViewPager(viewpager);

        viewpagertab.setOnTabClickListener(new SmartTabLayout.OnTabClickListener() {
            @Override
            public void onTabClicked(int position) {

            }
        });
    }








    @Override
    protected int getLayout() {
        return R.layout.fragment_find;
    }

    protected void getTrendData(InputStream inputStream) throws XmlPullParserException, IOException {
        ArrayList arrayList = new ArrayList();
        XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
        newPullParser.setInput(inputStream, "utf-8");
        TrendData r0;
        for (int eventType = newPullParser.getEventType(); 1 != eventType; eventType = newPullParser.next()) {
            String name = newPullParser.getName();
            if (eventType == 2) {
                if ("row".equals(name)) {
                    LottrtyAd lotteryAd = new LottrtyAd();
                    lotteryAd.setType("row");
                    lotteryAd.setAid(newPullParser.getAttributeValue(null, "aid"));
                    lotteryAd.setArcurl(newPullParser.getAttributeValue(null, "arcurl"));
                    lotteryAd.setNtitle(newPullParser.getAttributeValue(null, "ntitle"));
                    lotteryAd.setDescription(newPullParser.getAttributeValue(null, "description"));
                    lotteryAd.setZan(newPullParser.getAttributeValue(null, "zan"));
                    lotteryAd.setNdate(newPullParser.getAttributeValue(null, "ndate"));
                    lotteryAd.setGid(newPullParser.getAttributeValue(null, "gid"));
                    arrayList.add(lotteryAd);
                }
            }
        }
    }

    public static Fragment newInstance() {
        FindFragment findFragment = new FindFragment();
        return findFragment;
    }
}
