package com.un.utila.runtime.command;

import android.util.Log;

import com.un.utila.runtime.ProcessUtil;
import com.un.utila.runtime.model.ProcessInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by unlim on 15-8-6.
 */
public class Logcat {
	public static final String TAG = "Logcat";

	public static final String DEBUG_TAG = Logcat.class.getPackage().getName();

	/**
	 * 清除日志缓存
	 */
	public static void clearCache() {
		Process proc = null;
		List<String> commandList = new ArrayList<String>();
		commandList.add("logcat");
		commandList.add("-c");
		String[] commandArray = new String[commandList.size()];
		commandList.toArray(commandArray);
		try {
			proc = Runtime.getRuntime().exec(commandArray);
			if (proc.waitFor() != 0) {
				Log.w(DEBUG_TAG, "命令执行错误");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (proc != null) {
				proc.destroy();
			}
		}
	}

	/**
	 * 启动将日志存放到文件中的进程
	 */
	public static Process saveToFile(final String filePath, final String tag) {
		Process process = null;
		List<String> commandList = new ArrayList<String>();
		commandList.add("logcat");
		commandList.add("-f");
		commandList.add(filePath);
		commandList.add("-v");
		commandList.add("time");
		commandList.add("-s");
		commandList.add(tag + ":d");
		commandList.add("System.err:*");

		try {
			process = Runtime.getRuntime().exec(commandList.toArray(new String[commandList.size()]));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return process;
	}

	/**
	 * 杀死同一用户下的logcat进程
	 */
	public static void killUserLogcatPro(final String pPackageName) {
		List<ProcessInfo> processInfoList = Ps.getAllProcessModel();
		String myUser = ProcessUtil.getAppUser(pPackageName, processInfoList);
		for (ProcessInfo processInfo : processInfoList) {
			if (processInfo.user.equals(myUser) && "logcat".equals(processInfo.name)) {
				android.os.Process.killProcess(Integer.parseInt(processInfo.pid));
			}
		}
	}
}
