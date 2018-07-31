//package com.grandsea.ticketvendingapplication.activity.third_party.HIDTestNoUse;
//
//import android.app.Activity;
//import android.app.PendingIntent;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager.NameNotFoundException;
//import android.hardware.usb.UsbDevice;
//import android.hardware.usb.UsbManager;
//import android.os.Bundle;
//import android.os.SystemClock;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.Spinner;
//import android.widget.TextView;
//
//import com.Routon.iDRHIDLib.iDRHIDDev;
//import com.common.pos.api.util.posutil.PosUtil;
//import com.grandsea.ticketvendingapplication.R;
//
//public class HIDTestActivity extends Activity implements View.OnClickListener {
//
//	private static final String TAG = "HIDTestActivity";
//
//	private Button mBtnReadB;
//	private Button mBtnReadA;
//	private Button mBtnTestULCard;
//	private Button mBtnTestIsConnected;
//	private Button mBtnCPUCardActive, mBtnCPUCardDeactive, mBtnCPUCardSendAPDU;
//	private Button mBtnShowIDCardPic;
//	private TextView mTextView;
//	private Spinner mSpinnerAPDUCMD;
//	private UsbManager mUsbManager;
//	private UsbDevice mDevice;
//	private iDRHIDDev mHIDDev;
//	private TextView mTextCardInfo;
//	private ImageView mImageCardInfo;
//	private boolean mSecondCard;
//	private String mTypeACardType;
//	private String mTypeACardNo;
//	private String mTypeACardData;
//	private String mMULCardTestData;
//	private ShowIDCardPicDialog mShowIDCardPicDialog;
//	private iDRHIDDev.SecondIDInfo sIDInfo;
//
//	private static final String ACTION_USB_PERMISSION = "com.Routon.HIDTest.USB_PERMISSION";
//
//	/** Called when the activity is first created. */
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.third_idcard_main);
//
//		PosUtil.setRfidPower(PosUtil.RFID_POWER_ON);
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		mBtnReadB = (Button) findViewById(R.id.btnReadB);
//		mBtnReadB.setOnClickListener(this);
//
//		mBtnReadA = (Button) findViewById(R.id.btnFindTypeA);
//		mBtnReadA.setOnClickListener(this);
//
//		mBtnTestULCard = (Button) findViewById(R.id.btnTestULCard);
//		mBtnTestULCard.setOnClickListener(this);
//
//		mBtnTestIsConnected = (Button) findViewById(R.id.btnIsConnectedTest);
//		mBtnTestIsConnected.setOnClickListener(this);
//
//		mBtnCPUCardActive = (Button) findViewById(R.id.btnCPUCardActive);
//		mBtnCPUCardActive.setOnClickListener(this);
//
//		mBtnCPUCardDeactive = (Button) findViewById(R.id.btnCPUCardDeactive);
//		mBtnCPUCardDeactive.setOnClickListener(this);
//
//		mBtnCPUCardSendAPDU = (Button) findViewById(R.id.btnCPUCardSendAPDU);
//		mBtnCPUCardSendAPDU.setOnClickListener(this);
//
//		// card info view
//		mTextCardInfo = (TextView) findViewById(R.id.cardTextInfo);
//		mImageCardInfo = (ImageView) findViewById(R.id.cardImageInfo);
//
//		mBtnShowIDCardPic = (Button) findViewById(R.id.btnShowIDCardPic);
//		mBtnShowIDCardPic.setOnClickListener(this);
//
//		mTextView = (TextView) findViewById(R.id.textView1);
//		mTextView.setText("身份证阅读程序：");
//
//		TextView mTextVer = (TextView) findViewById(R.id.TextVersion);
//		mTextVer.setText("DEMO 版本:" + Version.getDEMOVersion(this) + "\nSDK 版本:" + Version.getSDKVersion());
//
//		mSpinnerAPDUCMD = (Spinner) findViewById(R.id.spinnerApduCMD);
//
//		mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
//
//		mDevice = null;
//
//		mHIDDev = new iDRHIDDev();
//		// initialize card info
//		mSecondCard = true;
//		clearSecondCardInfo();
//		clearTypeACardInfo();
//
//		// listen for new devices
//		IntentFilter filter = new IntentFilter();
//		filter.addAction(ACTION_USB_PERMISSION);
//		filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
//		registerReceiver(mUsbReceiver, filter);
//
//		// 检查USB设备是否插入
//		for (UsbDevice device : mUsbManager.getDeviceList().values()) {
//
//			Log.e(TAG, "vid: " + device.getVendorId() + " pid:" + device.getProductId());
//			if (device.getVendorId() == 1061 && device.getProductId() == 33113) {
//				Intent intent = new Intent(ACTION_USB_PERMISSION);
//				PendingIntent mPermissionIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
//				mUsbManager.requestPermission(device, mPermissionIntent);
//				Log.d(TAG, "usb device: checked");
//				appendLog("发现读卡设备");
//			}
//		}
//
//		mShowIDCardPicDialog = new ShowIDCardPicDialog(this);
//	}
//
//	private BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			String action = intent.getAction();
//
//			if (ACTION_USB_PERMISSION.equals(action)) {
//				synchronized (this) {
//					UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
//
//					if (mDevice != null) {
//						mHIDDev.closeDevice();
//						mDevice = null;
//					}
//
//					if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
//						if (device != null) {
//							// call method to set up device communication
//
//							final int ret = mHIDDev.openDevice(mUsbManager, device);
//							Log.i(TAG, "open device:" + ret);
//							if (ret == 0) {
//								mDevice = device;
//								appendLog("usb device: 已授权");
//							} else {
//								mDevice = null;
//								appendLog("usb device: 授权失败");
//							}
//						}
//					} else {
//						Log.d(TAG, "permission denied for device " + device);
//						appendLog("usb device: 未授权");
//						finish();
//					}
//				}
//			}
//		}
//	};
//
//	private void invalidate() {
//		String info = "";
//		if (mSecondCard) {
//			info = sIDInfo.getFormatIDCardText();
//		} else {
//			info = mTypeACardType + mTypeACardNo;
//		}
//		if (mMULCardTestData != null) {
//			info += "\n" + mMULCardTestData;
//			mMULCardTestData = null;
//		}
//
//		mTextCardInfo.setText(info);
//		if (sIDInfo != null) {
//			mImageCardInfo.setImageBitmap(sIDInfo.photo);
//		}
//
//	}
//
//	@Override
//	public void onDestroy() {
//		if (mDevice != null) {
//			mHIDDev.closeDevice();
//			mDevice = null;
//		}
//		unregisterReceiver(mUsbReceiver);
//		PosUtil.setRfidPower(PosUtil.RFID_POWER_OFF);
//		super.onDestroy();
//	}
//
//	private void appendLog(String log) {
//		log = mTextView.getText() + "\n" + log;
//
//		mTextView.setText(log);
//	}
//
//	private void clearSecondCardInfo() {
//		mTextCardInfo.setText("");
//		mImageCardInfo.setImageBitmap(null);
//	}
//
//	private void clearTypeACardInfo() {
//		mTypeACardType = "卡类型：";
//		mTypeACardNo = "\n卡序列号：";
//		mTypeACardData = "\n第３区第１块数据：";
//	}
//
//	public void onClick(View v) {
//		if (v == mBtnReadB) {
//			mTextView.setText("读二代证：");
//			mSecondCard = true;
//			clearSecondCardInfo();
//
//			if (mDevice == null) {
//				appendLog("请插入读卡器！");
//				return;
//			}
//
//			int ret;
//
//			// 读安全模块的状态
//			ret = mHIDDev.GetSamStaus();
//			if (ret < 0) {
//				appendLog("读卡器未准备好！");
//				return;
//			}
//
//			iDRHIDDev.SamIDInfo samIDInfo = mHIDDev.new SamIDInfo();
//
//			// 读安全模块号
//			ret = mHIDDev.GetSamId(samIDInfo);
//			appendLog("samid: " + samIDInfo.samID);
//
//			// 找卡
//			ret = mHIDDev.Authenticate();
//
//			if (ret >= 0) // 找到卡
//			{
//				// 读卡
//				sIDInfo = mHIDDev.new SecondIDInfo();
//				byte[] fingerPrint = new byte[1024];
//
//				// ret = mHIDDev.ReadBaseMsg(sIDInfo);
//				ret = mHIDDev.ReadBaseFPMsg(sIDInfo, fingerPrint);
//
//				if (ret < 0) {
//					appendLog("读卡失败：");
//					return;
//				}
//
//				appendLog(sIDInfo.fingerPrint == 1024 ? "有指纹" : "无指纹");
//
//				// 设置蜂鸣器和LED灯
//				ret = mHIDDev.BeepLed(true, true, 500);
//
//				// 刷新显示
//				invalidate();
//
//			} else // 未找到卡
//			{
//				iDRHIDDev.MoreAddrInfo mAddr = mHIDDev.new MoreAddrInfo();
//
//				// 通过读追加地址来判断卡是否在机具上。
//				ret = mHIDDev.GetNewAppMsg(mAddr);
//
//				if (ret < 0) // 机具上没有放卡
//					appendLog("请放卡：");
//				else // 机具上的卡已读过一次
//					appendLog("请重新放卡：");
//			}
//
//			// 读卡号， 注意不要放在读身份证信息前面，否则会读身份证信息失败
//			byte data[] = new byte[32];
//			int www = mHIDDev.getIDCardCID(data);
//			if (www < 0) {
//				appendLog("读卡号失败：" + www);
//			} else {
//				String cardID = String.format("%s %02x%02x%02x%02x%02x%02x%02x%02x", "卡体号：", data[0], data[1], data[2],
//						data[3], data[4], data[5], data[6], data[7]);
//				appendLog(cardID);
//			}
//
//		} else if (v == mBtnReadA) {
//			mTextView.setText("读TypeA卡：");
//			mSecondCard = false;
//			clearTypeACardInfo();
//
//			if (mDevice == null) {
//				appendLog("请插入读卡器！");
//				return;
//			}
//
//			int ret;
//
//			// 找A卡
//			ret = mHIDDev.typeAFindCard();
//			if (ret < 0) {
//				appendLog("找卡失败：" + ret);
//				return;
//			}
//
//			if (ret == 1) {
//				mTypeACardType += "Mifare one s50";
//			} else if (ret == 2) {
//				mTypeACardType += "CPU card";
//			} else if (ret == 3) {
//				mTypeACardType += "Mifare one s70";
//			} else if (ret == 4) {
//				mTypeACardType += "Mifare UltraLight";
//			} else {
//				mTypeACardType = "卡类型： " + String.format("0x%02x", ret);
//			}
//
//			byte[] data = new byte[16];
//			// 读卡号
//			int cardNoLen = mHIDDev.typeAReadCID(data);
//			if (cardNoLen < 0) {
//				appendLog("读卡号失败：" + cardNoLen);
//				return;
//			}
//
//			mTypeACardNo = "\n卡号：";
//			for (int i = 0; i < cardNoLen; i++) {
//				mTypeACardNo += String.format("%02x", data[i]);
//			}
//
//			// // M1卡 读写数据
//			// int i;
//			// byte sectorNo = 3;
//			// byte blockNo = 1;
//			// byte encyType = 0x60;
//			// byte[] encyKey60 = {-1, -1, -1, -1, -1, -1};
//			//
//			// // 写数据
//			// for(i=0; i<16; i++)
//			// data[i] = (byte)i;
//			//
//			// ret = mHIDDev.typeAWriteBlock(sectorNo, blockNo, encyType,
//			// encyKey60, data);
//			// appendLog("typeAWriteBlock: " + ret);
//			//
//			// // 读数据
//			// ret = mHIDDev.typeAReadBlock(sectorNo, blockNo, encyType,
//			// encyKey60, data);
//			// if(ret < 0)
//			// {
//			// appendLog("读块数据失败：" + ret);
//			// return;
//			// }
//			//
//			// mTypeACardData = "\n3区1块数据：\n";
//			// for(i=0; i<16; i++)
//			// mTypeACardData += data[i] + ((i+1<16)?",":" ");
//
//			// 设置蜂鸣器和LED灯
//			ret = mHIDDev.BeepLed(true, true, 100);
//			SystemClock.sleep(100);
//			mHIDDev.BeepLed(true, true, 100);
//
//			// 刷新显示
//			invalidate();
//		} else if (v == mBtnTestULCard) {
//
//			mMULCardTestData = "\nTest mifare UL card read write.\n";
//
//			byte[] writeData = { (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04 };
//			int ret = mHIDDev.writeMifareUL((byte) 0x19, writeData);
//			if (ret == 0) {
//				mMULCardTestData += "write success!\n";
//			}
//
//			byte[] readData = new byte[32];
//			ret = mHIDDev.readMifareUL((byte) 0x16, readData);
//			if (ret > 0) {
//				String byteStr = "";
//				for (int i = 0; i < ret; i++) {
//					byteStr += String.format("%02x.", readData[i]);
//				}
//				mMULCardTestData += "read data:\n" + byteStr + "\n";
//			} else {
//				mMULCardTestData += "read failed\n";
//			}
//
//			invalidate();
//
//		} else if (v == mBtnTestIsConnected) {
//			boolean isConnected = mHIDDev.isConnected();
//			if (isConnected) {
//				appendLog("isConnected");
//			} else {
//				appendLog("isDisconnected");
//			}
//		} else if (v == mBtnCPUCardActive) {
//			int ret = mHIDDev.CPUCardActive();
//			appendLog("激活CPU卡：" + ret);
//		} else if (v == mBtnCPUCardDeactive) {
//			int ret = mHIDDev.CPUCardDeactive();
//			appendLog("CPU卡下电：" + ret);
//		} else if (v == mBtnCPUCardSendAPDU) {
//			String cmd = (String) mSpinnerAPDUCMD.getSelectedItem();
//			byte[] APDU = Utils.str2byteArr(cmd);
//
//			String cmdStr = "send:";
//			for (int i = 0; i < APDU.length; i++) {
//				cmdStr += String.format("%02x", APDU[i]);
//			}
//			Log.e(TAG, cmdStr);
//
//			byte[] data = new byte[1024];
//			int ret = mHIDDev.CPUCardAPDU(APDU, data);
//			appendLog("发送APDU：" + ret);
//
//			String str = "recv:";
//			for (int i = 0; i < ret; i++) {
//				str += String.format("%02x", data[i]);
//			}
//			Log.e(TAG, str);
//		} else if (v == mBtnShowIDCardPic) {
//			// if (sIDInfo != null) {
//			// Bitmap front = sIDInfo.getIDCardFrontPic(this);
//			// Bitmap back = sIDInfo.getIDCardBackPic(this);
//			// mShowIDCardPicDialog.setBitmap(front, back);
//			// mShowIDCardPicDialog.show();
//			// }
//
//		}
//	}
//
//	@Override
//	protected void onStart() {
//		super.onStart();
//		try {
//			PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
//			Log.i(TAG, "iDRHIDDev Version:" + iDRHIDDev.version + "  Demo Version:" + info.versionName);
//		} catch (NameNotFoundException e) {
//			e.printStackTrace();
//		}
//		super.onStart();
//	}
//}