package com.aipiao.bkpkold.fragment.newshome;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.aipiao.bkpkold.R;
import com.aipiao.bkpkold.acitivty.CpDoubleChoseNumberActivity;
import com.aipiao.bkpkold.adapter.news.NewsAdapter;
import com.aipiao.bkpkold.adapter.newsadapter.NHomeAdapter;
import com.aipiao.bkpkold.base.BaseFragment;
import com.aipiao.bkpkold.bean.bmob.PushNewsTitle;
import com.aipiao.bkpkold.bean.wubai.AnalysisAd;
import com.aipiao.bkpkold.fragment.news.NewsActivity;
import com.aipiao.bkpkold.views.ListViewForScrollView;
import com.aipiao.bkpkold.views.MarqueeView;
import com.aipiao.bkpkold.views.MyGridView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by caihui on 2018/4/13.
 */

public class MyNewHomeFragment extends BaseFragment {

    private SwipeRefreshLayout homeSwipeRefreshLayout;
    private Banner homeBanner;

    private MyGridView homegridview;
    private MarqueeView homeMarqueeView;

    private int[] imageViewsUrl = { R.mipmap.lll};
    private List<Integer> imageViews;

    private NHomeAdapter nHomeAdapter;

    private TextView historyn1;
    private TextView historyn2;
    private TextView historyn3;
    private TextView historyn4;
    private TextView historyn5;
    private TextView historyn6;
    private TextView historyn7;
    private ImageView shuaixin1ImageView;
    private   Random rand;
    private List<AnalysisAd> analysisAds=new ArrayList<>();
    private  Animation operatingAnim;
    private ListViewForScrollView newListView;
    @Override
    public void onResume() {
        super.onResume();
        getPushNews();
        getCurrentNewData();
    }

    @Override
    protected void initView() {
        homeSwipeRefreshLayout = (SwipeRefreshLayout) findView(R.id.homeSwipeRefreshLayout);
        homeBanner = (Banner) findView(R.id.homeBanner);
        homegridview=findView(R.id.homegridview);
        homeMarqueeView=findView(R.id.homeMarqueeView);
        newListView=findView(R.id.newListView);

        historyn1 = (TextView) findView(R.id.historyn1);
        historyn2 = (TextView) findView(R.id.historyn2);
        historyn3 = (TextView) findView(R.id.historyn3);
        historyn4 = (TextView) findView(R.id.historyn4);
        historyn5 = (TextView) findView(R.id.historyn5);
        historyn6 = (TextView) findView(R.id.historyn6);
        historyn7 = (TextView) findView(R.id.historyn7);
        shuaixin1ImageView = (ImageView) findView(R.id.shuaixin1ImageView);



        shuaixin1ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code="";
                for(int a=0;a<=6;a++){
                    code +=rand.nextInt(10)+",";//生成6位验证码
                }
                String [] data=code.split(",");
                historyn1.setText(data[0]);
                historyn2.setText(data[1]);
                historyn3.setText(data[2]);
                historyn4.setText(data[3]);
                historyn5.setText(data[4]);
                historyn6.setText(data[5]);
                historyn7.setText(data[6]);
                historyn1.startAnimation(operatingAnim);
                historyn2.startAnimation(operatingAnim);
                historyn3.startAnimation(operatingAnim);
                historyn4.startAnimation(operatingAnim);
                historyn5.startAnimation(operatingAnim);
                historyn6.startAnimation(operatingAnim);
                historyn7.startAnimation(operatingAnim);
            }
        });


        newListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent(getContext(),NewsActivity.class);
                intent.putExtra("new",analysisAds.get(position));
                startActivity(intent);
//                toast(position+"");
            }
        });

    }


    private void getPushNews() {
        BmobQuery<PushNewsTitle> query = new BmobQuery<PushNewsTitle>();
        query.setLimit(50);
        query.findObjects(new FindListener<PushNewsTitle>() {
            @Override
            public void done(List<PushNewsTitle> object, BmobException e) {
                homeSwipeRefreshLayout.setRefreshing(false);
                if (e == null) {
                    List<String> mTitle = new ArrayList<String>();
                    if (object != null && object.size() > 0) {
                        for (PushNewsTitle pushNewsTitle : object) {
                            mTitle.add(pushNewsTitle.getTitle());
                        }
                        homeMarqueeView.startWithList(mTitle);
                    }
                }
            }
        });

    }
    private final OkHttpClient client = new OkHttpClient();
    private  void getCurrentNewData(){
        Request request = new Request.Builder()
                .url("http://5.9188.com/trade/forecast.go?pn=1&gid=71")
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                toast("请检查网络是否正常");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                try {
                    getAnalysisData(inputStream);
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }
            }
        });
    }
    private NewsAdapter newsAdapter;
    private void getAnalysisData(InputStream inputStream) throws XmlPullParserException, IOException {
        final List<AnalysisAd> arrayList = new ArrayList();
        XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
        newPullParser.setInput(inputStream, "utf-8");

        for (int eventType = newPullParser.getEventType(); 1 != eventType; eventType = newPullParser.next()) {
            String name = newPullParser.getName();
            if (eventType == 2) {
                if ("row".equals(name)) {
                    AnalysisAd analysisAd = new AnalysisAd();
                    analysisAd.setAid(newPullParser.getAttributeValue(null, "aid"));
                    analysisAd.setArcurl(newPullParser.getAttributeValue(null, "arcurl"));
                    analysisAd.setNtitle(newPullParser.getAttributeValue(null, "ntitle"));
                    analysisAd.setDescription(newPullParser.getAttributeValue(null, "description"));
                    analysisAd.setNdate(newPullParser.getAttributeValue(null, "ndate"));
                    analysisAd.setGid(newPullParser.getAttributeValue(null, "gid"));
                    analysisAd.setLitpic(newPullParser.getAttributeValue(null, "litpic"));
                    arrayList.add(analysisAd);
                }
                analysisAds=arrayList;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (newsAdapter!=null){
                            newsAdapter=null;
                        }
                        newsAdapter=new NewsAdapter(arrayList);
                        newListView.setAdapter(newsAdapter);
                    }
                });

            }
        }
    }


    @Override
    protected void initData() {
        operatingAnim = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_anim);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        rand =new Random();//生成随机数
        imageViews = new ArrayList<>();
        for (int i = 0; i < imageViewsUrl.length; i++) {
            imageViews.add(imageViewsUrl[i]);
        }
        homeBanner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        //设置图片加载器
        homeBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        homeBanner.setImages(imageViews);
        //banner设置方法全部调用完毕时最后调用
        homeBanner.start();
        nHomeAdapter=new NHomeAdapter();
        homegridview.setAdapter(nHomeAdapter);

        String code="";
        for(int a=0;a<=6;a++){
            code +=rand.nextInt(10)+",";//生成6位验证码
        }
        String [] data=code.split(",");
        historyn1.setText(data[0]);
        historyn2.setText(data[1]);
        historyn3.setText(data[2]);
        historyn4.setText(data[3]);
        historyn5.setText(data[4]);
        historyn6.setText(data[5]);
        historyn7.setText(data[6]);


        homegridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), CpDoubleChoseNumberActivity.class);
                switch (position) {
                    case 0:
                        intent.putExtra("cid", "11");
                        startActivity(intent);
                        break;
                    case 1:
                        intent.putExtra("cid", "1007");
                        startActivity(intent);
                        break;
                    case 2:
                        intent.putExtra("cid", "1");
                        startActivity(intent);
                        break;
                    case 3:
                        intent.putExtra("cid", "1005");
                        startActivity(intent);
                        break;
                    case 4:
                        intent.putExtra("cid", "1006");
                        startActivity(intent);
                        break;
                    case 5:
                        intent.putExtra("cid", "13");
                        startActivity(intent);
                        break;
                    case 6:
                        intent.putExtra("cid", "1008");
                        startActivity(intent);
                        break;
                    case 7:
                        toast("暂时未开放");
//                        startActivity(new Intent(getContext(), GameFootballActivity.class));
                        break;
                    case 8:
                        break;
                }
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_newhome;
    }

    public static Fragment newInstance() {
        MyNewHomeFragment homeFragment=new MyNewHomeFragment();
        return homeFragment;
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context.getApplicationContext())
                    .load(path)
                    .into(imageView);
        }


    }
}
