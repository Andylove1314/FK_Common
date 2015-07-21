package com.dtcloud.useranalysis.model.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;

import com.dtcloud.useranalysis.utils.LogUtil;

/**
 * Ӧ�ù���
 * @author fengkun
 */
public class AppUtil {

	private static String TAG = AppUtil.class.getName();
	
	/**
	 * ��ȡ����
	 * @param app
	 * @return
	 */
	public static String getPackageName(Application app){
		return app.getPackageName();
		
	}
	/**
	 * ��ȡ�ڲ��汾��
	 * @param app
	 * @return
	 */
	public static String getInnerVersion(Application app){
		PackageInfo info = null;
		try {
			info = app.getPackageManager().getPackageInfo(getPackageName(app), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			LogUtil.i(TAG, "��ȡ����Ϣʧ�ܣ�");
		}
		return info.versionCode+"";
		
	}
	
	/**
	 * ��ȡ�ⲿ�汾��
	 * @param app
	 * @return
	 */
	public static String getOutterVersion(Application app){
		PackageInfo info = null;
		try {
			info = app.getPackageManager().getPackageInfo(getPackageName(app), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			LogUtil.i(TAG, "��ȡ����Ϣʧ�ܣ�");
		}
		return info.versionName;

	}

	   /**
	    * �ж�app�Ƿ�װ
	    * @param app
	    * @return
	    */
		public static boolean isAppInstalled(Application app) {
			PackageInfo packageInfo;
			try {
				packageInfo = app.getPackageManager().getPackageInfo(getPackageName(app), 0);
			} catch (NameNotFoundException e) {
				packageInfo = null;
				e.printStackTrace();
			}
			if(packageInfo == null){
				return false;
			}else{
				return true;
			}
		}
	
		/**
	     * ��ȡ����ǩ����Ϣ
	     * @return
	     */
	    public static String getAppSignature(Application app)
	    {
	        if (getPackageName(app) == null || "".equals(getPackageName(app)))
	            return "";
	        try
	        {
	            PackageInfo packageInfo = app.getPackageManager().getPackageInfo(
	                    getPackageName(app), PackageManager.GET_SIGNATURES);
	            Signature[] signatures = packageInfo.signatures;
	            byte[] data = null;
	            if (signatures != null && signatures.length > 0)
	            {
	                data = signatures[0].toByteArray();
	            }
	            MessageDigest mdInst = MessageDigest.getInstance("MD5");
	            mdInst.update(data);
	            byte[] md = mdInst.digest();
	            return toHex(md);
	        }
	        catch (NameNotFoundException e)
	        {
	            e.printStackTrace();
	        }
	        catch (NoSuchAlgorithmException e)
	        {
	            e.printStackTrace();
	        }
	        return "";
	    }

	    /**
	     * ת��Ϊ16�����ַ���
	     * @param buffer
	     * @return
	     */
	    private static String toHex(byte[] buffer)
	    {
	        StringBuffer sb = new StringBuffer(buffer.length * 2);
	        for (int i = 0; i < buffer.length; i++)
	        {
	            sb.append(Character.forDigit((buffer[i] & 240) >> 4, 16));
	            sb.append(Character.forDigit(buffer[i] & 15, 16));
	        }
	        return sb.toString();
	    }
	    
	    /**
	     * ��ȡuuid
	     * @return
	     */
	    public static String getUuid(){
	    	UUID uuid = UUID.randomUUID();
	    	String id = uuid.toString().replace("-", "");
			return id;
	    	
	    }
}
