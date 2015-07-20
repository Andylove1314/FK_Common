package com.dtcloud.useranalysis.service;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;

import com.dtcloud.useranalysis.utils.LogUtil;

/**
 * 用户行为分析发送任务
 * @author fengkun
 *
 */
public class AnalysisTask extends AsyncTask<String, Void, String> {

	private static String TAG = AnalysisTask.class.getName();
	/**连接超时时间*/
	private static int TIME_OUT = 10*1000;
	@Override
	protected String doInBackground(String... url_params) {
		LogUtil.i(TAG, "异步执行...");
		String _url = url_params[0];
		LogUtil.i(TAG, "地址>>"+_url);
		String _param = url_params[1];
		LogUtil.i(TAG, "参数>>"+_param);
		String _result = null;
		
		URL url = null;
		HttpURLConnection  urlConn = null;
		InputStream is = null;
		try {
			//上传
			url = new URL(_url);
			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setConnectTimeout(TIME_OUT);
			urlConn.setRequestMethod("POST");
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			urlConn.setUseCaches(false);
			urlConn.setRequestProperty("Accept-Charset", "UTF-8");
			urlConn.setRequestProperty("Content-Type", "text/json");
			urlConn.connect();
			DataOutputStream out = new DataOutputStream(urlConn.getOutputStream());
			out.writeBytes(_param);
			out.flush();
			out.close();
			//返回
			is = urlConn.getInputStream();
			StringBuffer sb = new StringBuffer();
			int ch;
			while ((ch = is.read()) != -1) {
				sb.append((char) ch);
			}
			_result = sb.toString();
			is.close();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return _result;
	}
	
	@Override
	protected void onPreExecute() {
		LogUtil.i(TAG, "启动任务...");
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(String result) {
		LogUtil.i(TAG, "上传行为反馈>>"+result);
		super.onPostExecute(result);
	}
}
