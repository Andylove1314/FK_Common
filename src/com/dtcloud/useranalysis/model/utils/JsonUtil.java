package com.dtcloud.useranalysis.model.utils;

import android.app.Application;
import android.content.Context;

import com.dtcloud.useranalysis.model.AndroPlatformDetail;
import com.dtcloud.useranalysis.model.AppDetail;
import com.dtcloud.useranalysis.model.ExceptionDetail;
import com.dtcloud.useranalysis.model.LocationDetail;
import com.dtcloud.useranalysis.model.LoginedDetail;
import com.dtcloud.useranalysis.utils.DateUtil;

/**
 * json����
 * @author fengkun
 *
 */
public class JsonUtil {

	/**
	 * ��ȡ�豸��Ϣjson����
	 * @return
	 */
	public static String getAndroPlatformDetail(Application app){
		
		AndroPlatformDetail detail = new AndroPlatformDetail();
		detail.setModel(AndroPlatformUtil.getAndroModel());
		detail.setRelease(AndroPlatformUtil.getAndroRelease());
		detail.setApi(AndroPlatformUtil.getAPIVersion());
		detail.setImei(AndroPlatformUtil.getDeviceIMEI(app));
		return detail.toJSON(detail);
	}
	
	/**
	 * ��ȡAPP Json����
	 * @return
	 */
	public static String getAppDetail(Application app){
		
		AppDetail appDetail = new AppDetail();
		appDetail.setPackageName(AppUtil.getPackageName(app));
		appDetail.setInnerVersion(AppUtil.getInnerVersion(app));
		appDetail.setOutterVersion(AppUtil.getOutterVersion(app));
		return appDetail.toJSON(appDetail);
	
	}
	
	/**
	 * ��ȡapp�쳣 Json����
	 * @return
	 */
	public static String getExceptionDetail(Application app){
		
		ExceptionDetail appExcep = new ExceptionDetail();
		appExcep.setExceptionDate(DateUtil.getCurrentDateTime());
		appExcep.setExceptionContent(ExceptionUtil.crashReport);
		return appExcep.toJSON(appExcep);
	
	}
	
	/**
	 * ��ȡλ����Ϣ Json����
	 * @return
	 */
	public static String getLocationDetail(Application app){
		
		LocationDetail location = new LocationDetail();
		LocationUtil util = new LocationUtil(app);
		location.setLatitude(util.getLatitude());
		location.setLongitude(util.getLongitude());
		location.setLocationName(util.getLocationName());
		return location.toJSON(location);
	
	}
	
	/**
	 * ��ȡλ����Ϣ Json����
	 * @return
	 */
	public static String getLocationDetail(Context context){
		
		LocationDetail location = new LocationDetail();
		LocationUtil util = new LocationUtil(context);
		location.setLatitude(util.getLatitude());
		location.setLongitude(util.getLongitude());
		location.setLocationName(util.getLocationName());
		return location.toJSON(location);
	
	}
	
	/**
	 * ��½��Ϣ Json����
	 * @return
	 */
	public static String getloginedDetail(String userId){
		
		LoginedDetail logined = new LoginedDetail();
		logined.setLoginedUserId(userId);
		logined.setLoginedTime(DateUtil.getCurrentDateTime());
		logined.setLoginedLocation("");
		return logined.toJSON(logined);
	
	}

}
