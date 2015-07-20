package com.dtcloud.useranalysis.model.utils;

import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Android 系统信息
 * 
 * @author fengkun
 * 
 */
public class AndroPlatformUtil {

	/**
	 * 获取设备android版本
	 * 
	 * @return
	 */
	public static String getAndroRelease() {
		return android.os.Build.VERSION.RELEASE;

	}

	/**
	 * 获取设备型号
	 * 
	 * @return
	 */
	public static String getAndroModel() {
		return android.os.Build.MODEL;

	}

	/**
	 * 获取api 版本
	 * 
	 * @return
	 */
	public static String getAPIVersion() {
		return android.os.Build.VERSION.SDK_INT+"";

	}

	/**
	 * 获取设备imsi
	 * 
	 * @return
	 */
	public static String getDeviceIMSI(Application app) {

		return ((TelephonyManager) app
				.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();

	}

	/**
	 * 获取设备imei
	 * 
	 * @return
	 */
	public static String getDeviceIMEI(Application app) {
		return ((TelephonyManager) app
				.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();

	}

}
