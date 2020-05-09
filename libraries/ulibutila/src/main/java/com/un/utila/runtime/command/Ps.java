package com.un.utila.runtime.command;

import com.un.utila.runtime.model.ProcessInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by unlim on 15-8-6.
 * android环境下的ps命令
 */
public class Ps {
	public static final String TAG = "Ps";

	/**
	 * 运行PS命令得到进程信息
	 *
	 * @return USER PID PPID VSIZE RSS WCHAN PC NAME
	 */
	public static List<String> getAllProcess() {
		List<String> orgProcList = new ArrayList<String>();
		Process proc = null;
		List<String> commandList = new ArrayList<String>();
		commandList.add("ps");

		try {
			proc = Runtime.getRuntime().exec(commandList.toArray(new String[commandList.size()]));
			BufferedReader br = null;
			try {
				br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
				String line = null;
				while ((line = br.readLine()) != null) {
					orgProcList.add(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					br.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (proc != null) {
				proc.destroy();
			}
		}
		return orgProcList;
	}

	/**
	 * 根据ps命令得到的内容获取PID，User，name等信息
	 * 格式示例：
	 * USER PID PPID VSIZE RSS WCHAN PC NAME
	 * root 1 0 416 300 c00d4b28 0000cd5c S /init
	 *
	 * @return
	 */
	public static List<ProcessInfo> getAllProcessModel() {
		List<String> orgProcessList = getAllProcess();
		List<ProcessInfo> procInfoList = new ArrayList<ProcessInfo>();
		for (int i = 1; i < orgProcessList.size(); i++) {
			String processInfo = orgProcessList.get(i);
			String[] proStr = processInfo.split(" ");
			List<String> orgInfo = new ArrayList<String>();
			for (String str : proStr) {
				if (str != null && !str.isEmpty()) {
					orgInfo.add(str);
				}
			}
			if (orgInfo.size() == 9) {
				ProcessInfo pInfo = new ProcessInfo(orgInfo);
				procInfoList.add(pInfo);
			} else {
				//ps命令返回的数据格式未知
			}
		}
		return procInfoList;
	}

}
