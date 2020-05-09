package com.un.utila.runtime.model;

import java.util.List;

/**
 * Created by unlim on 15-8-6.
 */
public class ProcessInfo {
	public String user;
	public String pid;
	public String ppid;
	public String vsize;
	public String rss;
	public String wchan;
	public String pc;
	public String name;

	public ProcessInfo(final String user, final String pid, final String ppid, final String vsize, final String rss, final String wchan, final String pc, final String name) {
		this.user = user;
		this.pid = pid;
		this.ppid = ppid;
		this.vsize = vsize;
		this.rss = rss;
		this.wchan = wchan;
		this.pc = pc;
		this.name = name;
	}

	public ProcessInfo(List<String> pData) {
		this.user = pData.get(0);
		this.pid = pData.get(1);
		this.ppid = pData.get(2);
		this.vsize = pData.get(3);
		this.rss = pData.get(4);
		this.wchan = pData.get(5);
		this.pc = pData.get(6);
		this.name = pData.get(8);
	}

	@Override
	public String toString() {
		String str = "user=" + user + " pid=" + pid + " ppid=" + ppid + " name=" + name;
		return str;
	}
}
