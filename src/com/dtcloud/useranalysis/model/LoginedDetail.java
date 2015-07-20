package com.dtcloud.useranalysis.model;

import org.json.JSONException;

/**
 * µÇÂ½³É¹¦·´À¡
 * @author fengkun
 *
 */
@SuppressWarnings("serial")
public class LoginedDetail  extends BaseData{

	private String loginedUserId;
	private String loginedTime;
	private String loginedLocation;
	
	@Override
	public String toJSON(BaseData data) {
		try {
			json.put("loginedUserId", loginedUserId);
			json.put("loginedTime", loginedTime);
			json.put("loginedLocation", loginedLocation);
			return json.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getLoginedUserId() {
		return loginedUserId;
	}

	public void setLoginedUserId(String loginedUserId) {
		this.loginedUserId = loginedUserId;
	}

	public String getLoginedTime() {
		return loginedTime;
	}

	public void setLoginedTime(String loginedTime) {
		this.loginedTime = loginedTime;
	}

	public String getLoginedLocation() {
		return loginedLocation;
	}

	public void setLoginedLocation(String loginedLocation) {
		this.loginedLocation = loginedLocation;
	}

	
	 
}
