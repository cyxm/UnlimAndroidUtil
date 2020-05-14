package com.un.utila.runtime;

import android.app.ActivityManager;
import android.content.Context;

import com.un.utila.runtime.model.ProcessInfo;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by unlim on 15-8-10.
 */
public class ProcessUtil {
	/**
	 * 通过Android底层实现进程关闭
	 *
	 * @param process
	 */
	public static void killProcess(Process process) {
		int pid = getProcessId(process.toString());
		if (pid != 0) {
			try {
				android.os.Process.killProcess(pid);
			} catch (Exception e) {
				try {
					process.destroy();
				} catch (Exception ex) {
				}
			}
		}
	}

	/**
	 * 获取当前进程的ID
	 *
	 * @param str
	 *
	 * @return
	 */
	public static int getProcessId(String str) {
		try {
			int i = str.indexOf("=") + 1;
			int j = str.indexOf("]");
			String cStr = str.substring(i, j).trim();
			return Integer.parseInt(cStr);
		} catch (Exception e) {
			return 0;
		}
	}


	/**
	 * 关闭进程的所有流
	 *
	 * @param process
	 */
	public static void closeAllStream(Process process) {
		try {
			InputStream in = process.getInputStream();
			if (in != null)
				in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			InputStream in = process.getErrorStream();
			if (in != null)
				in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			OutputStream out = process.getOutputStream();
			if (out != null)
				out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 销毁一个进程
	 *
	 * @param process
	 */
	public static void processDestroy(Process process) {
		if (process != null) {
			try {
				if (process.exitValue() != 0) {
					closeAllStream(process);
					killProcess(process);
				}
			} catch (IllegalThreadStateException e) {
				closeAllStream(process);
				killProcess(process);
			}
		}
	}

	/**
	 * 获取本程序的用户名称
	 *
	 * @param packName
	 * @param allProcList
	 *
	 * @return
	 */
	public static String getAppUser(String packName, List<ProcessInfo> allProcList) {
		for (ProcessInfo processInfo : allProcList) {
			if (processInfo.name.equals(packName)) {
				return processInfo.user;
			}
		}
		return "";
	}

	/**
	 * 通过线程进行异步销毁
	 *
	 * @param process
	 */
	public static void asyncProcessDestroy(final Process process) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				processDestroy(process);
			}
		});
		thread.setDaemon(true);
		thread.start();
	}

	/**
	 * 获取当前进程名称
	 *
	 * @param context
	 *
	 * @return
	 */
	public static String getProcessName(Context context) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		if (am == null) {
			return null;
		}
		List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
		if (runningApps == null) {
			return null;
		}
		int pid = android.os.Process.myPid();
		for (ActivityManager.RunningAppProcessInfo proInfo : runningApps) {
			if (proInfo.pid == pid) {
				if (proInfo.processName != null) {
					return proInfo.processName;
				} else {
					break;
				}
			}
		}
		return null;
	}

	/**
	 * 进程是否正在活动
	 *
	 * @param process
	 *
	 * @return
	 */
	public static boolean isProcessAlive(Process process) {
		if (process == null) {
			return false;
		}
		try {
			process.exitValue();
			return false;
		} catch (Exception e) {
			return true;
		}
	}
}
