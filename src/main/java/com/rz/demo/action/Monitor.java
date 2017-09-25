package com.rz.demo.action;

import java.util.Map;

import jone.web.WebUtil;

import com.rz.demo.service.MonitorService;

public class Monitor
{
	public Map<String, double[]> memery()
	{
		int point = Integer.valueOf(WebUtil.param("point"));
		return MonitorService.getMemery(point);
	}
	
	public Map<String, double[]> thread()
	{
		int point = Integer.valueOf(WebUtil.param("point"));
		return MonitorService.getThread(point);
	}
}
