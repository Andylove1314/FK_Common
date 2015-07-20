package com.dtcloud.useranalysis.location;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * 异步定位
 * 
 * @author fengkun
 */
public class LocationAsync implements LocationListener, AMapLocationListener {

	public static final String LATITUDE = "lat";
	public static final String ADDR = "addr";
	public static final String LONGTITUDE = "lon";
	public static final String TYPE = "coordType";
	public static final String GPS = "1";
	public static final String AUTONAVI = "2";
	public static final String BAIDU = "3";

	/** 上下文 */
	private Context mContext;
	/** 定位结果处理handler */
	private LocationHandler mHandler = new LocationHandler(this);

	/** google基站定位 */
	private GoogleThread googleThread;

	/** 高德定位 */
	private LocationManagerProxy mAMapLocManager = null;

	/** 取消标志 */
	private boolean mCancel = false;

	/** 定位超时时间 */
	private long mOutTime = 120 * 1000;

	/** 当前正在定位标志 */
	private boolean running = false;

	/** 定位方式延时切换 */
	private static final int DELAY = 30 * 1000;

	/** handler 更新UIThread */
	private static final int LOCATION_COMPLETE = 1;
	private static final int LOCATION_ERROR = -1;

	/** 定位接口集合 */
	private Set<OnLocationListener> mOnLocationListener = new HashSet<OnLocationListener>();

	/**
	 * 构造
	 * 
	 * @param context
	 */
	public LocationAsync(Context context) {
		mContext = context;
	}

	/**
	 * 获取定位信息
	 * 
	 * @param listener
	 */
	public void getLocation(OnLocationListener listener) {
		addOnLocationListener(listener);
		if (running) {
			return;
		}
		running = true;
		mCancel = false;

		try {
			// framework gps
			startGpsLocation();
			// 高德
			startGaoDeLocation();
		} catch (Exception e) {
			e.printStackTrace();
		}

		ScheduledExecutorService exe = Executors
				.newSingleThreadScheduledExecutor();
		exe.schedule(new Runnable() {
			@Override
			public void run() {
				Exception e = new Exception("超时");
				sendMessage(LOCATION_ERROR, e);
				remove();
			}
		}, mOutTime, TimeUnit.MILLISECONDS);
	}

	/**
	 * 开启gps定位
	 */
	private void startGpsLocation() {

		LocationManager locMgr = (LocationManager) mContext
				.getSystemService(Context.LOCATION_SERVICE);
		locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60 * 1000,
				100, this);

	}

	/**
	 * 开启高德定位
	 */
	private void startGaoDeLocation() {

		mAMapLocManager = LocationManagerProxy.getInstance(mContext);
		/*
		 * mAMapLocManager.setGpsEnable(false);//
		 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
		 * API定位采用GPS和网络混合定位方式
		 * ，第一个参数是定位provider，第二个参数时间最短是5000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
		 */
		mAMapLocManager.setGpsEnable(true);
		mAMapLocManager.requestLocationData(LocationProviderProxy.AMapNetwork,
				5000, 10, this);
	}

	/**
	 * google基站定位
	 * 
	 * @author fengkun
	 * 
	 */
	private class GoogleThread extends Thread {

		private boolean stopFlag;

		private NetWorkLocation net;

		@Override
		public void run() {
			net = new NetWorkLocation();
			Location location = null;
			try {
				location = net.getLocation(mContext);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (location != null && !stopFlag) {
				HashMap<String, String> rs = new HashMap<String, String>();
				rs.put(LONGTITUDE, get(location.getLongitude()));
				rs.put(LATITUDE, get(location.getLatitude()));
				sendMessage(LOCATION_COMPLETE, rs);
			}
		}

		/**
		 * 取消定位请求
		 */
		public void stopThread() {
			stopFlag = true;
			if (net != null) {
				net.stop();
			}
		}
	}

	/**
	 * 定位处理handler
	 * 
	 * @author fengkun
	 * 
	 */
	private class LocationHandler extends Handler {

		private LocationAsync mLocation;

		private LocationHandler(LocationAsync locationAsync) {
			this.mLocation = locationAsync;
		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case LOCATION_COMPLETE:
				if (null != mOnLocationListener && !mCancel) {
					for (OnLocationListener listener : mOnLocationListener) {
						if (listener != null) {
							listener.onLocationComplete(mLocation, msg.obj);
						}
					}
				}
				removeImp();
				break;
			case LOCATION_ERROR:
				if (null != mOnLocationListener) {
					Exception e = new Exception("未取得定位信息，请检查GPS及网络状态！");
					for (OnLocationListener listener : mOnLocationListener) {
						if (listener != null) {
							listener.onLocationError(mLocation, e);

						}
					}
				}
				removeImp();
				break;
			}
		}
	}

	/**
	 * 发送定位信息
	 * 
	 * @param what
	 * @param obj
	 */
	private void sendMessage(int what, Object obj) {
		Message msg = mHandler.obtainMessage(what, obj);
		mHandler.sendMessage(msg);
	}

	/**
	 * 停止定位
	 */
	public void remove() {
		mCancel = true;
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				removeImp();
			}
		});
	}

	/**
	 * 移出定位工具
	 */
	private void removeImp() {

		mOnLocationListener.clear();
		// 终止高德定位
		stopGaodeLocation();

		// 终止google定位
		if (googleThread != null) {
			googleThread.stopThread();
		}
		// 终止gps定位
		if (mContext != null) {
			LocationManager locMgr = (LocationManager) mContext
					.getSystemService(Context.LOCATION_SERVICE);
			locMgr.removeUpdates(this);
		}
		running = false;
	}

	/**
	 * 销毁高德定位
	 */
	private void stopGaodeLocation() {
		if (mAMapLocManager != null) {
			mAMapLocManager.removeUpdates(this);
			mAMapLocManager.destroy();
		}
		mAMapLocManager = null;
	}

	/**
	 * 格式化数据
	 * 
	 * @param value
	 * @return
	 */
	private static String get(double value) {
		DecimalFormat df = new DecimalFormat("0.000000");
		return df.format(value) + "";
	}

	/** 设置定位超时时间 */
	public void setOutTime(long outTimeInMs) {
		mOutTime = outTimeInMs;
	}

	/**
	 * 填加定位接口
	 * 
	 * @param listener
	 */
	private void addOnLocationListener(OnLocationListener listener) {
		this.mOnLocationListener.add(listener);
	}

	@Override
	public void onLocationChanged(Location location) {
		HashMap<String, String> rs = new HashMap<String, String>();
		rs.put(LONGTITUDE, location.getLongitude() + "");
		rs.put(LATITUDE, location.getLatitude() + "");
		rs.put(TYPE, GPS);
		sendMessage(LOCATION_COMPLETE, rs);

	}

	@Override
	public void onProviderDisabled(String provider) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		switch (status) {
		default:
			return;
		case 0:

			return;
		case 2:

			return;
		case 1:
		}
	}

	@Override
	public void onLocationChanged(AMapLocation location) {
		if (location != null) {
			Double geoLat = location.getLatitude();
			Double geoLng = location.getLongitude();
			if (Math.abs(geoLat) > 0 && Math.abs(geoLng) > 0) {
				String desc = "";
				Bundle locBundle = location.getExtras();
				if (locBundle != null) {
					desc = locBundle.getString("desc");
				}
				String str = location.getCity() + location.getDistrict()
						+ location.getDistrict() + location.getAdCode();
				HashMap<String, String> rs = new HashMap<String, String>();
				rs.put(LONGTITUDE, geoLng + "");
				rs.put(LATITUDE, geoLat + "");
				rs.put(TYPE, AUTONAVI);
				if (desc != null) {
					rs.put(ADDR, desc);
				} else {
					rs.put(ADDR, str);
				}
				sendMessage(LOCATION_COMPLETE, rs);
			}
		}
	}

}
