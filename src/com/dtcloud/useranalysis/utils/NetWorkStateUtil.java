package com.dtcloud.useranalysis.utils;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * ����״̬����
 * @author fengkun
 *
 */
public class NetWorkStateUtil {
	/**
	 * Application ����״̬�ж�
	 * @param app
	 * @return
	 */
	public static boolean isNetworkAvailable(Application app) {
		
		ConnectivityManager connectivityManager = (ConnectivityManager) app
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeInfo = connectivityManager.getActiveNetworkInfo();
		if(activeInfo!=null){
			return true;
		}
		return false;
	}

/**
 * Context ����״̬�ж�
 * @param context
 * @return
 */
public static boolean isNetworkAvailable(Context context) {
	
	ConnectivityManager connectivityManager = (ConnectivityManager) context
			.getSystemService(Context.CONNECTIVITY_SERVICE);
	NetworkInfo activeInfo = connectivityManager.getActiveNetworkInfo();
	if(activeInfo!=null){
		return true;
	}
	return false;
}
}