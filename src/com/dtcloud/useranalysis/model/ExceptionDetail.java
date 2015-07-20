package com.dtcloud.useranalysis.model;

import org.json.JSONException;

/**
 * 程序异常详情
 * 
 * @author fengkun
 * 
 */
@SuppressWarnings("serial")
public class ExceptionDetail extends BaseData {

	/** 异常时间 */
	private String exceptionDate;
	/** 异常内容 */
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
