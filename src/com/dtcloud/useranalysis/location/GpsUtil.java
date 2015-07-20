package com.dtcloud.useranalysis.location;

import com.dtcloud.useranalysis.utils.LogUtil;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;

/**
 * gps工具
 * 
 * @author fengkun
 * 
 */
public class GpsUtil {

	/**
	 * 获取gps状态
	 * 
	 * @param context
	 * @return
	 */
	public static boolean getGpsState(Context context) {
		ContentResolver resolver = context.getContentResolver();
		boolean open = Settings.Secure.isLocationProviderEnabled(resolver,
				LocationManager.GPS_PROVIDER);
		LogUtil.i("getGpsState:", open + "");
		return open;
	}

	/**
	 * 打开gps
	 * @param context
	 */
	public static void toggleGps(Context context) {

		Intent GPSIntent = new Intent();
		GPSIntent.setClassName("com.android.settings",
				"com.android.settings.widget.SettingsAppWidgetProvider");
		GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
		GPSIntent.setData(Uri.parse("custom:3"));
		try {
			PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
		} catch (CanceledException e) {
			e.printStackTrace();
		}

	}
}
