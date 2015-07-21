package com.dtcloud.useranalysis.model;

import org.json.JSONException;

/**
 * �����쳣����
 * 
 * @author fengkun
 * 
 */
@SuppressWarnings("serial")
public class ExceptionDetail extends BaseData {

	/** �쳣ʱ�� */
	private String exceptionDate;
	/** �쳣���� */
	private String exceptionContent;

	@Override
	public String toJSON(BaseData data) {
		try {
			json.put("exceptionDate", exceptionDate);
			json.put("exceptionContent", exceptionContent);
			return json.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getExceptionDate() {
		return exceptionDate;
	}

	public void setExceptionDate(String exceptionDate) {
		this.exceptionDate = exceptionDate;
	}

	public String getExceptionContent() {
		return exceptionContent;
	}

	public void setExceptionContent(String exceptionContent) {
		this.exceptionContent = exceptionContent;
	}

	
}
