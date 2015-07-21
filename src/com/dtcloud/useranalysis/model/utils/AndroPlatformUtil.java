package com.dtcloud.useranalysis.model.utils;

import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Android ϵͳ��Ϣ
 * 
 * @author fengkun
 * 
 */
public class AndroPlatformUtil {

	/**
	 * ��ȡ�豸android�汾
	 * 
	 * @return
	 */
	public static String getAndroRelease() {
		return android.os.Build.VERSION.RELEASE;

	}

	/**
	 * ��ȡ�豸�ͺ�
	 * 
	 * @return
	 */
	public static String getAndroModel() {
		return android.os.Build.MODEL;

	}

	/**
	 * ��ȡapi �汾
	 * 
	 * @return
	 */
	public static String getAPIVersion() {
		return android.os.Build.VERSION.SDK_INT+"";

	}

	/**
	 * ��ȡ�豸imsi
	 * 
	 * @return
	 */
	public static String getDeviceIMSI(Application app) {

		return ((TelephonyManager) app
				.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();

	}

	/**
	 * ��ȡ�豸imei
	 * 
	 * @return
	 */
	public static String getDeviceIMEI(Application app) {
		return ((TelephonyManager) app
				.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();

	}

}
