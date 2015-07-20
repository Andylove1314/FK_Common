package com.dtcloud.useranalysis.model;

import java.io.Serializable;

import org.json.JSONArray;
import org.json.JSONObject;
/**
 * 实体基类
 * @author fengkun
 *
 */
public abstract class BaseData implements Serializable{

	private static final long serialVersionUID = 1L;
	/**json对象*/
	protected JSONObject json = new JSONObject();
	/**json array对象*/
	protected JSONArray array = new JSONArray();

	/**把对象转换为json*/
	public abstract String toJSON(BaseData data); 
}
