package com.dtcloud.useranalysis.location;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;

import com.dtcloud.useranalysis.utils.LogUtil;

/**
 * android定位之基站定位
 * @author fengkun
 *
 */

public class NetWorkLocation {
	
	/**网络请求,请求google服务器*/
	private DefaultHttpClient client;
	
	public Location getLocation(Context ctx){
		try{
			TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
			CellIDInfo info = new CellIDInfo();
			//设备网络类型
			if (tm.getPhoneType() == TelephonyManager.PHONE_TYPE_CDMA) {
				CdmaCellLocation location = (CdmaCellLocation) tm
						.getCellLocation();
				if (location == null){
					return null;
				}	
				// 系统标识 mobileNetworkCode
				int sid = location.getSystemId();
				// 基站小区号 cellId
				int bid = location.getBaseStationId();
				// 网络标识 locationAreaCode
				int nid = location.getNetworkId();
				LogUtil.i("sid:", "" + sid);
				LogUtil.i("bid:", "" + bid);
				LogUtil.i("nid:", "" + nid);

				info.cellId = bid;
				info.locationAreaCode = nid;
				info.mobileNetworkCode = String.valueOf(sid);
				info.mobileCountryCode = tm.getNetworkOperator().substring(
						0, 3);
				info.mobileCountryCode = tm.getNetworkOperator().substring(
						3, 5);
				info.radioType = "cdma";
				LogUtil.d("cellId:", "" + info.cellId);
				LogUtil.d("locationAreaCode:", "" + info.locationAreaCode);
				LogUtil.d("mobileNetworkCode:", info.mobileNetworkCode);
				LogUtil.d("mobileCountryCode:", info.mobileCountryCode);
			} else if (TelephonyManager.PHONE_TYPE_GSM == tm.getPhoneType()) {
				GsmCellLocation location = (GsmCellLocation) tm
						.getCellLocation();
				if (location == null){
					return null;
				}
					
				// 基站小区号 cellId
				int bid = location.getCid();
				// 网络标识 locationAreaCode
				int nid = location.getLac();

				LogUtil.i("bid:", "" + bid);
				LogUtil.i("nid:", "" + nid);

				info.cellId = bid;
				info.locationAreaCode = nid;
				info.mobileCountryCode = tm.getNetworkOperator().substring(
						0, 3);
				info.mobileNetworkCode = tm.getNetworkOperator().substring(
						3, 5);
				info.radioType = "gsm";
				LogUtil.d("cellId:", "" + info.cellId);
				LogUtil.d("locationAreaCode:", "" + info.locationAreaCode);
				LogUtil.d("mobileNetworkCode:", info.mobileNetworkCode);
				LogUtil.d("mobileCountryCode:", info.mobileCountryCode);
			}
			return callGear(info);
		}catch (Exception e) {
			return null;
		}
		
	}
	
	/**
	 * 中断连接
	 */
	public void stop(){
		if( client != null){
			client.getConnectionManager().shutdown();
		}
	}
	
	// 调用google gears的方法，该方法调用gears来获取经纬度
	private Location callGear(CellIDInfo cellID) {
		if (cellID == null)
			return null;
		client = new DefaultHttpClient();
		HttpPost post = new HttpPost("http://www.google.com/loc/json");
		JSONObject holder = new JSONObject();

		try {
			holder.put("version", "1.1.0");
			holder.put("host", "maps.google.com");
			holder.put("home_mobile_country_code", cellID.mobileCountryCode);
			holder.put("home_mobile_network_code", cellID.mobileNetworkCode);
			holder.put("radio_type", cellID.radioType);
			holder.put("request_address", true);
			if ("460".equals(cellID.mobileCountryCode))
				holder.put("address_language", "zh_CN");
			else
				holder.put("address_language", "en_US");

			JSONObject data, current_data;

			JSONArray array = new JSONArray();

			current_data = new JSONObject();
			current_data.put("cell_id", cellID.cellId);
			current_data.put("location_area_code", cellID.locationAreaCode);
			current_data.put("mobile_country_code", cellID.mobileCountryCode);
			current_data.put("mobile_network_code", cellID.mobileNetworkCode);
			current_data.put("age", 0);
			current_data.put("signal_strength", -60);
			current_data.put("timing_advance", 5555);
			array.put(current_data);

			holder.put("cell_towers", array);

			StringEntity se = new StringEntity(holder.toString(),"utf-8");
			LogUtil.i("Location send", holder.toString());
			post.setEntity(se);
			HttpResponse resp = client.execute(post);

			HttpEntity entity = resp.getEntity();

			BufferedReader br = new BufferedReader(new InputStreamReader(entity
					.getContent(),"utf-8"));
			StringBuffer sb = new StringBuffer();
			String result = br.readLine();
			while (result != null) {
				LogUtil.e("Locaiton reseive", result);
				sb.append(result);
				result = br.readLine();
			}

			data = new JSONObject(sb.toString());
			LogUtil.d("-", sb.toString());
			data = (JSONObject) data.get("location");

			Location loc = new Location(LocationManager.NETWORK_PROVIDER);
			loc.setLatitude((Double) data.get("latitude"));
			loc.setLongitude((Double) data.get("longitude"));
			loc.setAccuracy(Float.parseFloat(data.get("accuracy").toString()));
			loc.setTime(System.currentTimeMillis());

			return loc;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		LogUtil.d("-", "null 1");
		return null;
	}
	
}
