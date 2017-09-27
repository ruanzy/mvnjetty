package com.rz.demo.util;

import java.util.Timer;
import java.util.TimerTask;

import jone.util.RrdUtils;

public class MonitorUtil
{

	public static void start()
	{
		RrdUtils.createRrd("hmucommitted.rrd", "hmucommitted");
		RrdUtils.createRrd("hmuused.rrd", "hmuused");
		RrdUtils.createRrd("hmumax.rrd", "hmumax");
		RrdUtils.createRrd("thread_daemon.rrd", "thread_daemon");
		RrdUtils.createRrd("thread_started.rrd", "thread_started");
		RrdUtils.createRrd("thread_active.rrd", "thread_active");
		RrdUtils.createRrd("cpu.rrd", "cpu");
		new Timer().schedule(new TimerTask() {
			@Override
			public void run()
			{
				double[] memarr = JmxUtil.getMemory("localhost", 9401);
				double[] threadarr = JmxUtil.getThread("localhost", 9401);
				long cpu = JmxUtil.getCPU("localhost", 9401);
				RrdUtils.writeRrd("hmucommitted.rrd", "hmucommitted", memarr[1]);
				RrdUtils.writeRrd("hmuused.rrd", "hmuused", memarr[2]);
				RrdUtils.writeRrd("hmumax.rrd", "hmumax", memarr[3]);
				RrdUtils.writeRrd("thread_daemon.rrd", "thread_daemon", threadarr[1]);
				RrdUtils.writeRrd("thread_started.rrd", "thread_started", threadarr[0]);
				RrdUtils.writeRrd("thread_active.rrd", "thread_active", threadarr[2]);
				RrdUtils.writeRrd("cpu.rrd", "cpu", cpu);
			}
		}, 2000, RrdUtils.STEP * 1000);
	}
	
	public static void main(String[] args)
	{
		//System.out.println(JmxUtil.getPID("localhost", "9401"));
	}
}
