package com.grandsea.ticketvendingapplication.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.grandsea.ticketvendingapplication.R;
import com.grandsea.ticketvendingapplication.base.BaseActivity;
import com.grandsea.ticketvendingapplication.model.bean.PrintfInfo;
import com.grandsea.ticketvendingapplication.util.DateUtil;
import com.grandsea.ticketvendingapplication.util.LogUtil;

import java.util.Date;
import java.util.Hashtable;

/**
 * Created by Grandsea09 on 2017/10/11.
 */

public class TicketView extends LinearLayout {
    private ImageView ivQrCode;
    private TextView tvTitle, tvWay, tvDate, tvGetOnStation, tvTakeOffStation, tvTime, tvCarNo, tvQrCode, tvSitNumber;
    private TextView tvTicketType, tvCheckEnter, tvCheckEnterNumber, tvName, tvPhone,tvCustomService, tvTerminalNo, tvPrintTime;
    private TextView tvTicketPrice;

    public TicketView(Context context) {
        this(context, null);
    }

    public TicketView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TicketView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_ticket, this);
        ivQrCode = ((ImageView) findViewById(R.id.vt_ivQrCode));//
        tvTitle = ((TextView) findViewById(R.id.vt_title));//车票标题-- 珠海机场快线乘车凭证
        tvWay = ((TextView) findViewById(R.id.vt_way));//线路
        tvDate = ((TextView) findViewById(R.id.vt_date));//日期：2017-10-02 (周一)
        tvTime = ((TextView) findViewById(R.id.vt_time));//车票时间
        tvGetOnStation = ((TextView) findViewById(R.id.vt_getOnStation));//上车点
        tvTakeOffStation = ((TextView) findViewById(R.id.vt_takeOffStation));//下车点
        tvCarNo = ((TextView) findViewById(R.id.vt_carNo));//车牌号码
        tvQrCode = ((TextView) findViewById(R.id.vt_tvQrCode));//验票码 （二维码字符串）
        tvSitNumber = ((TextView) findViewById(R.id.vt_sitNumber));//座位号
        tvTicketType = ((TextView) findViewById(R.id.vt_ticketType));//票型
        tvTicketPrice = ((TextView) findViewById(R.id.vt_ticketPrice));//票价
//        tvTicketPrice.setVisibility(GONE);// TODO: 2017/10/21 有返回后显示
        tvCheckEnter = ((TextView) findViewById(R.id.vt_checkEnter));//检票口（暂时不显示）
        tvCheckEnterNumber = ((TextView) findViewById(R.id.vt_checkEnterNumber));//卡位号（暂时不显示）
        tvName = ((TextView) findViewById(R.id.vt_name));// 乘车人
        tvPhone = ((TextView) findViewById(R.id.vt_phone));// 客服电话 （暂时不显示）
        tvCustomService = ((TextView) findViewById(R.id.vt_customService));// 客服电话 （暂时不显示）
        tvTerminalNo = ((TextView) findViewById(R.id.vt_terminalNo));//终端号
        tvPrintTime = ((TextView) findViewById(R.id.vt_printTime));//打印时间
    }

    public ImageView getIvQrCode() {
        return ivQrCode;
    }

    public void setViewByData(PrintfInfo.ElectTicketsBean ticketInfo) {
        if (null == ticketInfo)
            return;
        String qrcodeStr = ticketInfo.getQrCode();
        qrCode(qrcodeStr);
        tvTitle.setText(""+ticketInfo.getBusPartnerName()+"乘车凭证");//珠海机场快线乘车凭证
        tvWay.setText("线路: \n"+ticketInfo.getGetOnStation()+"--"+ticketInfo.getTakeOffStation());//线路
        tvDate.setText(""+ticketInfo.getTicketDate()+"　"+ticketInfo.getGetOnTime().substring(0,5));//车票日期，3空格
        tvGetOnStation.setText("上车: "+ticketInfo.getGetOnStation());//出发站
        tvTakeOffStation.setText("下车: "+ticketInfo.getTakeOffStation());//抵达站
        tvTime.setText(""+ticketInfo.getGetOnTime());//开车时间
        tvCarNo.setText(""+ticketInfo.getPlateNum());//车牌号码
        String text1 = "验票码:" + ticketInfo.getCode();
        tvQrCode.setText(text1);//验票码
        tvSitNumber.setText(""+ticketInfo.getSeat());//座位号
        ticketType(ticketInfo.getType());//车票类型
        tvTicketPrice.setText(""+ticketInfo.getPrice());// TODO: 2017/10/21 有返回后赋值
//        tvCheckEnter.setText(""+ticketInfo.);//检票口
//        tvCheckEnterNumber.setText(""+ticketInfo.get);//卡位号
        String name = ticketInfo.getName().trim();
        String trim = ticketInfo.getIdCard().trim();
        String text = "" + name+ "(" + trim + ")";
        LogUtil.i("text----------------->","tvName  text:"+text);
        //tvName.setText(text);//购票(联系)人
        tvSpannale(tvName,text,name);
        tvPhone.setText(""+ticketInfo.getPhone());//购票(联系)人手机号
        tvCustomService.setText("客服电话: "+ticketInfo.getBusPartnerPhone());//
        tvTerminalNo.setText("终端机号: "+ ((BaseActivity) getContext()).getApp().getSnCode());//
        tvPrintTime.setText("打印时间: "+DateUtil.formatYYYYMMDDHHMMSS(new Date()));//打印日期

    }

    private void ticketType(Integer type) {
        if (null==type)return;
        if (1==type)//1
            tvTicketType.setText("成人");
        else if (2==type)
            tvTicketType.setText("儿童");
        else if (3==type)
            tvTicketType.setText("学生");
        else if (4==type)
            tvTicketType.setText("免票儿童");
    }

    /**
     * 使用SpannableStringBuilder设置样式——字体大小
     */
    private void tvSpannale(TextView textView,String strAll,String first) {
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append(strAll);
        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(16);
        spannableString.setSpan(absoluteSizeSpan, first.length(), strAll.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        textView.setText(spannableString);
    }

    private void qrCode(String qrcodeStr) {
        if (TextUtils.isEmpty(qrcodeStr))
            return;
        try {
            Bitmap bmqrcode = createCode(qrcodeStr, BarcodeFormat.QR_CODE, 256, 256);
            ivQrCode.setImageBitmap(bmqrcode);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成条码
     *
     * @param str
     *            条码内容
     * @param type
     *            条码类型： AZTEC, CODABAR, CODE_39, CODE_93, CODE_128, DATA_MATRIX,
     *            EAN_8, EAN_13, ITF, MAXICODE, PDF_417, QR_CODE, RSS_14,
     *            RSS_EXPANDED, UPC_A, UPC_E, UPC_EAN_EXTENSION;
     * @param bmpWidth
     *            生成位图宽,宽不能大于384，不然大于打印纸宽度
     * @param bmpHeight
     *            生成位图高，8的倍数
     */

    public Bitmap createCode(String str, BarcodeFormat type, int bmpWidth, int bmpHeight) throws WriterException {
        Hashtable<EncodeHintType,String> mHashtable = new Hashtable<EncodeHintType,String>();
        mHashtable.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 生成二维矩阵,编码时要指定大小,不要生成了图片以后再进行缩放,以防模糊导致识别失败
        BitMatrix matrix = new MultiFormatWriter().encode(str, type, bmpWidth, bmpHeight, mHashtable);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        // 二维矩阵转为一维像素数组（一直横着排）
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                } else {
                    pixels[y * width + x] = 0xffffffff;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // 通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

}
