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
 * �û���Ϊ��ʼ������
 * 
 * @author fengkun
 * 
 */
public class DTInitializer {

	private static String TAG = DTInitializer.class.getName();
      
	/**
	 * ��ʼ��app��Ϣ
	 * @param app
	 */
	public static void init(Application app) {
		DateUtil.getCurrentDateTime();
	}
	
	/**
	 * ��ʼ�������Ϣ
	 * @param context
	 */
	public static void init(Context context) {
		JsonUtil.getLocationDetail(context);
	}

	/**
	 * �����û���Ϊ���ٷ���
	 * @param app
	 */
	public static void startAnalysisService(Application app) {

		Intent service = new Intent(app,AnalysisService.class);
		app.startService(service);
	}
	
	/**
	 * ������������
	 * @param app
	 *        data
	 */
	public static void excuteAnalysisTask(Application app, String data) {

		AnalysisTask task = new AnalysisTask();
		if (data != null && NetWorkStateUtil.isNetworkAvailable(app)) {
			task.execute(Gloabals.URL, data);
		} else {
			LogUtil.i(TAG, "ִ��ʧ�ܣ�û������û����");
		}

	}

	/**
	 * ������������
	 * @param context
	 *            , data
	 */
	public static void excuteAnalysisTask(Context context, String data) {

		AnalysisTask task = new AnalysisTask();
		if (data != null && NetWorkStateUtil.isNetworkAvailable(context)) {
			task.execute(Gloabals.URL, data);
		} else {
			LogUtil.i(TAG, "ִ��ʧ�ܣ�û������û����");
		}
	}
	
	/**
	 * ��ʼ����½��Ϣ
	 * @param app
	 */
	public static void initLogined(Application app){
		
	}
	
	/**
	 * ��ʼ��ע����Ϣ
	 * @param app
	 */
	public static void initRegisted(Application app){
		
	}
	
}
