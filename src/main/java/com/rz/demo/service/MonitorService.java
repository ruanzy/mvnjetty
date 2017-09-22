package com.rz.demo.service;

import java.util.Calendar;

import jone.R;
import jone.util.RrdUtils;

public class MonitorService
{
	public static R getMemeryHistory(int point)  
    {  
        R r = new R();
        Calendar c = Calendar.getInstance();
        long end = Long.valueOf(c.getTime().getTime()/1000);
        long start = end - point * RrdUtils.STEP;
        double[] d1 = RrdUtils.readRrd("192168001001_hmuinit.rrd", "hmuinit", start, end);
        double[] d2 = RrdUtils.readRrd("192168001001_hmuused.rrd", "hmuused", start, end);
        r.put("hmuinit", d1);
        r.put("hmuused", d2);
        return r;  
    }
	
	public static double getMemery()  
    {  
        return 131231233;  
    }
}
