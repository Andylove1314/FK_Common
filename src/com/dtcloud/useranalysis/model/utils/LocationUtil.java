package com.dtcloud.useranalysis.model.utils;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Application;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.dtcloud.useranalysis.service.LocationInfoProvider;

/**
 * 定位工具
 * @author fengkun
 *
 */
public class LocationUtil {

	private static String TAG = LocationUtil.class.getName();
	/**位置信息提供器*/
	private LocationInfoProvider locationInfoProvider;
	/**位置经纬度*/
	private String location;
	private Application app;
	private Context context;
	/**纬度*/
	private String latitude;
	/**经度 */
	private String longitude;
	/**时间 */
	private String time;
	
	/**构造*/
	public LocationUtil(Context context){

	}
	
	/**构造*/
	public LocationUtil(Application app){
		
	}
	
	/**
	 * 获取位置纬度
	 * @return
	 */
	public String getLatitude(){
		return latitude;
	}

	/**
	 * 获取位置经度
	 * @return
	 */
	public String getLongitude(){
		return longitude;
	}
	/**
	 * 获取时间
	 * @return
	 */
	public String getTime(){
		return time;
	}
	
	/**
	 * 获取位置名称
	 * @return
	 */
	public String getLocationName(){
		String loc = null;
		Geocoder coder = new Geocoder(app,Locale.getDefault());
		double lat = Double.parseDouble(latitude);
		double lon = Double.parseDouble(longitude);
		
		try {
			List<Address> addresses = coder.getFromLocation(lat, lon, 1);
			Address address = addresses.get(0);
			loc = address.getCountryName()+address.getLocality()+address.getAddressLine(0)+"附近";
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return loc;
	}
	
}
