package com.dtcloud.useranalysis.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.dtcloud.useranalysis.utils.LogUtil;

/**
 * �û���Ϊ����
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
		LogUtil.i(TAG, "�û���Ϊ������������");	

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//ճ�Է���
		return START_STICKY;
	}
	
}
