package com.rz.demo.util;

import java.io.File;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;

import jone.monitor.Gauge;
import jone.monitor.Indicators;

public class MetricUtil
{
	static OperatingSystemMXBean system = ManagementFactory
			.getOperatingSystemMXBean();
	static ClassLoadingMXBean classLoad = ManagementFactory
			.getClassLoadingMXBean();
	static RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
	static MemoryMXBean memory = ManagementFactory.getMemoryMXBean();
	static Runtime r = Runtime.getRuntime();
	static ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

	public static void start()
	{
		File d = new File("d:/metrics");
		Indicators.build(d).add("freeMemory", new Gauge() {

			@Override
			public Double getValue()
			{
				return Double.valueOf(Runtime.getRuntime().freeMemory());
			}
		}).start(5);
	}
}
