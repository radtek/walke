package com.aipiao.bkpk.fragment;

import android.support.v4.app.Fragment;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.aipiao.bkpk.R;
import com.aipiao.bkpk.adapter.news.NewsAdapter;
import com.aipiao.bkpk.base.BaseFragment;

import okhttp3.OkHttpClient;

/**
 * Created by chennaikang on 2018/3/24.
 */

public class AnalysisFragment extends BaseFragment {

    private PullToRefreshListView analysPullToRefreshListView;
    private NewsAdapter analysisAdapter;


    @Override
    public void onResume() {
        super.onResume();
//        Request request = new Request.Builder()
//                .url("http://5.9188.com/trade/forecast.go?name=yuce")
//                .build();
//        client.newCall(request).enqueue(new okhttp3.Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                System.out.println();
//                toast("请检查网络是否正常");
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//                InputStream inputStream = response.body().byteStream();
//                try {
////                    getAnalysisData(inputStream);
//                } catch (XmlPullParserException e) {
//                    e.printStackTrace();
//                } finally {
//                    if (inputStream != null) {
//                        inputStream.close();
//                    }
//                }
//            }
//        });
    }

    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void initView() {
        analysPullToRefreshListView = (PullToRefreshListView) findView(R.id.analysPullToRefreshListView);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayout() {
        return R.layout.fragmnet_analysis;
    }

    public static Fragment newInstance() {
        AnalysisFragment analysisFragment=new AnalysisFragment();
        return analysisFragment;
    }
}
