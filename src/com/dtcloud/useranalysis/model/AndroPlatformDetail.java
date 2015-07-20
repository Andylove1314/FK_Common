package com.dtcloud.useranalysis.model;

import org.json.JSONException;

/**
 * 设备详情
 * @author fengkun
 *
 */
public class AndroPlatformDetail  extends BaseData{

	private static final long serialVersionUID = 1L;
	/**手机型号*/
	private String model;
	/**系统版本*/
	private String release;
	/**系统API版本*/
	private String api;
	/**设备imei*/
	private String imei;
	
	
	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	@Override
	public String toJSON(BaseData data) {
		try {
			json.put("model", model);
			json.put("release", release);
			json.put("api", api);
			json.put("imei", imei);
			return json.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getRelease() {
		return release;
	}

	public void setRelease(String release) {
		this.release = release;
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	
}
