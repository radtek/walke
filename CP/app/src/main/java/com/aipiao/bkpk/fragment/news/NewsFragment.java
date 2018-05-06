package com.aipiao.bkpk.fragment.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.aipiao.bkpk.R;
import com.aipiao.bkpk.adapter.news.NewsAdapter;
import com.aipiao.bkpk.base.BaseFragment;
import com.aipiao.bkpk.bean.wubai.AnalysisAd;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by chennaikang on 2018/3/24.
 */

public class NewsFragment extends BaseFragment {
    private final OkHttpClient client = new OkHttpClient();


    private PullToRefreshListView newsPullToRefreshListView;
    private NewsAdapter newsAdapter;
   private List<AnalysisAd> analysisAds=new ArrayList<>();
    private String  cid;

    private  int currentPage=1;


    @Override
    protected void initView() {
        newsPullToRefreshListView = (PullToRefreshListView) findView(R.id.newsPullToRefreshListView);
        newsPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                newsPullToRefreshListView.setRefreshing(true);
            }
        });
        Bundle bundle=getArguments();
        cid= (String) bundle.get("gid");
        Log.d("tag","cid "+cid);
        getCurrentNewData(1,cid);
    }

    private  void getCurrentNewData(int page,String cid){
        Request request = new Request.Builder()
                .url("http://5.9188.com/trade/forecast.go?pn="+page+"&gid="+cid)
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

    @Override
    protected void initData() {
        newsPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getCurrentNewData(1,cid);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getCurrentNewData(currentPage++,cid);
            }
        });
        newsPullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent(getContext(),NewsActivity.class);
                intent.putExtra("new",analysisAds.get(position));
                startActivity(intent);
//                toast(position+"");
            }
        });

}

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
                      newsPullToRefreshListView.onRefreshComplete();
                      newsAdapter=new NewsAdapter(arrayList);
                      newsPullToRefreshListView.setAdapter(newsAdapter);
                  }
              });

            }
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_ssq;
    }

    public static Fragment newInstance() {

        NewsFragment newsFragment=new NewsFragment();
        return  newsFragment;
    }
}
