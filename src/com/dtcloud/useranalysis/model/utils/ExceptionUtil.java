package com.dtcloud.useranalysis.model.utils;

import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Application;
import android.content.Context;

import com.dtcloud.useranalysis.DTInitializer;
import com.dtcloud.useranalysis.utils.LogUtil;

/**
 * 异常处理工具
 * 
 * @author fengkun
 * 
 */
public class ExceptionUtil extends Exception implements
		UncaughtExceptionHandler {

	private static String TAG = ExceptionUtil.class.getName();
	private static final long serialVersionUID = 1L;
	private Application app;
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	/**异常信息*/
	public static String crashReport;

	public ExceptionUtil(Application app) {
		this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		this.app = app;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {

		if (mDefaultHandler != null && !handleException(ex)) {
			mDefaultHandler.uncaughtException(thread, ex);
		}
	}

	private boolean handleException(Throwable ex) {

		if (ex == null) {
			return false;
		}

		final Context context = app.getApplicationContext();
		if (context == null) {
			return false;
		}

		crashReport = getCrashReport(ex);
		post();
		
		return true;
	}

	/**
	 * 发送异常报告
	 */
	private void post() {
		
		LogUtil.i(TAG, "异常信息>>"+crashReport);
		LogUtil.i(TAG, JsonUtil.getExceptionDetail(app));
		DTInitializer.excuteAnalysisTask(app, JsonUtil.getExceptionDetail(app));

	}

	/**
	 * 捕捉app异常信息
	 * 
	 * @param ex
	 * @return
	 */
	private String getCrashReport(Throwable ex) {

		StringBuffer exceptionStr = new StringBuffer();
		exceptionStr.append("Exception: " + ex.getMessage() + "\n");
		StackTraceElement[] elements = ex.getStackTrace();
		for (int i = 0; i < elements.length; i++) {
			exceptionStr.append(elements[i].toString() + "\n");
		}
		return exceptionStr.toString();
	}

}
