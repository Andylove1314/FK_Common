package com.dtcloud.useranalysis.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;

import com.dtcloud.useranalysis.Gloabals;

/**
 * ���ڹ���
 * 
 * @author fengkun
 * 
 */
@SuppressLint("SimpleDateFormat")
public class DateUtil {

	private static String TAG = DateUtil.class.getName();

	/** ʱ���ʽ */
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * ��ȡ��ǰ����ʱ��
	 */
	public static String getCurrentDateTime() {
		//��ǰʱ��
		String date = null;
		long ld = 0;
		URLConnection uc = null;
		try {
			LogUtil.i(TAG, " ��ȡ����ʱ��");
			URL url = new URL(Gloabals.BEIJING_TIME);
			uc = url.openConnection();
			// ��������
			uc.connect();
			ld = uc.getDate();
		} catch (MalformedURLException e) {
			LogUtil.i(TAG, " ��ȡ����ʱ��ʧ��");
			e.printStackTrace();
			ld = getDeviceDateTime();
		} catch (IOException e) {
			LogUtil.i(TAG, " ��ȡ����ʱ��ʧ��");
			e.printStackTrace();
			ld = getDeviceDateTime();
		}
		// ת��Ϊ��׼ʱ�����
		Date _date = new Date(ld);
		date = df.format(_date);
		LogUtil.i(TAG, "��ǰʱ��>>>"+date);
		return date;
	}
	
	/**
	 * ��ȡ�豸ʱ��
	 * @return
	 */
	private static long getDeviceDateTime(){
		LogUtil.i(TAG, " ��ȡ�豸ʱ��");
		long ld = System.currentTimeMillis();
		return ld;
	}
}
