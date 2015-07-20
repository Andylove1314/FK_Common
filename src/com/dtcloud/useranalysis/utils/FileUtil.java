package com.dtcloud.useranalysis.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.dtcloud.useranalysis.Gloabals;
import com.dtcloud.useranalysis.model.utils.AppUtil;

/**
 * 文件操作工具
 * @author fengkun
 *
 */
public class FileUtil {

	private static String TAG = FileUtil.class.getName();
	
	/**
	 * 缓存数据对象
	 * @param context
	 * @param data
	 */
	public static void saveObjectToFile(Context context, Object data) {

		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			
			fos = context.openFileOutput(Gloabals.DISK_PATH, Context.MODE_PRIVATE);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(data);
			oos.flush();
			oos.close();
			fos.flush();
			fos.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (Exception e) {
			}
			try {
				fos.close();
			} catch (Exception e) {
			}
		}
	}
	
	/**
	 * 
	 * @param context
	 * @return
	 */
	public static Object readObjectFromFile(Context context) {

				
		if (!isExistDataCache(context)) {
			LogUtil.i(TAG, "文件不存在");
			return null;
		}

		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = context.openFileInput(Gloabals.DISK_PATH);
			ois = new ObjectInputStream(fis);
			Object ob = ois.readObject();
			return ob;
		} catch (FileNotFoundException e) {
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof InvalidClassException) {
				File dataFile = context.getFileStreamPath(Gloabals.DISK_PATH);
				dataFile.delete();
			}
		} finally {
			try {
				ois.close();
			} catch (Exception e) {
			}
			try {
				fis.close();
			} catch (Exception e) {
			}
		}
		return null;
	}
	
	/**
	 * 判断缓存文件是否存在
	 * @param contextØ
	 * @return
	 */
	private static boolean isExistDataCache(Context context) {
		
		File data = context.getFileStreamPath(Gloabals.DISK_PATH);
		if (data.exists()) {
			return true;
		}
		return false;
	}

	/**
	 * 删除缓存文件
	 * @param app
	 */
	@SuppressLint("SdCardPath") 
	public static void deleteUserData(Application app) {
		//缓存文件目录
		String path = "/data/data/"+AppUtil.getPackageName(app)+"/files/"+Gloabals.DISK_PATH;
		File fileFile = new File(path);
		if (fileFile.exists()) {
			fileFile.delete();
		}

	}
	
}
