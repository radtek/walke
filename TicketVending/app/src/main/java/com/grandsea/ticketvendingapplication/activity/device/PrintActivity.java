package com.grandsea.ticketvendingapplication.activity.device;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.grandsea.ticketvendingapplication.R;
import com.grandsea.ticketvendingapplication.util.BitmapUtil;
import com.telpo.tps550.api.TelpoException;
import com.telpo.tps550.api.printer.UsbThermalPrinter;
import com.telpo.tps550.api.util.StringUtil;
import com.telpo.tps550.api.util.SystemUtil;

import java.util.Hashtable;

public class PrintActivity extends Activity {

    private String printVersion;
    private final int NOPAPER = 3;
    private final int LOWBATTERY = 4;
    private final int PRINTVERSION = 5;
    private final int PRINTBARCODE = 6;
    private final int PRINTQRCODE = 7;
    private final int PRINTPAPERWALK = 8;
    private final int PRINTCONTENT = 9;
    private final int CANCELPROMPT = 10;
    private final int PRINTERR = 11;
    private final int OVERHEAT = 12;
    private final int MAKER = 13;
    private final int PRINTPICTURE = 14;
    private final int NOBLACKBLOCK = 15;

//    private LinearLayout print_text, print_pic;
//    private TextView text_index, pic_index,textPrintVersion;
    MyHandler handler;
//    private EditText editTextLeftDistance,editTextLineDistance,editTextWordFont,editTextPrintGray,
//            editTextBarcode,editTextQrcode,editTextPaperWalk,editTextContent,
//            edittext_maker_search_distance,edittext_maker_walk_distance;
//    private Button buttonBarcodePrint,buttonPaperWalkPrint,buttonContentPrint,
//            buttonGetExampleText,buttonGetZhExampleText,buttonGetFRExampleText,buttonClearText,
//            button_maker,button_print_picture;
    private Button buttonQrcodePrint;
    private TextView textPrintVersion;
    private String Result;
    private Boolean nopaper = false;
    private boolean LowBattery = false;

    public static String barcodeStr;
    public static String qrcodeStr;
    public static int paperWalk;
    public static String printContent;
    private int leftDistance = 0;
    private int lineDistance;
    private int wordFont;
    private int printGray;
    private ProgressDialog progressDialog;
    private final static int MAX_LEFT_DISTANCE = 255;
    ProgressDialog mDialog;
    UsbThermalPrinter mUsbThermalPrinter = new UsbThermalPrinter(PrintActivity.this);
    private String picturePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/111.bmp";
    private EditText editTextQrcode;
    private LinearLayout printLinearLayout;
    private EditText editTextPrintGray;

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case NOPAPER://纸不足
                    noPaperDlg();
                    break;
                case LOWBATTERY://电量不足
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(PrintActivity.this);
                    alertDialog.setTitle(R.string.operation_result);
                    alertDialog.setMessage(getString(R.string.LowBattery));
                    alertDialog.setPositiveButton(getString(R.string.dialog_comfirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    alertDialog.show();
                    break;
                case NOBLACKBLOCK://找不到黑标
                    Toast.makeText(PrintActivity.this, R.string.maker_not_find, Toast.LENGTH_SHORT).show();
                    break;
                case PRINTVERSION://操作失败
                    mDialog.dismiss();
                    if (msg.obj.equals("1")) {
                        textPrintVersion.setText(printVersion);
                    } else {
                        Toast.makeText(PrintActivity.this, R.string.operation_fail, Toast.LENGTH_LONG).show();
                    }
                    break;
                case PRINTBARCODE://
//                    new barcodePrintThread().start();
                    break;
                case PRINTQRCODE:
                    Log.i("123","12333");
                    new qrcodePrintThread().start();
                    break;
                case PRINTPAPERWALK://走纸
//                    new paperWalkPrintThread().start();
                    break;
                case PRINTCONTENT://内容打印
//                    new contentPrintThread().start();
                    break;
                case MAKER:
//                    new MakerThread().start();
                    break;
                case PRINTPICTURE:
//                    new printPicture().start();
                    break;
                case CANCELPROMPT://取消提示
                    Log.i("123","12333CANCELPROMPT");
                    if (progressDialog != null && !PrintActivity.this.isFinishing()) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                    break;
                case OVERHEAT://结果
                    AlertDialog.Builder overHeatDialog = new AlertDialog.Builder(PrintActivity.this);
                    overHeatDialog.setTitle(R.string.operation_result);//结果
                    overHeatDialog.setMessage(getString(R.string.overTemp));//打印过热
                    overHeatDialog.setPositiveButton(getString(R.string.dialog_comfirm),//确定
                            new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    overHeatDialog.show();
                    break;
                default://打印出错
                    Toast.makeText(PrintActivity.this, "Print Error!", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    private void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_print);
        initView();
        handler = new MyHandler();
        IntentFilter pIntentFilter = new IntentFilter();
        pIntentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        pIntentFilter.addAction("android.intent.action.BATTERY_CAPACITY_EVENT");
        registerReceiver(printReceive, pIntentFilter);

        buttonQrcodePrint = (Button) findViewById(R.id.ap_btnPrint);//print_qrcode
        textPrintVersion = (TextView) findViewById(R.id.ap_text);//print_qrcode
        editTextQrcode = (EditText) findViewById(R.id.ap_etQrcode);//print_qrcode
        editTextPrintGray = (EditText) findViewById(R.id.ap_printGray);//print_qrcode
        printLinearLayout = (LinearLayout) findViewById(R.id.ap_printLinearLayout);//print_qrcode
        //TODO 这里为啥错
        if(SystemUtil.getDeviceType() == StringUtil.DeviceModelEnum.TPS900.ordinal()){
            editTextPrintGray.setText("5");
        }
        buttonQrcodePrint.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String exditText = editTextPrintGray.getText().toString();
                if (exditText == null || exditText.length() < 1) {
                    Toast.makeText(PrintActivity.this, getString(R.string.gray_level)//灰度:
                            + getString(R.string.lengthNotEnougth)//长度不足！
                            , Toast.LENGTH_LONG).show();
                    return;
                }
                printGray = Integer.parseInt(exditText);
                if (printGray < 0 || printGray > 7) {
                    Toast.makeText(PrintActivity.this,
                            getString(R.string.outOfGray),//超出浓度范围！
                            Toast.LENGTH_LONG).show();
                    return;
                }
                qrcodeStr = editTextQrcode.getText().toString();
                if (qrcodeStr == null || qrcodeStr.length() == 0) {
                    Toast.makeText(PrintActivity.this, getString(R.string.input_print_data),//请输入打印数据
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (LowBattery == true) { //电量
                    handler.sendMessage(handler.obtainMessage(LOWBATTERY, 1, 0, null));
                } else {
                    if (!nopaper) {
                        progressDialog = ProgressDialog.show(PrintActivity.this, getString(R.string.D_barcode_loading), //二维码加载
                                getString(R.string.generate_barcode_wait));//二维码生成中，请稍候……
                        handler.sendMessage(handler.obtainMessage(PRINTQRCODE, 1, 0, null));
                    } else {
                        Toast.makeText(PrintActivity.this, getString(R.string.ptintInit),//打印机初始化中，请稍后再试
                                Toast.LENGTH_LONG).show();
                    }
                }

            }
        });


        mDialog = new ProgressDialog(PrintActivity.this);
        mDialog.setTitle(R.string.idcard_czz);//操作中
        mDialog.setMessage(getText(R.string.watting));//正在检测驱动版本，请稍候...
        mDialog.setCancelable(false);
        mDialog.show();

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    mUsbThermalPrinter.start(0);
                    mUsbThermalPrinter.reset();
                    printVersion = mUsbThermalPrinter.getVersion();
                } catch (TelpoException e) {
                    e.printStackTrace();
                } finally {
                    if (printVersion != null) {
                        Message message = new Message();
                        message.what = PRINTVERSION;
                        message.obj = "1";
                        handler.sendMessage(message);
                    } else {
                        Message message = new Message();
                        message.what = PRINTVERSION;
                        message.obj = "0";
                        handler.sendMessage(message);
                    }
                }
            }
        }).start();

    }

    /* Called when the application resumes */
    @Override
    protected void onResume() {
        super.onResume();
    }

    private final BroadcastReceiver printReceive = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_NOT_CHARGING);
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
                //TPS390 can not print,while in low battery,whether is charging or not charging
                if(SystemUtil.getDeviceType() == StringUtil.DeviceModelEnum.TPS390.ordinal()){
                    if (level * 5 <= scale) {
                        LowBattery = true;
                    } else {
                        LowBattery = false;
                    }
                }else {
                    if (status != BatteryManager.BATTERY_STATUS_CHARGING) {
                        if (level * 5 <= scale) {
                            LowBattery = true;
                        } else {
                            LowBattery = false;
                        }
                    } else {
                        LowBattery = false;
                    }
                }
            }
            //Only use for TPS550MTK devices
            else if (action.equals("android.intent.action.BATTERY_CAPACITY_EVENT")) {
                int status = intent.getIntExtra("action", 0);
                int level = intent.getIntExtra("level", 0);
                if(status == 0){
                    if(level < 1){
                        LowBattery = true;
                    }else {
                        LowBattery = false;
                    }
                }else {
                    LowBattery = false;
                }
            }
        }
    };

    private void noPaperDlg() {
        AlertDialog.Builder dlg = new AlertDialog.Builder(PrintActivity.this);
        dlg.setTitle(getString(R.string.noPaper));//打印缺纸
        dlg.setMessage(getString(R.string.noPaperNotice));//打印缺纸，请放入纸后重试
        dlg.setCancelable(false);
        dlg.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        dlg.show();
    }

    //二维码打印线程
    private class qrcodePrintThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                mUsbThermalPrinter.reset();
                mUsbThermalPrinter.setGray(printGray);
				Bitmap bmqrcode = CreateCode(qrcodeStr, BarcodeFormat.QR_CODE, 256, 256);
                View inflate = View.inflate(PrintActivity.this, R.layout.printer_ticket, null);
                Bitmap bitmap = BitmapUtil.getViewBitmap(inflate, 300, 170);//inflate   printLinearLayout
                if(bitmap != null){
                    mUsbThermalPrinter.printLogo(bitmap, true);
                }
                mUsbThermalPrinter.addString(qrcodeStr);
                mUsbThermalPrinter.printString();
                mUsbThermalPrinter.walkPaper(20);
            } catch (Exception e) {
                e.printStackTrace();
                Result = e.toString();
                if (Result.equals("com.telpo.tps550.api.printer.NoPaperException")) {
                    nopaper = true;
                } else if (Result.equals("com.telpo.tps550.api.printer.OverHeatException")) {
                    handler.sendMessage(handler.obtainMessage(OVERHEAT, 1, 0, null));
                } else {
                    handler.sendMessage(handler.obtainMessage(PRINTERR, 1, 0, null));
                }
            } finally {
                handler.sendMessage(handler.obtainMessage(CANCELPROMPT, 1, 0, null));
                if (nopaper){
                    handler.sendMessage(handler.obtainMessage(NOPAPER, 1, 0, null));
                    nopaper = false;
                    return;
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (progressDialog != null && !PrintActivity.this.isFinishing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        unregisterReceiver(printReceive);
        mUsbThermalPrinter.stop();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
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

    public Bitmap CreateCode(String str, BarcodeFormat type, int bmpWidth, int bmpHeight) throws WriterException {
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
