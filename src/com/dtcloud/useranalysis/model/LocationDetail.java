package com.dtcloud.useranalysis.model;

import org.json.JSONException;

/**
 * λ������
 * @author fengkun
 *
 */
public class LocationDetail extends BaseData{

	private static final long serialVersionUID = 1L;
	/** λ��γ��*/
	private String latitude;
	/** λ�þ���*/
	private String longitude;
	/** λ������*/
	private String locationName;
	@Override
	public String toJSON(BaseData data) {
		try {
			json.put("latitude", latitude);
			json.put("longitude", longitude);
			json.put("locationName", locationName);
			return json.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	
}
