package com.dtcloud.useranalysis.model;

import java.io.Serializable;

import org.json.JSONArray;
import org.json.JSONObject;
/**
 * ʵ�����
 * @author fengkun
 *
 */
public abstract class BaseData implements Serializable{

	private static final long serialVersionUID = 1L;
	/**json����*/
	protected JSONObject json = new JSONObject();
	/**json array����*/
	protected JSONArray array = new JSONArray();

	/**�Ѷ���ת��Ϊjson*/
	public abstract String toJSON(BaseData data); 
}
