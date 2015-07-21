package com.dtcloud.useranalysis.model;

import org.json.JSONException;

/**
 * app����
 * @author fengkun
 *
 */
@SuppressWarnings("serial")
public class AppDetail  extends BaseData{

	/**����*/
	private String packageName;
	/**�ڲ��汾��*/
	private String innerVersion;
	/**�ⲿ�汾��*/
	private String outterVersion;
	
	@Override
	public String toJSON(BaseData data) {
		
		try {
			json.put("packageName", packageName);
			json.put("innerVersion", innerVersion);
			json.put("outterVersion", outterVersion);
			return json.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getInnerVersion() {
		return innerVersion;
	}

	public void setInnerVersion(String innerVersion) {
		this.innerVersion = innerVersion;
	}

	public String getOutterVersion() {
		return outterVersion;
	}

	public void setOutterVersion(String outterVersion) {
		this.outterVersion = outterVersion;
	}

	
}
