package com.dtcloud.useranalysis.location;

/**
 * ��λ����ӿ�
 * @author fengkun
 *
 */
public interface OnLocationListener {
	
	
	void onLocationComplete(LocationAsync location, Object obj);
	
	void onLocationError(LocationAsync location, Exception error);
	
}
