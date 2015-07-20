package com.dtcloud.useranalysis.utils;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * ÍøÂç×´Ì¬¹¤¾ß
 * @author fengkun
 *
 */
public class NetWorkStateUtil {
	/**
	 * Application ÍøÂç×´Ì¬ÅÐ¶Ï
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
 * Context ÍøÂç×´Ì¬ÅÐ¶Ï
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