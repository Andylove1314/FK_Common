package com.dtcloud.useranalysis.location;

/**
 * 定位结果接口
 * @author fengkun
 *
 */
public interface OnLocationListener {
	
	
	void onLocationComplete(LocationAsync location, Object obj);
	
	void onLocationError(LocationAsync location, Exception error);
	
}
