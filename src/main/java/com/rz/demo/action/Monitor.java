package com.rz.demo.action;

import jone.R;
import jone.web.WebUtil;

import com.rz.demo.service.MonitorService;

public class Monitor
{
	public R memeryHistory()
	{
		int point = Integer.valueOf(WebUtil.param("point"));
		return MonitorService.getMemeryHistory(point);
	}
	
	public R memery()
	{
		return MonitorService.getMemery();
	}
}
