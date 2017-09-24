package com.rz.demo.service;

import jone.R;
import jone.util.RrdUtils;

public class MonitorService
{
	public static R getMemeryHistory(int point)  
    {  
		R r = new R();
		double[] hmucommitted = RrdUtils.readLastValues("192168001001_hmucommitted.rrd", "hmucommitted", point, 1);
		double[] hmuused = RrdUtils.readLastValues("192168001001_hmuused.rrd", "hmuused", point, 1);
		double[] hmumax = RrdUtils.readLastValues("192168001001_hmumax.rrd", "hmumax", point, 1);
		r.put("hmucommitted", hmucommitted);
		r.put("hmuused", hmuused);
		r.put("hmumax", hmumax);
		return r;
    }
	
	public static R getMemery()  
    {  
		R r = new R();
		double hmucommitted = RrdUtils.readLastValues("192168001001_hmucommitted.rrd", "hmucommitted", 1, 1)[0];
		double hmuused = RrdUtils.readLastValues("192168001001_hmuused.rrd", "hmuused", 1, 1)[0];
		double hmumax = RrdUtils.readLastValues("192168001001_hmumax.rrd", "hmumax", 1, 1)[0];
		r.put("hmucommitted", hmucommitted);
		r.put("hmuused", hmuused);
		r.put("hmumax", hmumax);
		return r;
    }
}
