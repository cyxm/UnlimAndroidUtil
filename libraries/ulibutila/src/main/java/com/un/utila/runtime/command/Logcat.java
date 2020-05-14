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
	 * 启动logcat进程将日志存入文件
	 *
	 * @param filePath
	 * 		文件路径
	 * @param tag
	 * 		Tag
	 * @param level
	 * 		日志等级
	 *
	 * @return logcat进程
	 */
	public static Process logToFile(final String filePath, final String tag, final String level) {
		Process process = null;
		List<String> commandList = new ArrayList<String>();
		commandList.add("logcat");
		commandList.add("-f");
		commandList.add(filePath);
		commandList.add("-v");
		commandList.add("time");
		commandList.add("-s");
		commandList.add(tag + ":" + level);

		try {
			process = Runtime.getRuntime().exec(commandList.toArray(new String[commandList.size()]));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return process;
	}

	/**
	 * 打印Error等级及以上的log到文件中
	 */
	public static Process logIToFile(final String filePath, final String tag) {
		return logToFile(filePath, tag, "I");
	}

	/**
	 * 打印Error等级及以上的log到文件中
	 */
	public static Process logEToFile(final String filePath) {
		return logToFile(filePath, "*", "E");
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
