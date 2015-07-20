package com.dtcloud.useranalysis.utils;

import android.util.Log;

import com.dtcloud.useranalysis.BuildConfig;

/**
 * 
 * @author fengkun
 * 日志打印工具
 */
public class LogUtil {

	/**是否是调试模式*/
	private static boolean allowedPrint = BuildConfig.DEBUG;
	
	/**
	 * log.i
	 * @param tag
	 * @param content
	 */
	public static void i(String tag, String content){
		if(allowedPrint){
			Log.i(tag, content);
		}
	}
	
	/**
	 * log.d
	 * @param tag
	 * @param content
	 */
	public static void d(String tag, String content){
		if(allowedPrint){
			Log.d(tag, content);
		}
	}
	
	/**
	 * log.v
	 * @param tag
	 * @param content
	 */
	public static void v(String tag, String content){
		if(allowedPrint){
			Log.v(tag, content);
		}
	}
	
	/**
	 * log.e
	 * @param tag
	 * @param content
	 */
	public static void e(String tag, String content){
		if(allowedPrint){
			Log.e(tag, content);
		}
	}
	
	/**
	 * log.w
	 * @param tag
	 * @param content
	 */
	public static void w(String tag, String content){
		if(allowedPrint){
			Log.w(tag, content);
		}
	}
}
