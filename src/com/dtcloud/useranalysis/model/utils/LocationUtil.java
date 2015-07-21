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
 * ��λ����
 * @author fengkun
 *
 */
public class LocationUtil {

	private static String TAG = LocationUtil.class.getName();
	/**λ����Ϣ�ṩ��*/
	private LocationInfoProvider locationInfoProvider;
	/**λ�þ�γ��*/
	private String location;
	private Application app;
	private Context context;
	/**γ��*/
	private String latitude;
	/**���� */
	private String longitude;
	/**ʱ�� */
	private String time;
	
	/**����*/
	public LocationUtil(Context context){

	}
	
	/**����*/
	public LocationUtil(Application app){
		
	}
	
	/**
	 * ��ȡλ��γ��
	 * @return
	 */
	public String getLatitude(){
		return latitude;
	}

	/**
	 * ��ȡλ�þ���
	 * @return
	 */
	public String getLongitude(){
		return longitude;
	}
	/**
	 * ��ȡʱ��
	 * @return
	 */
	public String getTime(){
		return time;
	}
	
	/**
	 * ��ȡλ������
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
			loc = address.getCountryName()+address.getLocality()+address.getAddressLine(0)+"����";
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return loc;
	}
	
}
