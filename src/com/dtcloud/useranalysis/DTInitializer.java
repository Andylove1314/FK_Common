package com.dtcloud.useranalysis;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.dtcloud.useranalysis.model.utils.JsonUtil;
import com.dtcloud.useranalysis.service.AnalysisService;
import com.dtcloud.useranalysis.service.AnalysisTask;
import com.dtcloud.useranalysis.utils.DateUtil;
import com.dtcloud.useranalysis.utils.LogUtil;
import com.dtcloud.useranalysis.utils.NetWorkStateUtil;

/**
 * 用户行为初始化工具
 * 
 * @author fengkun
 * 
 */
public class DTInitializer {

	private static String TAG = DTInitializer.class.getName();
      
	/**
	 * 初始化app信息
	 * @param app
	 */
	public static void init(Application app) {
		DateUtil.getCurrentDateTime();
	}
	
	/**
	 * 初始化组件信息
	 * @param context
	 */
	public static void init(Context context) {
		JsonUtil.getLocationDetail(context);
	}

	/**
	 * 启动用户行为跟踪服务
	 * @param app
	 */
	public static void startAnalysisService(Application app) {

		Intent service = new Intent(app,AnalysisService.class);
		app.startService(service);
	}
	
	/**
	 * 启动发送任务
	 * @param app
	 *        data
	 */
	public static void excuteAnalysisTask(Application app, String data) {

		AnalysisTask task = new AnalysisTask();
		if (data != null && NetWorkStateUtil.isNetworkAvailable(app)) {
			task.execute(Gloabals.URL, data);
		} else {
			LogUtil.i(TAG, "执行失败，没网或者没数据");
		}

	}

	/**
	 * 启动发送任务
	 * @param context
	 *            , data
	 */
	public static void excuteAnalysisTask(Context context, String data) {

		AnalysisTask task = new AnalysisTask();
		if (data != null && NetWorkStateUtil.isNetworkAvailable(context)) {
			task.execute(Gloabals.URL, data);
		} else {
			LogUtil.i(TAG, "执行失败，没网或者没数据");
		}
	}
	
	/**
	 * 初始化登陆信息
	 * @param app
	 */
	public static void initLogined(Application app){
		
	}
	
	/**
	 * 初始化注册信息
	 * @param app
	 */
	public static void initRegisted(Application app){
		
	}
	
}
