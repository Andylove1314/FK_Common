package com.dtcloud.useranalysis.utils;

import java.security.MessageDigest;

/**
 * md5加密工具
 * @author fengkun
 *
 */
public class MD5Util {

	/**
	 * 生成md5
	 * @param source
	 * @return
	 */
	public static String getMD5(String source) {
		String s = null;
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = source.getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(strTemp);
			byte tmp[] = md.digest();
			char str[] = new char[16 * 2];
			int k = 0;
			for (int i = 0; i < 16; i++) {
				byte byte0 = tmp[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			s = new String(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
}
