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
 * 日期工具
 * 
 * @author fengkun
 * 
 */
@SuppressLint("SimpleDateFormat")
public class DateUtil {

	private static String TAG = DateUtil.class.getName();

	/** 时间格式 */
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 获取当前日期时间
	 */
	public static String getCurrentDateTime() {
		//当前时间
		String date = null;
		long ld = 0;
		URLConnection uc = null;
		try {
			LogUtil.i(TAG, " 获取网络时间");
			URL url = new URL(Gloabals.BEIJING_TIME);
			uc = url.openConnection();
			// 发出连接
			uc.connect();
			ld = uc.getDate();
		} catch (MalformedURLException e) {
			LogUtil.i(TAG, " 获取网络时间失败");
			e.printStackTrace();
			ld = getDeviceDateTime();
		} catch (IOException e) {
			LogUtil.i(TAG, " 获取网络时间失败");
			e.printStackTrace();
			ld = getDeviceDateTime();
		}
		// 转换为标准时间对象
		Date _date = new Date(ld);
		date = df.format(_date);
		LogUtil.i(TAG, "当前时间>>>"+date);
		return date;
	}
	
	/**
	 * 获取设备时间
	 * @return
	 */
	private static long getDeviceDateTime(){
		LogUtil.i(TAG, " 获取设备时间");
		long ld = System.currentTimeMillis();
		return ld;
	}
}
