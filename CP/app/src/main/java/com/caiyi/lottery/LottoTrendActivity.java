package com.caiyi.lottery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.caiyi.data.TrendData;
import com.caiyi.ui.DDTrendChart;
import com.caiyi.ui.DDTrendChart.ISelectedChangeListener;
import com.caiyi.ui.LottoTrendView;
import com.aipiao.bkpkold.R;
import com.aipiao.bkpkold.views.TopView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LottoTrendActivity extends Activity implements ISelectedChangeListener {

    private LottoTrendView mTrendView;
    final int maxSignleNum = 9;
     private TopView trentTopView;
    private DDTrendChart mTrendChart;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_lotto_trend);
        initViews();
        loadData();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message paramMessage) {
            super.handleMessage(paramMessage);
            LottoTrendActivity.this.mTrendChart.updateData("01", (ArrayList) paramMessage.obj);
        }
    };

    private void initViews() {
        trentTopView= (TopView) findViewById(R.id.trentTopView);
        this.mTrendView = (LottoTrendView) findViewById(R.id.ltv_trendView);
        this.mTrendChart = new DDTrendChart(this, this.mTrendView);
        this.mTrendView.setChart(this.mTrendChart);
        this.mTrendChart.setShowYilou(true);
        this.mTrendChart.setDrawLine(true);
        this.mTrendChart.setSelectedChangeListener(this);

        trentTopView.getLeftImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private final OkHttpClient client = new OkHttpClient();

    private void loadData() {
        String url = "http://mobile.9188.com/data/app/zst/01/30.xml";
        // 根据01/30.xml 或者是01/50.xm可以调整数字
        Intent intent = getIntent();
        if (intent!=null){
         String type= (String) intent.getExtras().get("type");
            if (type.equals("11")) {
                trentTopView.getTopTitleTextView().setText("双色球走势图");
                url = "http://mobile.9188.com/data/app/zst/01/50.xml";
            } else if (type.equals("1007")) {
                trentTopView.getTopTitleTextView().setText("大乐透走势图");
                url = "http://mobile.9188.com/data/app/zst/50/50.xml";
            } else if (type.equals("1")) {
                url="http://mobile.9188.com/data/app/zst/01/30.xml";
                trentTopView.getTopTitleTextView().setText("福彩3d走势图");
            } else if (type.equals("1005")) {
                url="http://mobile.9188.com/data/app/zst/54/50.xml";
                trentTopView.getTopTitleTextView().setText("排列3走势图");
            } else if (type.equals("1006")) {
                url="http://mobile.9188.com/data/app/zst/01/50.xml";
                trentTopView.getTopTitleTextView().setText("排列5走势图");
            } else if (type.equals("13")) {
                url="http://mobile.9188.com/data/app/zst/54/30.xml";
                trentTopView.getTopTitleTextView().setText("七乐彩走势图");
            } else if (type.equals("1008")) {
                url="http://mobile.9188.com/data/app/zst/50/30.xml";
                trentTopView.getTopTitleTextView().setText("七星彩走势图");
            }
        }


        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                try {
                    a(inputStream);
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

    protected void a(InputStream inputStream) throws XmlPullParserException, IOException {
        ArrayList arrayList = new ArrayList();
        Collection arrayList2 = new ArrayList();
        XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
        newPullParser.setInput(inputStream, "utf-8");
        TrendData r0;
        for (int eventType = newPullParser.getEventType(); 1 != eventType; eventType = newPullParser.next()) {
            String name = newPullParser.getName();
            if (eventType == 2) {
                if ("row".equals(name)) {
                    TrendData trendData = new TrendData();
                    trendData.setType("row");
                    String attributeValue = newPullParser.getAttributeValue(null, "pid");
                    if (!(TextUtils.isEmpty(attributeValue) || attributeValue.length() <= 4)) {
                        attributeValue = attributeValue.substring(4);
                    }
                    trendData.setPid(attributeValue);
                    trendData.setRed(newPullParser.getAttributeValue(null, "red"));
                    trendData.setBlue(newPullParser.getAttributeValue(null, "blue"));
                    trendData.setBalls(newPullParser.getAttributeValue(null, "balls"));
                    trendData.setOes(newPullParser.getAttributeValue(null, "oe"));
                    trendData.setBss(newPullParser.getAttributeValue(null, "bs"));
                    trendData.setOne(newPullParser.getAttributeValue(null, "one"));
                    trendData.setTwo(newPullParser.getAttributeValue(null, "two"));
                    trendData.setThree(newPullParser.getAttributeValue(null, "three"));
                    trendData.setCodes(newPullParser.getAttributeValue(null, "codes"));
                    trendData.setSum(newPullParser.getAttributeValue(null, "sum"));
                    trendData.setSpace(newPullParser.getAttributeValue(null, "space"));
                    trendData.setNum(newPullParser.getAttributeValue(null, "num"));
                    trendData.setTimes(newPullParser.getAttributeValue(null, "times"));
                    trendData.setForm(newPullParser.getAttributeValue(null, "form"));
                    arrayList.add(trendData);
                } else if ("dis".equals(name)) {
                    r0 = new TrendData();
                    r0.setType("dis");
                    r0.setRed(newPullParser.getAttributeValue(null, "red"));
                    r0.setBlue(newPullParser.getAttributeValue(null, "blue"));
                    r0.setBalls(newPullParser.getAttributeValue(null, "balls"));
                    r0.setOne(newPullParser.getAttributeValue(null, "one"));
                    r0.setTwo(newPullParser.getAttributeValue(null, "two"));
                    r0.setThree(newPullParser.getAttributeValue(null, "three"));
                    r0.setNum(newPullParser.getAttributeValue(null, "num"));
                    arrayList2.add(r0);
                } else if ("avg".equals(name)) {
                    r0 = new TrendData();
                    r0.setType("avg");
                    r0.setRed(newPullParser.getAttributeValue(null, "red"));
                    r0.setBlue(newPullParser.getAttributeValue(null, "blue"));
                    r0.setBalls(newPullParser.getAttributeValue(null, "balls"));
                    r0.setOne(newPullParser.getAttributeValue(null, "one"));
                    r0.setTwo(newPullParser.getAttributeValue(null, "two"));
                    r0.setThree(newPullParser.getAttributeValue(null, "three"));
                    r0.setNum(newPullParser.getAttributeValue(null, "num"));
                    arrayList2.add(r0);
                } else if ("mmv".equals(name)) {
                    r0 = new TrendData();
                    r0.setType("mmv");
                    r0.setRed(newPullParser.getAttributeValue(null, "red"));
                    r0.setBlue(newPullParser.getAttributeValue(null, "blue"));
                    r0.setBalls(newPullParser.getAttributeValue(null, "balls"));
                    r0.setOne(newPullParser.getAttributeValue(null, "one"));
                    r0.setTwo(newPullParser.getAttributeValue(null, "two"));
                    r0.setThree(newPullParser.getAttributeValue(null, "three"));
                    r0.setNum(newPullParser.getAttributeValue(null, "num"));
                    arrayList2.add(r0);
                } else if ("mlv".equals(name)) {
                    r0 = new TrendData();
                    r0.setType("mlv");
                    r0.setRed(newPullParser.getAttributeValue(null, "red"));
                    r0.setBlue(newPullParser.getAttributeValue(null, "blue"));
                    r0.setBalls(newPullParser.getAttributeValue(null, "balls"));
                    r0.setOne(newPullParser.getAttributeValue(null, "one"));
                    r0.setTwo(newPullParser.getAttributeValue(null, "two"));
                    r0.setThree(newPullParser.getAttributeValue(null, "three"));
                    r0.setNum(newPullParser.getAttributeValue(null, "num"));
                    arrayList2.add(r0);
                }
            }
        }
        arrayList.addAll(arrayList2);
        mHandler.sendMessage(mHandler.obtainMessage(120, arrayList));
    }


    protected void onDestroy() {
        super.onDestroy();
    }

    public void onSelectedChange(TreeSet<Integer> treeSet, TreeSet<Integer> treeSet2) {

    }
}
