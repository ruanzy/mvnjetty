package com.rz.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jone.util.RrdUtils;

public class MonitorService
{
	public static Map<String, double[]> getMemery(int point)  
    {  
		List<String> dss = new ArrayList<String>();
		dss.add("hmucommitted");
		dss.add("hmuused");
		dss.add("hmumax");
		Map<String, double[]> ret = RrdUtils.readLastValues("192_168_1_1.rrd", dss, point, 1);
		return ret;
    }
	
	public static Map<String, double[]> getThread(int point)  
    {  
		List<String> dss = new ArrayList<String>();
		dss.add("thread_daemon");
		dss.add("thread_started");
		dss.add("thread_active");
		Map<String, double[]> ret = RrdUtils.readLastValues("192_168_1_1.rrd", dss, point, 1);
		return ret;
    }
}
