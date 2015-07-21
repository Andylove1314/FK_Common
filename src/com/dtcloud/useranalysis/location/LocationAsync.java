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
 * �첽��λ
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

	/** ������ */
	private Context mContext;
	/** ��λ�������handler */
	private LocationHandler mHandler = new LocationHandler(this);

	/** google��վ��λ */
	private GoogleThread googleThread;

	/** �ߵ¶�λ */
	private LocationManagerProxy mAMapLocManager = null;

	/** ȡ����־ */
	private boolean mCancel = false;

	/** ��λ��ʱʱ�� */
	private long mOutTime = 120 * 1000;

	/** ��ǰ���ڶ�λ��־ */
	private boolean running = false;

	/** ��λ��ʽ��ʱ�л� */
	private static final int DELAY = 30 * 1000;

	/** handler ����UIThread */
	private static final int LOCATION_COMPLETE = 1;
	private static final int LOCATION_ERROR = -1;

	/** ��λ�ӿڼ��� */
	private Set<OnLocationListener> mOnLocationListener = new HashSet<OnLocationListener>();

	/**
	 * ����
	 * 
	 * @param context
	 */
	public LocationAsync(Context context) {
		mContext = context;
	}

	/**
	 * ��ȡ��λ��Ϣ
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
			// �ߵ�
			startGaoDeLocation();
		} catch (Exception e) {
			e.printStackTrace();
		}

		ScheduledExecutorService exe = Executors
				.newSingleThreadScheduledExecutor();
		exe.schedule(new Runnable() {
			@Override
			public void run() {
				Exception e = new Exception("��ʱ");
				sendMessage(LOCATION_ERROR, e);
				remove();
			}
		}, mOutTime, TimeUnit.MILLISECONDS);
	}

	/**
	 * ����gps��λ
	 */
	private void startGpsLocation() {

		LocationManager locMgr = (LocationManager) mContext
				.getSystemService(Context.LOCATION_SERVICE);
		locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60 * 1000,
				100, this);

	}

	/**
	 * �����ߵ¶�λ
	 */
	private void startGaoDeLocation() {

		mAMapLocManager = LocationManagerProxy.getInstance(mContext);
		/*
		 * mAMapLocManager.setGpsEnable(false);//
		 * 1.0.2�汾��������������true��ʾ��϶�λ�а���gps��λ��false��ʾ�����綨λ��Ĭ����true Location
		 * API��λ����GPS�������϶�λ��ʽ
		 * ����һ�������Ƕ�λprovider���ڶ�������ʱ�������5000���룬������������������λ���ף����ĸ������Ƕ�λ������
		 */
		mAMapLocManager.setGpsEnable(true);
		mAMapLocManager.requestLocationData(LocationProviderProxy.AMapNetwork,
				5000, 10, this);
	}

	/**
	 * google��վ��λ
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
		 * ȡ����λ����
		 */
		public void stopThread() {
			stopFlag = true;
			if (net != null) {
				net.stop();
			}
		}
	}

	/**
	 * ��λ����handler
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
					Exception e = new Exception("δȡ�ö�λ��Ϣ������GPS������״̬��");
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
	 * ���Ͷ�λ��Ϣ
	 * 
	 * @param what
	 * @param obj
	 */
	private void sendMessage(int what, Object obj) {
		Message msg = mHandler.obtainMessage(what, obj);
		mHandler.sendMessage(msg);
	}

	/**
	 * ֹͣ��λ
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
	 * �Ƴ���λ����
	 */
	private void removeImp() {

		mOnLocationListener.clear();
		// ��ֹ�ߵ¶�λ
		stopGaodeLocation();

		// ��ֹgoogle��λ
		if (googleThread != null) {
			googleThread.stopThread();
		}
		// ��ֹgps��λ
		if (mContext != null) {
			LocationManager locMgr = (LocationManager) mContext
					.getSystemService(Context.LOCATION_SERVICE);
			locMgr.removeUpdates(this);
		}
		running = false;
	}

	/**
	 * ���ٸߵ¶�λ
	 */
	private void stopGaodeLocation() {
		if (mAMapLocManager != null) {
			mAMapLocManager.removeUpdates(this);
			mAMapLocManager.destroy();
		}
		mAMapLocManager = null;
	}

	/**
	 * ��ʽ������
	 * 
	 * @param value
	 * @return
	 */
	private static String get(double value) {
		DecimalFormat df = new DecimalFormat("0.000000");
		return df.format(value) + "";
	}

	/** ���ö�λ��ʱʱ�� */
	public void setOutTime(long outTimeInMs) {
		mOutTime = outTimeInMs;
	}

	/**
	 * ��Ӷ�λ�ӿ�
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
