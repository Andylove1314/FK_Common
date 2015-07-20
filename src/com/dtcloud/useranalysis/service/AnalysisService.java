package com.dtcloud.useranalysis.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.dtcloud.useranalysis.utils.LogUtil;

/**
 * 用户行为服务
 * @author fengkun
 *
 */
public class AnalysisService extends Service{

	private String TAG = AnalysisService.class.getName();
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		LogUtil.i(TAG, "用户行为分析服务启动");	

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//粘性服务
		return START_STICKY;
	}
	
}
