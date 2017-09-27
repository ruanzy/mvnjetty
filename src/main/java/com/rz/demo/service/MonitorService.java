package com.rz.demo.service;

import java.util.HashMap;
import java.util.Map;

import com.rz.demo.util.JmxUtil;

import jone.util.RrdUtils;

public class MonitorService
{
	public static Map<String, double[]> getMemery(int point)  
    {  
		Map<String, double[]> ret = new HashMap<String, double[]>();
		double[] hmucommitted = RrdUtils.readLastValues("hmucommitted.rrd", "hmucommitted", point, 1);
		double[] hmuused = RrdUtils.readLastValues("hmuused.rrd", "hmuused", point, 1);
		double[] hmumax = RrdUtils.readLastValues("hmumax.rrd", "hmumax", point, 1);
		ret.put("hmucommitted", hmucommitted);
		ret.put("hmuused", hmuused);
		ret.put("hmumax", hmumax);
		return ret;
    }
	
	public static double[] getCpu(int point)  
    {  
		double[] ret = new double[point];
		int core = JmxUtil.getCore("localhost", 9401);
		double[] cpu = RrdUtils.readLastValues("cpu.rrd", "cpu", point + 1, 1);
		for (int i = 0; i < point; i++)
		{
			double d = cpu[i + 1] - cpu[i];
			double usage = d/10000.0/5000/core;
			ret[i] = usage;
		}
		return ret;
    }
	
	public static Map<String, double[]> getThread(int point)  
    {  
		Map<String, double[]> ret = new HashMap<String, double[]>();
		double[] thread_daemon = RrdUtils.readLastValues("thread_daemon.rrd", "thread_daemon", point, 1);
		double[] thread_started = RrdUtils.readLastValues("thread_started.rrd", "thread_started", point, 1);
		double[] thread_active = RrdUtils.readLastValues("thread_active.rrd", "thread_active", point, 1);
		ret.put("thread_daemon", thread_daemon);
		ret.put("thread_started", thread_started);
		ret.put("thread_active", thread_active);
		return ret;
    }
}
