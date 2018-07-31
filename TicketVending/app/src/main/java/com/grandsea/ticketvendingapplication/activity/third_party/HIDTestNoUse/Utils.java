package com.grandsea.ticketvendingapplication.activity.third_party.HIDTestNoUse;

public class Utils {
	public static String byteArr2str(byte[] bArr){
		String tmp = "";
		for(byte b:bArr){
			tmp += String.format("%02x", b);
		}
		return tmp;
	}

	public static byte[] str2byteArr(String str){
		byte []bArr = new byte[str.length()>>1];
		for(int i = 0; i < str.length();){
			int bArrIndex = i/2;
			int h = char2int(str.charAt(i++));
			int l = char2int(str.charAt(i++));
			bArr[bArrIndex] = (byte) (h*0x10 + l);
		}
		return bArr;
	}

	private static int char2int(char ch){
		int value = -1;
		if((ch >= '0' && ch <= '9')){
			value = ch - '0';
		} else if (ch >= 'a' && ch <= 'f'){
			value = ch - 'a' + 10;
		} else if (ch >= 'A' && ch <= 'F'){
			value = ch - 'A' + 10;
		}
		return value;
	}
}
